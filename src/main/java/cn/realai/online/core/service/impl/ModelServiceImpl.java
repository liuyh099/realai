package cn.realai.online.core.service.impl;

import cn.realai.online.core.bo.ModelDetailBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.dao.ModelDao;
import cn.realai.online.core.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 16:52
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelDao modelDao;

    @Override
    public List<ModelListBO> selectList(String name, Long serviceId) {
        return modelDao.selectList(name, serviceId);
    }

    @Override
    public ModelDetailBO selectDetail(Long modelId) {
        return modelDao.selectDetail(modelId);
    }
}
