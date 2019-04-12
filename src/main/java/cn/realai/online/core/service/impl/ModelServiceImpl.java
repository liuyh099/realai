package cn.realai.online.core.service.impl;

import cn.realai.online.core.bo.ModelDetailBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.dao.ModelDao;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    public Model get(Long modelId) {
        return modelDao.get(modelId);
    }

    @Override
    public List<ModelListBO> selectList(String name, Long serviceId) {
        return modelDao.selectList(name, serviceId);
    }

    @Override
    public ModelDetailBO selectDetail(Long modelId) {
        return modelDao.selectDetail(modelId);
    }

    @Override
    public Integer update(Model model) {
        return modelDao.update(model);
    }

    @Override
    public Model selectLatest() {
        return modelDao.selectLatest();
    }

    @Override
    public Model selectByServiceId(Long serviceId) {
        Assert.notNull(serviceId, "服务Id不能为空");
        return modelDao.selectByServiceId(serviceId);
    }

    @Override
    public List<Model> findList(Model model) {
        return modelDao.findList(model);
    }

    @Override
    public int insert(Model model) {
        return modelDao.insert(model);
    }

    @Override
    public void offline(Long serviceId, Long id, String value) {
         modelDao.offline(serviceId,id,value);
    }

    @Override
    public List<Model> findModelOptionHistory(Long serviceId) {
        return  modelDao.findModelOptionHistory(serviceId);
    }

    @Override
    public Integer selectCountByServiceId(Long serviceId) {
        return modelDao.selectCountByServiceId(serviceId);
    }

    @Override
    public List<Model> selectAllModelNameList(Long serviceId) {
        return modelDao.selectAllModelNameList(serviceId);
    }

    @Override
    public List<Model> findLastModelSelect() {
        return modelDao.findLastModelSelect();
    }

	@Override
	public Model selectOnlineModelByServiceId(Long serviceId) {
		return modelDao.selectModelByServiceIdAndStatus(serviceId, Model.RELEASE_STATUS.ONLINE.value);
	}
}
