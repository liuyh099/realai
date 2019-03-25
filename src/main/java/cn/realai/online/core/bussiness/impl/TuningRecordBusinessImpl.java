package cn.realai.online.core.bussiness.impl;

import cn.realai.online.core.bussiness.PsiCheckResultBussiness;
import cn.realai.online.core.bussiness.ServiceBussiness;
import cn.realai.online.core.bussiness.TuningRecordBussiness;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.TuningRecord;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.service.TuningRecordService;
import cn.realai.online.core.vo.PsiCheckVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;


/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-22 18:15
 */
@Component
public class TuningRecordBusinessImpl implements TuningRecordBussiness {

    @Autowired
    private PsiCheckResultBussiness psiCheckResultBussiness;
    @Autowired
    private TuningRecordService tuningRecordService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ServiceBussiness serviceBussiness;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer createTuningRecord(Long modelId, String securityKey) {
        Assert.notNull(modelId, "模型ID不能为空");
        PsiCheckVO checkVO = psiCheckResultBussiness.checkPsi(modelId);
        Assert.isTrue(checkVO != null && checkVO.isFlag(), "该模型不存在或PSI未达标不允许调优");
        Model model = modelService.get(modelId);
        Assert.notNull(model, "该模型没有对应服务");

        if (!StringUtils.isNotEmpty(securityKey)) {
            //Psi调优更新其他记录状态
            TuningRecord item = new TuningRecord();
            item.setStatus(TuningRecord.STATUS.INVALID.value);
            item.setServiceId(model.getServiceId());
            item.setType(TuningRecord.TYPE.PSI.value);
            tuningRecordService.updateByServiceIdAndType(item);
        } else {
            //校验密钥串是否可用并绑定
           // serviceBussiness.bindTuningSecretKey(model.getServiceId(), securityKey);
            //密钥调优更新其他记录状态
            TuningRecord item = new TuningRecord();
            item.setStatus(TuningRecord.STATUS.INVALID.value);
            item.setServiceId(model.getServiceId());
            item.setType(TuningRecord.TYPE.KEY.value);
            tuningRecordService.updateByServiceIdAndType(item);
        }

        //创建新的调优记录
        TuningRecord record = new TuningRecord();
        record.setCreateTime(new Date().getTime());
        record.setUpdateTime(record.getCreateTime());
        record.setStatus(TuningRecord.STATUS.VALID.value);
        if (StringUtils.isNotEmpty(securityKey)) {
            record.setType(TuningRecord.TYPE.KEY.value);
        } else {
            record.setType(TuningRecord.TYPE.PSI.value);
        }
        record.setMaxPsi(checkVO.getMaxPsi());
        record.setSecuriyKey(securityKey);
        record.setModelId(modelId);
        record.setServiceId(model.getServiceId());
        return tuningRecordService.insert(record);
    }

    @Override
    public TuningRecord selectValidTuningRecord(Long serviceId) {
        Assert.notNull(serviceId, "调优服务ID不能为空");
        TuningRecord record = new TuningRecord();
        record.setServiceId(serviceId);
        record.setStatus(TuningRecord.STATUS.VALID.value);
        List<TuningRecord> records = tuningRecordService.findList(record);
        if (records == null || records.isEmpty()) {
            return null;
        }
        records.sort((TuningRecord r1, TuningRecord r2) -> {
            if (r1.getType() != null && r2.getType() != null) {
                return r1.getType().compareTo(r2.getType());
            }
            return 0;
        });
        return records.get(0);
    }
}
