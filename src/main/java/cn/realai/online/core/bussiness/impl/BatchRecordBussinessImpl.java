package cn.realai.online.core.bussiness.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.BatchListBO;
import cn.realai.online.core.bussiness.BatchRecordBussiness;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.query.OfflineBatchListQuery;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.vo.OfflineBatchCreateVO;
import cn.realai.online.core.vo.OfflineBatchListVO;
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
import java.time.LocalDateTime;
import java.util.*;

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


    @Override
    public PageBO<OfflineBatchListVO> pageList(OfflineBatchListQuery query) {
        //开启分页
        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());

        BatchListBO listBO = new BatchListBO();
        listBO.setModelName(query.getName());
        listBO.setStatus(query.getTrainStatus());

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
                voList.add(itemVO);
            });

        }
        PageBO<OfflineBatchListVO> pageBO = new PageBO<>(voList, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long createBatchRecord(OfflineBatchCreateVO createVO) {
        HashMap releaseMap = experimentService.findByServiceIdAndReleaseStatus(createVO.getServiceId(), Experiment.RELEAS_YES);
        Assert.notNull(releaseMap, "未找到对应服务信息无法创建跑批记录");

        //创建跑批记录
        BatchRecord record = new BatchRecord();
        record.setXtableHeterogeneousDataSource(createVO.getxHeteroDataSource());
        record.setXtableHomogeneousDataSource(createVO.getxHomoDataSource());
        record.setYtableDataSource(createVO.getyDataSource());
        record.setBatchType(BatchRecord.BATCH_TYPE_OFFLINE);
        record.setBatchName("");
        record.setServiceId(createVO.getServiceId());
        if (releaseMap != null) {
            record.setExperimentId((Long) releaseMap.get("experimentId"));
            record.setBatchName(String.valueOf(releaseMap.get("serviceName")) + "_" + String.valueOf(record.getCreateTime()) + "_离线跑批");
            record.setExperimentId((Long) releaseMap.get("experimentId"));
        }
        batchRecordService.insert(record);
        log.warn("新增的recordId:" + record.getId());
        //todo 更新服务跑批次数

        return record.getId();
    }
}
