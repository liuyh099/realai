package cn.realai.online.core.bussiness.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ModelDetailBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.bussiness.ModelBusiness;
import cn.realai.online.core.dao.ModelPerfomanceDao;
import cn.realai.online.core.entity.ModelPerformance;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.service.ModelPerformanceService;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.vo.ModelDetailVO;
import cn.realai.online.core.vo.ModelListVO;
import cn.realai.online.core.vo.ModelPerformanceVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 18:10
 */
@Service
public class ModelBusinessImpl implements ModelBusiness {

    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelPerformanceService modelPerformanceService;

    @Override
    public PageBO<ModelListVO> pageList(ModelListQuery query) {
        //开启分页
        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());

        //执行条件查询
        List<ModelListBO> list = modelService.selectList(query.getName(), query.getServiceId());

        //处理查询结果
        List<ModelListVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (ModelListBO item:list) {
                ModelListVO voItem = new ModelListVO();
                BeanUtils.copyProperties(item, voItem);
                voList.add(voItem);
            }
        }
        PageBO<ModelListVO> pageBO = new PageBO<>(voList, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    @Override
    public ModelDetailVO selectModelDetail(Long modelId) {
        ModelDetailVO detailVO = new ModelDetailVO();
        //读取模型详情
        ModelDetailBO detailBO = modelService.selectDetail(modelId);
        if (detailBO != null) {
            BeanUtils.copyProperties(detailBO, detailVO);
        }
        //读取模型表现
        List<ModelPerformance> performanceList = modelPerformanceService.selectList(modelId);
        if (performanceList != null && !performanceList.isEmpty()) {
            List<ModelPerformanceVO> performanceVOList = new ArrayList<>();
            for (ModelPerformance item:performanceList) {
                ModelPerformanceVO itemVO = new ModelPerformanceVO();
                BeanUtils.copyProperties(item, itemVO);
                performanceVOList.add(itemVO);
            }
            detailVO.setPerformanceList(performanceVOList);
        }
        return detailVO;
    }
}
