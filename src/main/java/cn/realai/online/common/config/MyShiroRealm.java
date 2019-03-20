package cn.realai.online.common.config;

import cn.realai.online.userandperm.entity.User;
import cn.realai.online.userandperm.service.MenuService;
import cn.realai.online.userandperm.service.RoleService;
import cn.realai.online.userandperm.service.UserService;
import cn.realai.online.util.EncodingPasswordUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.crazycake.shiro.RedisSessionDAO;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Resource
    private UserService userService;

    @Resource
    private RedisSessionDAO redisSessionDAO;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        try {

            authorizationInfo.addStringPermission("permission:user:list");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        String pwd = new String((char[]) token.getCredentials());
        User user = userService.findByNameOrPhoneNumber(username);

        if (user == null) {
            return null;
        }
        byte[] salt = HexUtils.fromHexString(user.getPwd().substring(0, 16));
        String pwd1 = EncodingPasswordUtils.encodingPassword(pwd, salt);
        if (username.equals(user.getName()) && pwd1.equals(user.getPwd())) {
            SessionsSecurityManager securityManager = (SessionsSecurityManager) SecurityUtils.getSecurityManager();
            DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
            // 获取所有session
            Collection<Session> sessions = redisSessionDAO.getActiveSessions();
            for (Session session : sessions) {
                String userId = (String) session.getAttribute("userId");
                // 如果session里面有当前登陆的，则证明是重复登陆的，则将其剔除
                if (userId != null && !"".equals(userId)) {
                    if (userId.equals(user.getId() + "")) {
                        session.setTimeout(0);
                        sessionManager.getSessionDAO().delete(session);
                    }
                }
            }
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户名
                user.getPwd().substring(16), //密码
                ByteSource.Util.bytes(salt),//salt=username+salt
                getName()  //realm name
        );

        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userId", user.getId() + "");

        return authenticationInfo;
    }
}
