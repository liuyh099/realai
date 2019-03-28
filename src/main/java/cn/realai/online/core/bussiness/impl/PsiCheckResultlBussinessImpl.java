package cn.realai.online.core.bussiness.impl;

import cn.realai.online.common.Constant;
import cn.realai.online.core.bo.PsiResultBO;
import cn.realai.online.core.bussiness.PsiCheckResultBussiness;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.PSICheckResult;
import cn.realai.online.core.entity.TuningRecord;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.service.PsiCheckResultService;
import cn.realai.online.core.service.TuningRecordService;
import cn.realai.online.core.vo.PsiCheckVO;
import cn.realai.online.core.vo.PsiResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 18:10
 */
@Service
public class PsiCheckResultlBussinessImpl implements PsiCheckResultBussiness {

    @Autowired
    private PsiCheckResultService psiChekcResultService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private TuningRecordService tuningRecordService;

    @Override
    public List<PsiResultVO> selectList(Long modelId) {
        List<PsiResultVO> psiResultVOList = new ArrayList<>();
        List<PsiResultBO> psiResultBOList = psiChekcResultService.selectList(modelId);
        if (psiResultBOList != null && !psiResultBOList.isEmpty()) {
            psiResultBOList.forEach(psi -> {
                PsiResultVO itemVO = new PsiResultVO();
                BeanUtils.copyProperties(psi, itemVO);
                itemVO.setAlerName(PSICheckResult.STATUS.getDesc(itemVO.getAler()));
                psiResultVOList.add(itemVO);
            });
        }
        return psiResultVOList;
    }

    @Override
    public PsiCheckVO checkPsi(Long modelId) {
        Model model = modelService.get(modelId);
        Assert.notNull(model, "模型不存在无法调优");

        //判断当前是否有调优记录
        TuningRecord record = new TuningRecord();
        record.setServiceId(model.getServiceId());
        record.setStatus(TuningRecord.STATUS.VALID.value);
        List<TuningRecord> tuningRecords = tuningRecordService.findList(record);
        boolean flag = tuningRecords == null || tuningRecords.isEmpty();
        String reason = "已有调优尚未完成暂时无法新增调优";

        PsiCheckVO checkVO = new PsiCheckVO();
        checkVO.setModelId(modelId);
        if (!flag) {
            checkVO.setPsiFlag(false);
            checkVO.setPsiReason(reason);
            checkVO.setKeyFlag(false);
            checkVO.setKeyReason(reason);
        } else {
            Double maxPsi = psiChekcResultService.selectMaxPsi(modelId);
            checkVO.setMaxPsi(maxPsi);
            checkVO.setPsiFlag(maxPsi > Constant.PSI_ALER_VALUE);
            if (!checkVO.isPsiFlag()) {
                checkVO.setPsiReason("PSI值未达标");
            }
            checkVO.setKeyFlag(true);
        }

        return checkVO;
    }

    @Override
    public boolean psiCheckNotice() {
        Integer result = psiChekcResultService.findMaxPsiTotal();
        if (result > 0) {
            return true;
        }
        return false;
    }
}
