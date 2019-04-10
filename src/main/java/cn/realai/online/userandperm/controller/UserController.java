package cn.realai.online.userandperm.controller;

import cn.realai.online.common.config.MySessionManager;
import cn.realai.online.common.config.SingleLogin;
import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.vo.IdVO;
import cn.realai.online.userandperm.bo.RoleBO;
import cn.realai.online.userandperm.bo.UserBO;
import cn.realai.online.userandperm.business.RoleBusiness;
import cn.realai.online.userandperm.business.UserBusiness;
import cn.realai.online.userandperm.query.UserPageQuery;
import cn.realai.online.userandperm.vo.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理controller
 */
@RestController
@Api(tags = "权限管理-用户管理")
@RequestMapping("permission/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserBusiness userBusiness;

    @Autowired
    private RoleBusiness roleBusiness;

    @Autowired
    private SingleLogin singleLogin;

    @RequiresPermissions("permission:user")
    @GetMapping()
    @ApiOperation(value = "查询用户列表")
    public Result<PageBO<UserVO>> list(UserPageQuery pageQuery) {
        try {
            PageBO<UserBO> pageBO = userBusiness.list(pageQuery);
            List<UserVO> result = JSON.parseArray(JSON.toJSONString(pageBO.getPageContent()), UserVO.class);
            PageBO<UserVO> page = new PageBO<UserVO>(result, pageQuery.getPageSize(), pageQuery.getPageNum(), pageBO.getCount(), pageBO.getTotalPage());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), page);
        } catch (Exception e) {
            logger.error("查询用户列表异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }

    @RequiresPermissions("permission:user")
    @GetMapping("checkName/{name}")
    @ApiOperation(value = "检查用户名（true表示检查通过）")
    @ApiImplicitParam(name = "name", value = "用户名", required = true, type = "path")
    public Result<Boolean> checkName(@PathVariable String name) {
        try {
            Boolean flag = userBusiness.checkUserName(name);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), flag);
        } catch (Exception e) {
            logger.error("检查用户名异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("permission:user")
    @GetMapping("checkPhoneNumber/{phoneNumber}")
    @ApiOperation(value = "检查手机号码（true表示检查通过）")
    @ApiImplicitParam(name = "phoneNumber", value = "手机号码", required = true, type = "path")
    public Result<Boolean> checkPhoneNumber(@PathVariable String phoneNumber) {
        try {
            Boolean flag = userBusiness.checkPhoneNumber(phoneNumber);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), flag);
        } catch (Exception e) {
            logger.error("检查手机号码异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("permission:user")
    @PostMapping()
    @ApiOperation(value = "新增用户")
    public Result add(@Validated @RequestBody UserAddVO userAddVO) {
        try {
            if ("admin".equals(userAddVO.getName())) {
                return new Result(ResultCode.DATA_ERROR.getCode(), "不能增加超级用户", null);
            }
            Result result = checkNameOrPhoneNumber(userAddVO.getName(), "name", null);
            if (result != null) {
                return result;
            }
            result = checkNameOrPhoneNumber(userAddVO.getPhoneNumber(), "password", null);
            if (result != null) {
                return result;
            }
            UserBO userBO = new UserBO();
            BeanUtils.copyProperties(userAddVO, userBO);
            if (userBusiness.insert(userBO)) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            } else {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
        } catch (Exception e) {
            logger.error("新增用户异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("permission:user")
    @GetMapping("roles")
    @ApiOperation(value = "获得所有的角色选项")
    public Result<List<RoleSelectVO>> roles() {
        try {
            List<RoleBO> roles = roleBusiness.findList();
            List<RoleSelectVO> result = JSON.parseArray(JSON.toJSONString(roles), RoleSelectVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("获得所有的角色选项异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @RequiresPermissions("permission:user")
    @DeleteMapping()
    @ApiOperation(value = "删除用户")
    public Result delete(@RequestBody List<Long> ids) {
        try {
            if (ids.contains(1L)) {
                ids.remove(1L);
            }
            if (CollectionUtils.isEmpty(ids)) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            if (userBusiness.delete(ids) > 0) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            } else {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }

        } catch (Exception e) {
            logger.error("删除用户异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }

    @RequiresPermissions("permission:user")
    @GetMapping("detail")
    @ApiOperation(value = "获得用户详情")
    public Result<UserDetailVO> detail(@Validated IdVO idVO) {
        try {
            UserBO userBO = userBusiness.detail(idVO.getId());
            UserDetailVO userDetailVO = new UserDetailVO();
            BeanUtils.copyProperties(userBO, userDetailVO);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), userDetailVO);
        } catch (Exception e) {
            logger.error("获得用户详情异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }

    @RequiresPermissions("permission:user")
    @PutMapping()
    @ApiOperation(value = "更新用户")
    public Result update(@Validated @RequestBody UserUpdateVO userUpdateVO) {
        try {
            if (userUpdateVO.getId().equals(1L)) {
                userUpdateVO.setName("admin");
            }
            Result result = checkNameOrPhoneNumber(userUpdateVO.getName(), "name", userUpdateVO.getId());
            if (result != null) {
                return result;
            }
            result = checkNameOrPhoneNumber(userUpdateVO.getPhoneNumber(), "password", userUpdateVO.getId());
            if (result != null) {
                return result;
            }
            UserBO userbo = new UserBO();
            BeanUtils.copyProperties(userUpdateVO, userbo);
            if (!userBusiness.update(userbo)) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            logger.error("更新用户信息异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }

    private Result checkNameOrPhoneNumber(String name, String type, Long userId) {
        if (!userBusiness.checkUserNameOrPhoneNumber(name, userId)) {
            if (type.equals("name")) {
                return new Result(ResultCode.DATA_ERROR.getCode(), "用户名已经存在", null);
            } else {
                return new Result(ResultCode.DATA_ERROR.getCode(), "电话号码已经存在", null);
            }
        }
        return null;
    }

    @RequiresPermissions("permission:user")
    @PutMapping("updatePwd")
    @ApiOperation(value = "更新用户密码")
    public Result updatePwd(@Validated @RequestBody UserUpdatePwdVO userUpdatePwdVO) {
        try {
            UserBO userbo = new UserBO();
            BeanUtils.copyProperties(userUpdatePwdVO, userbo);
            if (!userBusiness.updatePwd(userbo)) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            singleLogin.deleteSessionsByUserId(userbo.getId());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            logger.error("更新用户密码异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }


}
