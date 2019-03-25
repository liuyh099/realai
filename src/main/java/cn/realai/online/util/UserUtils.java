package cn.realai.online.util;

import cn.realai.online.userandperm.entity.User;
import cn.realai.online.userandperm.entity.UserRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import java.security.Principal;

public class UserUtils {

    public static User getUser() {
        return getLoginUser();
    }

    private static User getLoginUser() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Object object = subject.getPrincipal();
            if (object != null) {
                return (User) object;
            } else {
                throw new UnavailableSecurityManagerException("未登录");
            }
        } finally {

        }
    }

    public static boolean isAdmin(User user) {
        if ("admin".equals(user.getName())) {
            return true;
        }
        return false;
    }

    public static boolean isAdmin() {
        if ("admin".equals(getUser().getName())) {
            return true;
        }
        return false;
    }
}
