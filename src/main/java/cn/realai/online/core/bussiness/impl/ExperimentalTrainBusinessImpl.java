package cn.realai.online.core.bussiness.impl;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.common.Constant;
import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.ExperimentalTrainDetailBO;
import cn.realai.online.core.bo.VariableDataBO;
import cn.realai.online.core.bussiness.ExperimentalTrainBusiness;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.query.ExperimentalTrainCreateModelDataQuery;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.query.PageQuery;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.core.vo.ExperimentalTrainSelectFileVO;
import cn.realai.online.core.vo.ExperimentalTrainVO;
import cn.realai.online.tool.lock.MysqlLock;
import cn.realai.online.tool.redis.RedisClientTemplate;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实验训练的业务实现
 */
@Service
@Transactional(readOnly = true)
public class ExperimentalTrainBusinessImpl implements ExperimentalTrainBusiness {

    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private MysqlLock mysqlLock;

    @Autowired
    private VariableDataService variableDataService;

    /**
     * 根据实验名称和状态等分页查询实验列表
     *
     * @param experimentalTrainQuery 实验列表查询条件
     * @return
     */
    @Override
    public PageBO<ExperimentalTrainVO> pageList(ExperimentalTrainQuery experimentalTrainQuery) {
        //开启分页
        Page page = PageHelper.startPage(experimentalTrainQuery.getPageNum(), experimentalTrainQuery.getPageSize());

        //执行条件查询
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentalTrainQuery, experiment);
        List<ExperimentBO> list = experimentService.findList(experiment);

        //处理查询结果
        List<ExperimentalTrainVO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentalTrainVO.class);
        PageBO<ExperimentalTrainVO> pageBO = new PageBO<ExperimentalTrainVO>(result, experimentalTrainQuery.getPageSize(), experimentalTrainQuery.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    /**
     * 根据实验训练id删除实验训练
     *
     * @param ids 实验训练id集合
     */
    @Override
    @Transactional(readOnly = false)
    public Integer deleteExperimentByIds(List<Long> ids) {
        int count = experimentService.deleteExperimentByIds(ids);
        return count;
    }


    /*
     * 训练
     * @param trainId 实验id
     */
    @Override
    public int train(long experimentId) {
        //获取训练锁
        MLock mlock = experimentService.getExperimentTrainMLockInstance(experimentId);
        if (mlock.tryLock()) {
            return -1;
        }

        //修改试验状态
        int ret = experimentService.updateExperimentStatus(experimentId, Experiment.STATUS_TRAINING);
        ExperimentBO experimentBO = experimentService.selectExperimentById(experimentId);
        trainService.training(experimentBO);
        return ret;
    }


    @Override
    public void testPreprocess(long experimentId) {
        ExperimentBO experimentBO = experimentService.selectExperimentById(experimentId);
        trainService.preprocess(experimentBO);
    }

    /**
     * 根据实验ID获取实验详情
     *
     * @param experimentId 实验id
     * @return 实验详情
     */
    @Override
    public ExperimentalTrainDetailBO detail(long experimentId) {
        ExperimentalTrainDetailBO experimentalTrainDetailBO = experimentService.selectExperimentDetailById(experimentId);
        return experimentalTrainDetailBO;
    }

    @Override
    @Transactional(readOnly = false)
    public Long selectFileAdd(ExperimentBO experimentBO) {
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentBO, experiment);
        experiment.setCreateTime(System.currentTimeMillis());
        experiment.setStatus(Experiment.STATUS_FILE);
        experiment.setReleasStatus(Experiment.RELEAS_NO);
        return experimentService.insert(experiment);
    }

    @Override
    public boolean checkTrainName(String name, Long id) {
        return experimentService.checkTrainName(name, id);

    }

    @Override
    public ExperimentBO selectById(Long trainId) {
        Experiment experiment = experimentService.selectExperimentById(trainId);
        ExperimentBO experimentBO = new ExperimentBO();
        BeanUtils.copyProperties(experiment, experimentBO);
        return experimentBO;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer selectFileUpdate(ExperimentBO experimentBO) {
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentBO, experiment);
        return experimentService.selectFileUpdate(experiment);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateParam(ExperimentBO experimentBO) {
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentBO, experiment);
        return experimentService.updateParam(experiment);
    }

    @Override
    public PageBO<VariableDataBO> pageHomOrHemeList(ExperimentalTrainCreateModelDataQuery query) {
        ExperimentBO experimentBO = selectById(query.getId());
        if(experimentBO!=null && new Integer(2).equals(experimentBO.getPreFinish())){
             //可以查询训练结果
            VariableData variableData =new VariableData();
            variableData.setExperimentId(query.getId());
            variableData.setVariableType(query.getVariableType());
            Page page=PageHelper.startPage(query.getPageNum(),query.getPageSize());
            List<VariableData> list=variableDataService.findList(variableData);
            if(CollectionUtils.isEmpty(list)){
                return null;
            }
            List<VariableDataBO>  result=JSON.parseArray(JSON.toJSONString(list),VariableDataBO.class);
            PageBO<VariableDataBO> pageBO = new PageBO<VariableDataBO>(result, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
            return pageBO;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteVariableData(Long experimentId, List<Long> ids) {
        variableDataService.deleteVariableData(experimentId,ids);
    }
}
