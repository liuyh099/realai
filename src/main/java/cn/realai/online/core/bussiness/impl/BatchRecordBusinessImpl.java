package cn.realai.online.core.bussiness.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.BatchListBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.bussiness.BatchRecordBusiness;
import cn.realai.online.core.query.OfflineBatchListQuery;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.core.vo.ModelListVO;
import cn.realai.online.core.vo.OfflineBatchListVO;
import cn.realai.online.util.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-21 10:30
 */
public class BatchRecordBusinessImpl implements BatchRecordBusiness {

    @Autowired
    private BatchRecordService batchRecordService;

    @Override
    public PageBO<OfflineBatchListVO> pageList(OfflineBatchListQuery query) {
        //开启分页
        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());

        BatchListBO listBO = new BatchListBO();
        listBO.setModeleName(query.getName());
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
}
