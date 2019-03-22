package cn.realai.online.core.bussiness;

public interface TuningRecordBussiness {

    //创建调优记录
    Integer createTuningRecord(Long modelId, String securityKey);

}
