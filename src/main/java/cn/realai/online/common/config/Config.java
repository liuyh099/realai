package cn.realai.online.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:config.properties"})
public class Config {
	
	private static final String http = "http://"; 
	
    @Value("${python.host}")
    private String PYTHON_HOST;

    @Value("${python.port}")
    private String PYTHON_PORT;

    @Value("${python.url}")
    private String PYTHON_URL;
    
    @Value("${model.publish}")
    private String MODEL_PUBLISH;
    
    @Value("${model.drop}")
    private String MODEL_DROP;
    
    @Value("${model.offline.batch}")
    private String MODEL_OFFLINE_BATCH;
    
    @Value("${nginx.url}")
    private String NGINX_URL;
    
    @Value("${nginx.port}")
    private String NGINX_PORT;
    
    public String getPythonUrl() {
        return http + PYTHON_HOST + ":" + PYTHON_PORT + PYTHON_URL;
    }
    
    public String getNginxUrl() {
    	return http + NGINX_URL + ":" + NGINX_PORT;
    }

    public String getModelPublish() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + MODEL_PUBLISH;
    }
    
    public String getModelDrop() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + MODEL_DROP;
    }
    
    public String getModelOfflineBatch() {
    	return http + PYTHON_HOST + ":" + PYTHON_PORT + MODEL_OFFLINE_BATCH;
    }
    
}
