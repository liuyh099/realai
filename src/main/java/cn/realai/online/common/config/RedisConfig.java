package cn.realai.online.common.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:config.properties"})
public class RedisConfig {

	@Value("${spring.redis.database}")
    private int database;
	
	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
	private String port;
    
    @Bean
	Redisson redissonSentinel() {
		//支持单机，主从，哨兵，集群等模式
		//此为哨兵模式
		Config config = new Config();
		config.useSentinelServers()
			.setDatabase(database)
			.addSentinelAddress(host + port);
		return (Redisson)Redisson.create(config);
	}

}
