package cn.realai.online.util;

import cn.realai.online.userandperm.dao.UserDao;
import cn.realai.online.userandperm.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import java.security.Principal;

public class UserUtils {

    public static User getUser() {
        Principal principal = getPrincipal();
        User user = (User) principal;
        return user;
    }

    private static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e) {

        }
        return null;
    }

}
