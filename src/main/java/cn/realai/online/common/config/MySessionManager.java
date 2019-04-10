package cn.realai.online.common.config;

import cn.realai.online.util.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.exception.SerializationException;
import org.crazycake.shiro.serializer.RedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class MySessionManager extends DefaultWebSessionManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String a = "cn.realai.online.common.config.MyShiroRealm.authorizationCache:";

    RedisCacheManager redisCacheManager;

    private static final String AUTHORIZATION = "mySessionId";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MySessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }

    @Override
    public void validateSessions() {
        super.validateSessions();
        Collection<Session> sessions = getActiveSessions();
        if (!CollectionUtils.isEmpty(sessions)) {
            if (redisCacheManager == null) {
                synchronized (a) {
                    redisCacheManager = (RedisCacheManager) SpringContextUtils.getBean("myRedisCacheManager");
                }
            }
            for (Session session : sessions) {
                if(session==null){
                    continue;
                }
                Date date = session.getLastAccessTime();
                long last = date.getTime();
                long current = System.currentTimeMillis();
                Object o = session.getAttribute("userId");
                if(o==null){
                    if (current - last > getSessionValidationInterval()) {
                        super.delete(session);
                    }
                }else {
                    if (current - last > getGlobalSessionTimeout()) {
                        if (o != null) {
                            String userId = (String) o;
                            String preFix = redisCacheManager.getKeyPrefix();
                            RedisSerializer redisSerializer = redisCacheManager.getKeySerializer();
                            try {
                                byte[] key = redisSerializer.serialize(preFix + a + userId);
                                redisCacheManager.getRedisManager().del(key);
                            } catch (SerializationException e) {
                                e.printStackTrace();
                            }
                        }
                        super.delete(session);
                    }
                }

            }

        }
    }

    /**
     * 根据用户ID删除session
     * @param userId
     */
    @Async
    public void deleteSessionsByUserId(Long userId) {
        Collection<Session> sessions = getActiveSessions();
        if (!CollectionUtils.isEmpty(sessions)) {
            if (redisCacheManager == null) {
                synchronized (a) {
                    redisCacheManager = (RedisCacheManager) SpringContextUtils.getBean("myRedisCacheManager");
                }
            }
            for (Session session : sessions) {
                if(session==null){
                    continue;
                }
                Object o = session.getAttribute("userId");
                if (o != null) {
                    String rdsUserId = (String) o;
                    if (userId.toString().equals(rdsUserId)) {
                        String preFix = redisCacheManager.getKeyPrefix();
                        RedisSerializer redisSerializer = redisCacheManager.getKeySerializer();
                        try {
                            byte[] key = redisSerializer.serialize(preFix + a + userId);
                            redisCacheManager.getRedisManager().del(key);
                        } catch (SerializationException e) {
                            e.printStackTrace();
                        }
                        super.delete(session);
                    }
                }

            }
        }
    }
}
