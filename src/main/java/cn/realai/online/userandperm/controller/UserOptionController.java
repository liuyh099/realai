package cn.realai.online.userandperm.controller;

import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.userandperm.vo.MySessionVo;
import cn.realai.online.userandperm.vo.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "登录/菜单/消息通知相关接口")
@RequestMapping("user")
public class UserOptionController {

    @PostMapping("login")
    @ApiOperation("用户登录Api")
    public Result<MySessionVo> login(@RequestBody UserLoginVo userLoginVo){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userLoginVo.getName(), userLoginVo.getPwd());
        try {
            subject.login(token);
            Serializable session=subject.getSession().getId();
            MySessionVo mySessionVo=new MySessionVo();
            mySessionVo.setSessionId(session);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), mySessionVo);
        } catch (IncorrectCredentialsException e) {
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.PWD_ERROR.getMsg(), null);
        } catch (LockedAccountException e) {
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.LOCKED_ACCOUNT.getMsg(), null);
        } catch (AuthenticationException e) {
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.NOT_EXISTS_USER.getMsg(), null);
        } catch (Exception e) {
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.PROGRAM_EXCEPTION.getMsg(), null);
        }
    }

    @GetMapping("/unAuth")
    public Result unAuth() {
        return new Result(ResultCode.NO_PERMISSION.getCode(), ResultMessage.NO_PERMISSION.getMsg(), null);
    }

}
