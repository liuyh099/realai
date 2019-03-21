package cn.realai.online.core.bussiness.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.VariableDataBO;
import cn.realai.online.core.bussiness.VariableDataBusiness;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.query.VariableDataQuery;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.core.vo.VariableDataVO;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 变量数据的业务实现
 */
@Service
@Transactional
public class VariableDataBussinessImpl implements VariableDataBusiness {


    @Autowired
    private VariableDataService variableDataService;

    /**
     * 根据实验id和模式类型等分页查询变量数据
     * @param variableDataQuery
     * @return
     */
    @Override
    public PageBO<VariableDataVO> pageList(VariableDataQuery variableDataQuery) {
        //开启分页
        Page page = PageHelper.startPage(variableDataQuery.getPageNum(), variableDataQuery.getPageSize());

        //执行条件查询
        VariableData variableData = new VariableData();
        BeanUtils.copyProperties(variableDataQuery, variableData);
        List<VariableDataBO> list = variableDataService.findList(variableData);


        //处理查询结果
        List<VariableDataVO> result = JSON.parseArray(JSON.toJSONString(list), VariableDataVO.class);
        PageBO<VariableDataVO> pageBO = new PageBO<VariableDataVO>(result, variableDataQuery.getPageSize(), variableDataQuery.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }
}
