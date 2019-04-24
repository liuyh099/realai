package cn.realai.online.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import cn.realai.online.util.HttpUtil;

@Configuration
@PropertySource({"classpath:config.properties"})
public class Config {
	
	private Logger logger = LoggerFactory.getLogger(Config.class);
	
	private static final String http = "http://"; 
	
    @Value("${python.host}")
    private String PYTHON_HOST;

    @Value("${python.port}")
    private String PYTHON_PORT;

    @Value("${train.url}")
    private String TRAIN_URL;
    
    @Value("${experiment.drop}")
    private String EXPERIMENT_DROP;
    
    @Value("${model.online.publish}")
    private String MODEL_ONLINE_PUBLISH;
    
    @Value("${model.offline.publish}")
    private String MODEL_OFFLINE_PUBLISH;
    
    @Value("${model.drop}")
    private String MODEL_DROP;
    
    @Value("${model.offline.batch}")
    private String MODEL_OFFLINE_BATCH;
    
    @Value("${nginx.url}")
    private String NGINX_URL;
    
    @Value("${nginx.port}")
    private String NGINX_PORT;
    
    @Value("${realtime.url}")
    private String REALTIME_URL;
    
    @Value("${model.daily.batch}")
    private String MODEL_DAILY_BATCH;
    
    public String getPythonUrl() {
        return http + PYTHON_HOST + ":" + PYTHON_PORT + TRAIN_URL;
    }
    
    public String getExperimentDrop() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + EXPERIMENT_DROP;
    }
    
    public String getNginxUrl() {
    	return http + NGINX_URL + ":" + NGINX_PORT;
    }

    public String getModelOfflinePublish() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + MODEL_OFFLINE_PUBLISH;
    }
    
    public String getModelOnlinePublish() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + MODEL_ONLINE_PUBLISH;
    }
    
    public String getModelDrop() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + MODEL_DROP;
    }
    
    public String getModelOfflineBatch() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + MODEL_OFFLINE_BATCH;
    }
    
    public String getRealtimeUrl() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + REALTIME_URL;
    } 
    
    public String getModelDailyBatch() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + MODEL_DAILY_BATCH;
    }
    
}
