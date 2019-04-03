package cn.realai.online.core.bussiness.impl;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.common.RedisKeyPrefix;
import cn.realai.online.common.config.Config;
import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.BatchListBO;
import cn.realai.online.core.bussiness.BatchRecordBussiness;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.query.OfflineBatchListQuery;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.service.ServiceService;
import cn.realai.online.core.vo.OfflineBatchCreateVO;
import cn.realai.online.core.vo.OfflineBatchListVO;
import cn.realai.online.tool.lock.RedissonLock;
import cn.realai.online.tool.modelcallthreadpool.ModelCallPool;
import cn.realai.online.util.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-21 10:30
 */
@Service
public class BatchRecordBussinessImpl implements BatchRecordBussiness {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BatchRecordService batchRecordService;
    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private TrainService trainService;
    @Autowired
    private RedissonLock redissonLock;
    @Autowired
    private ModelService modelService;
    @Autowired
    private Config config;


    @Override
    public PageBO<OfflineBatchListVO> pageList(OfflineBatchListQuery query) {
        //开启分页
        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());

        BatchListBO listBO = new BatchListBO();
        listBO.setModelName(query.getName());
        listBO.setStatus(query.getStatus());

        Long minDateV = null, maxDateV = null;
        try {
            if (StringUtils.isNotBlank(query.getMinDate())) {
                Date minDate = DateUtils.parseDate(query.getMinDate(), DateUtil.ymd, DateUtil.ymdhms);
                minDateV = Optional.ofNullable(minDate).map(v -> v.getTime()).orElse(null);
            }
            if (StringUtils.isNotBlank(query.getMaxDate())) {
                Date maxDate = DateUtils.parseDate(query.getMinDate(), DateUtil.ymd, DateUtil.ymdhms);
                maxDateV = Optional.ofNullable(maxDate).map(v -> v.getTime()).orElse(null);
            }

        } catch (ParseException e) {
            log.error("跑批日期格式不正确", e);
        }
        List<BatchListBO> list = batchRecordService.selectList(listBO, minDateV, maxDateV);

        //处理查询结果
        List<OfflineBatchListVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.forEach(item -> {
                OfflineBatchListVO itemVO = new OfflineBatchListVO();
                BeanUtils.copyProperties(item, itemVO);
                //处理离线跑批的下载地址
                if (StringUtils.isNotEmpty(item.getDownUrl())) {
                    itemVO.setDownUrl(config.getNginxUrl() + item.getDownUrl());
                }
                voList.add(itemVO);
            });

        }
        PageBO<OfflineBatchListVO> pageBO = new PageBO<>(voList, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BatchRecord createBatchRecord(OfflineBatchCreateVO createVO) {
        boolean isAvailable = serviceService.checkService(createVO.getServiceId());
        Assert.isTrue(isAvailable, "当前服务不可用");
        Model model = modelService.selectByServiceId(createVO.getServiceId());
        Assert.notNull(model, "当前服务下无已发布模型");
        cn.realai.online.core.entity.Service serviceEntity = serviceService.get(createVO.getServiceId());
        int batchTimes = (int)serviceEntity.getBatchTimes() + 1;

        //创建跑批记录
        BatchRecord record = new BatchRecord();
        record.setXtableHeterogeneousDataSource(createVO.getxHeteroDataSource());
        record.setStatus(BatchRecord.BATCH_STATUS_NEW);
        record.setXtableHomogeneousDataSource(createVO.getxHomoDataSource());
        record.setYtableDataSource(createVO.getyDataSource());
        record.setBatchType(BatchRecord.BATCH_TYPE_OFFLINE);
        record.setOfflineTimes(batchTimes);
        //record.setCreateTime(new Date().getTime());
        record.setModelId(model.getId());
        record.setServiceId(createVO.getServiceId());
        record.setExperimentId(model.getExperimentId());
        record.setBatchName(serviceEntity.getName() + "_" + String.valueOf(DateUtil.formatDateToString(new Date(), "yyyyMMddHHmmss")) + "_离线跑批");
        record.setRemark(createVO.getRemark());
        record.setStatus(BatchRecord.BATCH_STATUS_NEW);
        record.setDelFlag(BatchRecord.NO_DEL);
        batchRecordService.insert(record);

        //更新服务跑批次数
        serviceEntity.setBatchTimes(batchTimes);
        serviceService.update(serviceEntity);
        return record;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int executeBatchRecord(BatchRecord batchRecord) {
    	int ret = trainService.runBatchOfOffline(batchRecord);
    	if (ret == 1) {
    		batchRecordService.updateBatchRecordStatus(batchRecord.getId(), BatchRecord.BATCH_STATUS_EXECUTING);
    	}
    	return ret;
    }
}
