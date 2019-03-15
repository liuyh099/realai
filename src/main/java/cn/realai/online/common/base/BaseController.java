package cn.realai.online.common.base;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.realai.online.common.base.BaseRequest;
import cn.realai.online.userandperm.bo.UserLoginInfo;

/**
 * controller的基类
 *
 * @author A lyh
 */
public class BaseController extends BaseRequest {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

     /* 获取Tomcatsession
     *
     * @return
     */
    protected HttpSession getSession() {
        return request.getSession();
    }

    /**
     * 从session获取用户的登录信息
     * @author A tanxh
     * @return null：session中没找到用户信息，用户可能没有登录；
     */
    protected UserLoginInfo getCurrentLoginUserInfo() {
    	UserLoginInfo userLoginInfo = (UserLoginInfo) request.getAttribute(Constants.REQUEST_USER_LOGIN_INFO);
    	if(userLoginInfo != null){
            return (UserLoginInfo) userLoginInfo;
        }
        logger.error("BaseController getCurrentLoginUserInfo 登录信息未获取到");
        return null;
    }

    /**
     * 返回session中存储的当前登录用户userid
     * @author A tanxh
     * @return -1:session中没找到用户信息，用户可能没有登录； 其他：用户id
     */
    protected long getCurrentLoginUserId() {
    	UserLoginInfo userLoginInfo = getCurrentLoginUserInfo();
        if (userLoginInfo != null) {
            return userLoginInfo.getId();
        }
        return -1;
    }

}
