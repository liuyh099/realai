package cn.realai.online.common.config;

import cn.realai.online.userandperm.entity.User;
import cn.realai.online.userandperm.service.MenuService;
import cn.realai.online.userandperm.service.RoleService;
import cn.realai.online.userandperm.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.crazycake.shiro.RedisSessionDAO;  

import javax.annotation.Resource;
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

    @Resource
    private SingleLogin singleLogin;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        user = userService.get(user.getId());
        try {
            List<String> permissionList = null;
            if (user.getName().equals("admin")) {
                //获得所有的权限
                permissionList = menuService.getAllPermission();
            } else {
                if (user.getRoleId() != null) {
                    permissionList = menuService.getPermissionByRoleId(user.getRoleId());
                }

            }
            if (!CollectionUtils.isEmpty(permissionList)) {
                for (String permission : permissionList) {
                    if (!StringUtils.isBlank(permission)) {
                        String[] permissions = permission.split(",");
                        for (String result : permissions) {
                            authorizationInfo.addStringPermission(result);
                        }
                    }
                }
            }

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
        Session session = SecurityUtils.getSubject().getSession();
        singleLogin.singleLogin(username, pwd, user, salt, session.getId());

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //用户名
                user.getPwd().substring(16), //密码
                ByteSource.Util.bytes(salt),//salt=username+salt
                getName()  //realm name
        );


        session.setAttribute("userId", user.getId() + "");

        return authenticationInfo;
    }
}
