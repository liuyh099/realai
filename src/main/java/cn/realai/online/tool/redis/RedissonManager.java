package cn.realai.online.tool.redis;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.realai.online.common.config.RedisConfig;


@Configuration
public class RedissonManager {

	@Autowired
    private RedisConfig redisConfig;
	
	@Bean
    public RedissonClient getRedisson(){
    	String[] nodes = redisConfig.getClusterNodes().split(",");
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
    	for(int i=0;i<nodes.length;i++){
    	    nodes[i] = "redis://"+nodes[i];
    	} 
    	RedissonClient redisson = null;
        Config config = new Config();
        config.useClusterServers() //这是用的集群server
        .setScanInterval(2000) //设置集群状态扫描时间 
        .addNodeAddress(nodes);
        redisson = Redisson.create(config);
        try {
			System.out.println(redisson.getConfig().toJSON().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("qwe");
        //可通过打印redisson.getConfig().toJSON().toString()来检测是否配置成功
        return redisson;
    }
	
}
