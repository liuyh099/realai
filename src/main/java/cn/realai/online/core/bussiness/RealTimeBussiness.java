package cn.realai.online.core.bussiness;

import java.util.Map;

public interface RealTimeBussiness {

	String getForecastResult(Map<String, Object> map, long id);

}
