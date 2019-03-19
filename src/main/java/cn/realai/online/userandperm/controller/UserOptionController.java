package cn.realai.online.userandperm.controller;

import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.userandperm.bo.MenuTreeNodeBO;
import cn.realai.online.userandperm.bo.UserBO;
import cn.realai.online.userandperm.business.RoleBusiness;
import cn.realai.online.userandperm.business.UserOptionBusiness;
import cn.realai.online.userandperm.vo.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@Api(tags = "登录/菜单/消息通知相关接口")
@RequestMapping("user")
public class UserOptionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserOptionBusiness userOptionBusiness;
    @Autowired
    private RoleBusiness roleBusiness;


    @PostMapping("login")
    @ApiOperation("用户登录Api")
    public Result<MySessionVo> login(@RequestBody UserLoginVo userLoginVo) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userLoginVo.getName(), userLoginVo.getPwd());
        try {
            subject.login(token);
            Serializable session = subject.getSession().getId();
            MySessionVo mySessionVo = new MySessionVo();
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
    @ApiOperation("未认证接口(前端不需要关注)")
    public Result unAuth() {
        return new Result(ResultCode.NO_PERMISSION.getCode(), ResultMessage.NO_PERMISSION.getMsg(), null);
    }


    @PostMapping("/forget")
    @ApiOperation("忘记密码")
    public Result forget(@RequestBody ForgetVo forgetVo) {
        try {
            Boolean flag = userOptionBusiness.forget(forgetVo);
            if (flag) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        } catch (Exception e) {
            logger.error("忘记密码异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @PutMapping("/changePwd")
    @ApiOperation("修改密码")
    public Result changePwd(@RequestBody ChangePwdVO changePwdVO) {
        try {

            //1.检查旧密码
            if (!userOptionBusiness.checkOldPwd(changePwdVO.getOldPwd())) {
                return new Result(ResultCode.DATA_ERROR.getCode(), "旧密码不正确", null);
            }

            if (changePwdVO.getNewPwd().equals(changePwdVO.getOldPwd())) {
                return new Result(ResultCode.DATA_ERROR.getCode(), "新旧密码一致", null);
            }
            if (!userOptionBusiness.changePwd(changePwdVO)) {
                return new Result(ResultCode.DATA_ERROR.getCode(), "更新密码失败", null);
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            logger.error("修改密码异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @GetMapping("/forgetNotice")
    @ApiOperation("忘记密码消息提示")
    public Result<Integer> forgetNotice() {
        try {
            Integer num = userOptionBusiness.forgetNotice();
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), num);
        } catch (Exception e) {
            logger.error("忘记密码消息提示异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @GetMapping("menu")
    @ApiOperation(value = "获得用户菜单")
    public Result<List<IndexMenuTreeNodeVo>> menu() {
        try {
            List<MenuTreeNodeBO> list=roleBusiness.findIndexMenu();
            List<IndexMenuTreeNodeVo> result = JSON.parseArray(JSON.toJSONString(list),IndexMenuTreeNodeVo.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("获得用户菜单异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }

}
