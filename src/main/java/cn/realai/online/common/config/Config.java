package cn.realai.online.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:config.properties"})
public class Config {

	@Value("${python.host}")
	private String PYTHON_HOST;
	
	@Value("${python.port}")
	private String PYTHON_PORT;
	
	@Value("${python.url}")
	private String PYTHON_URL;
	
	public String getUrl() {
		return PYTHON_HOST + ":" + PYTHON_PORT + PYTHON_URL;
	}
	
}
