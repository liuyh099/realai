package cn.realai.online.tool.redis;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import cn.realai.online.common.Constant;

@Service
public class RedisClientTemplate {

    private static Logger logger = LoggerFactory.getLogger(RedisClientTemplate.class);

    /*@Autowired
    private JedisClusterClient jedisClusterClient;*/
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;

    /*
     * 设置key-value
     * @param key
     * @param value
     * @param expiredTime 过期时间 单位秒 如果永久不过期,此值传-1
     * @return
     */
    public boolean set(String key, Object value, int expiredTime) {
        try {
            if (expiredTime != -1) {
            	redisTemplate.opsForValue().set("name", "tomcat", expiredTime, TimeUnit.SECONDS);
            } else {
            	redisTemplate.opsForValue().set("name", "tomcat");
            }
            return true;
        } catch (Exception ex) {
            logger.error("setToRedis:{Key:" + key + ",value" + value + "}", ex);
        }
        return false;
    }

    /*
     * 设置key-value 默认过期时间为一个星期
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        return set(key, value, Constant.REDIS_EXPIRED_TIME);
    }

    /*
     * 通过key获取value
     */
    public String get(String key) {
        Object obj = null;
        try {
        	ValueOperations<String, String> value = redisTemplate.opsForValue();
        	obj = value.get(key);
        	if (obj != null ) {
        		return obj.toString();
        	}
        } catch (Exception ex) {
            logger.error("getRedis:{Key:" + key + "}", ex);
        }
        return null;
    }

    /*
     * 删除key
     * @param key
     * @return
     */
    public boolean delete(String key) {
        try {
            return redisTemplate.delete(key);
        } catch (Exception ex) {
            logger.error("delete:{Key:" + key, ex);
        }
        return false;
    }

}
