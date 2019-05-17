package cn.realai.online.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:config.properties"})
public class Config {
	
	private static final String http = "http://"; 
	
    @Value("${python.offline.host}")
    private String PYTHON_OFFLINE_HOST;

    @Value("${python.offline.port}")
    private String PYTHON_OFFLINE_PORT;
    
    @Value("${python.online.host}")
    private String PYTHON_ONLINE_HOST;

    @Value("${python.online.port}")
    private String PYTHON_ONLINE_PORT;
    
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
    
    public String getPythonOfflineUrl() {
        return http + PYTHON_OFFLINE_HOST + ":" + PYTHON_OFFLINE_PORT + TRAIN_URL;
    }
    
    public String getExperimentDrop() {
    	return http + PYTHON_OFFLINE_HOST + ":" + PYTHON_OFFLINE_PORT + EXPERIMENT_DROP;
    }
    
    public String getNginxUrl() {
    	return http + NGINX_URL + ":" + NGINX_PORT;
    }

    public String getModelOfflinePublish() {
    	return http + PYTHON_OFFLINE_HOST + ":" + PYTHON_OFFLINE_PORT + MODEL_OFFLINE_PUBLISH;
    }
    
    public String getModelOnlinePublish() {
    	return http + PYTHON_OFFLINE_HOST + ":" + PYTHON_OFFLINE_PORT + MODEL_ONLINE_PUBLISH;
    }
    
    public String getModelDrop() {
    	return http + PYTHON_OFFLINE_HOST + ":" + PYTHON_OFFLINE_PORT + MODEL_DROP;
    }
    
    public String getModelOfflineBatch() {
    	return http + PYTHON_OFFLINE_HOST + ":" + PYTHON_OFFLINE_PORT + MODEL_OFFLINE_BATCH;
    }
    
    public String getRealtimeUrl() {
    	return http + PYTHON_ONLINE_HOST + ":" + PYTHON_ONLINE_PORT + REALTIME_URL;
    } 
    
    public String getModelDailyBatch() {
    	return http + PYTHON_ONLINE_HOST + ":" + PYTHON_ONLINE_PORT + MODEL_DAILY_BATCH;
    }
    
}
