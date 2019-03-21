package cn.realai.online.tool.redis;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import cn.realai.online.common.config.RedisConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@Configuration
public class JedisClusterClient {

    @Autowired
    private RedisConfig redisConfig;

    public JedisCluster getJedisCluster() {
        String[] serverArray = redisConfig.getClusterNodes().split(",");
        Set<HostAndPort> nodes = new HashSet<>();

        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
        return new JedisCluster(nodes, redisConfig.getCommandTimeout());
    }

}
