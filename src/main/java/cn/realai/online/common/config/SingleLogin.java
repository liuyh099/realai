package cn.realai.online.common.config;

import cn.realai.online.userandperm.entity.User;
import cn.realai.online.util.EncodingPasswordUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author hyx
 * @date 2019/4/10 13:03
 * @email 996463990@qq.com
 */
@Component
public class SingleLogin {

    @Value("${system.singleLogin}")
    private boolean singleLogin;


    @Resource
    private RedisSessionDAO redisSessionDAO;

    /**
     * 排他登录
     * @param username
     * @param pwd
     * @param user
     * @param salt
     */
    @Async
    public void singleLogin(String username, String pwd, User user, byte[] salt, Serializable sessionId) {
        if(singleLogin){
            String pwd1 = EncodingPasswordUtils.encodingPassword(pwd, salt);
            if (username.equals(user.getName()) && pwd1.equals(user.getPwd())) {
                SessionsSecurityManager securityManager = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
                DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
                // 获取所有session
                Collection<Session> sessions = redisSessionDAO.getActiveSessions();
                for (Session session : sessions) {
                    if(session==null || sessionId.equals(session.getId())){
                        continue;
                    }
                    Object o =  session.getAttribute("userId");
                    // 如果session里面有当前登陆的，则证明是重复登陆的，则将其剔除
                    if(o!=null){
                        String userId= (String) o;
                        if (userId != null && !"".equals(userId)) {
                            if (userId.equals(user.getId() + "")) {
                                session.setTimeout(0);
                                sessionManager.getSessionDAO().delete(session);
                            }
                        }
                    }

                }
            }
        }
    }

    @Async
    public void deleteSessionsByUserId(Long userId){
        SessionsSecurityManager securityManager = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
        MySessionManager sessionManager = (MySessionManager) securityManager.getSessionManager();
        sessionManager.deleteSessionsByUserId(userId);
    }

    @Async
    public void clearPermissionByUserId(Long userId) {
        SessionsSecurityManager securityManager = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
        MySessionManager sessionManager = (MySessionManager) securityManager.getSessionManager();
        sessionManager.clearPermissionByUserId(userId+"");
    }
}
