package cn.realai.online.core.bussiness;

import cn.realai.online.core.entity.TuningRecord;
import cn.realai.online.lic.LicenseException;

public interface TuningRecordBussiness {

    //创建调优记录
    Integer createTuningRecord(Long modelId, String securityKey)  throws LicenseException;

    //获取可用调优记录
    TuningRecord selectValidTuningRecord(Long serviceId);

}
