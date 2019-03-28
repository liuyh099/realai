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

}
