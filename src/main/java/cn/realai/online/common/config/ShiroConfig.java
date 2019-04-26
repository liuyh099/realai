package cn.realai.online.common.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisClusterManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限框架
 */
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.cache.clusterNodes}")
    private String host;


    @Value("${session.timeout}")
    private Long sessionTimeout;

    @Value("${session.sessionValidationInterval}")
    private Long sessionValidationInterval;

    @Value("${spring.redis.cache.redisAlone:true}")
    private boolean redisAlone;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/user/logout", "anon");
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/forget", "anon");
        filterChainDefinitionMap.put("/offlineBatch/**", "anon");
        filterChainDefinitionMap.put("/experimental/**", "anon");
        filterChainDefinitionMap.put("/modelCall/**", "anon");
        filterChainDefinitionMap.put("/realTime/**", "anon");
        //filterChainDefinitionMap.put("/experimental/train/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setLoginUrl("/user/unAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-1");//散列算法:这里使用SHA-1算法;
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数
        return hashedCredentialsMatcher;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setGlobalSessionTimeout(sessionTimeout); //Session时间默认30 分钟 乘以6 为3个小时
        mySessionManager.setSessionDAO(redisSessionDAO());
        mySessionManager.setSessionValidationInterval(sessionValidationInterval);
        mySessionManager.setSessionValidationSchedulerEnabled(true);
        return mySessionManager;
    }


    public RedisClusterManager clusterRedisManager() {
        RedisClusterManager redisClusterManager = new RedisClusterManager();
        redisClusterManager.setHost(host);
        return redisClusterManager;
    }

    public RedisManager aloneRedisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        return redisManager;
    }


    @Bean("myRedisCacheManager")
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        if(redisAlone){
            redisCacheManager.setRedisManager(aloneRedisManager());
        }else {
            redisCacheManager.setRedisManager(clusterRedisManager());
        }
        return redisCacheManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        if(redisAlone){
            redisSessionDAO.setRedisManager(aloneRedisManager());
        }else {
            redisSessionDAO.setRedisManager(clusterRedisManager());
        }
//      Custom your redis key prefix for session management, if you doesn't define this parameter,
//      shiro-redis will use 'shiro_redis_session:' as default prefix
//      redisSessionDAO.setKeyPrefix("");
        return redisSessionDAO;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注册全局异常处理
     *
     * @return
     */
    @Bean(name = "exceptionHandler")
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new MyExceptionHandler();
    }

}
