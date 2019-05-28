package cn.realai.online.core.bussiness;

import cn.realai.online.core.query.realtime.RealTimeData;

public interface RealTimeBussiness {

    String getForecastResult(RealTimeData realTimeData)  throws Exception;

	int recordRequestInformation(String score, String realTimeJson, Long startTime);

}
