package cn.realai.online.core.controller;


import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bussiness.ModelBussiness;
import cn.realai.online.core.bussiness.PsiCheckResultBussiness;
import cn.realai.online.core.bussiness.TuningRecordBussiness;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.TuningRecord;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.vo.*;
import cn.realai.online.lic.ServiceLicenseCheck;
import cn.realai.online.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 功能描述：模型管理
 *
 * @author admin
 * @create 2019-03-18 14:13
 */

@RestController
@RequestMapping("/model")
@Api(tags = "模型管理API")
public class ModelController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ModelBussiness modelBusiness;
    @Autowired
    private ModelService modelService;
    @Autowired
    private PsiCheckResultBussiness psiCheckResultBusiness;
    @Autowired
    private ServiceLicenseCheck serviceLicenseCheck;
    @Autowired
    private TuningRecordBussiness tuningRecordBussiness;

    @RequiresPermissions("model:list")
    @GetMapping("/list")
    @ApiOperation(value = "查询模型列表")
    @ResponseBody
    public Result<PageBO<ModelListVO>> list(ModelListQuery query) {
        try {
            PageBO<ModelListVO> page = modelBusiness.pageList(query);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), page);
        } catch (Exception e) {
            log.error("查询模型列表异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @RequiresPermissions("model:list")
    @GetMapping("/detail/{modelId}")
    @ApiOperation(value = "模型详情")
    @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<ModelDetailVO> detail(@PathVariable Long modelId) {
        try {
            Assert.notNull(modelId, "模型ID不能为空");
            ModelDetailVO result = modelBusiness.selectModelDetail(modelId);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            log.error("查询模型详情异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @RequiresPermissions("model:list")
    @PostMapping("/update")
    @ApiOperation(value = "模型详情编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "模型名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataType = "String", paramType = "query")
    })
    @ResponseBody
    public Result<Void> update(@RequestParam("modelId") Long modelId, @RequestParam("name") String name,
                               @RequestParam(name = "remark", required = false) String remark) {
        try {
            Assert.hasLength(name, "模型名称不能为空");
            Model model = modelService.get(modelId);
            Assert.notNull(model, "待更新模型不存在");
            model.setName(name);
            model.setRemark(remark);
            modelService.update(model);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            log.error("模型更新异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @GetMapping("/selectModelNameList")
    @ApiOperation(value = "查询服务下的已发布模型集合")
    @ApiImplicitParam(name = "serviceId", value = "服务ID", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    public Result<List<ModelNameSelectVO>> selectModelNameList(@RequestParam(name = "serviceId", required = true) Long serviceId) {
        try {
            List<ModelNameSelectVO> result = modelBusiness.selectModelNameList(serviceId);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            log.error("查询服务下的已发布模型集合", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }


    @GetMapping("/selectRecentModelNameList")
    @ApiOperation(value = "最近一次发布的模型信息")
    @ResponseBody
    public Result<ModelSelectVO> selectRecentModelNameList() {
        try {
            ModelSelectVO result = modelBusiness.selectRecentModelNameList();
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            log.error("根据服务ID或者最近一次发布的模型对应的服务下的模型集合", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @RequiresPermissions("model:better")
    @GetMapping("/checkPsi/{modelId}")
    @ApiOperation(value = "模型调优-检测PSI是否可以点击")
    @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<PsiCheckVO> checkPsi(@PathVariable Long modelId) {
        try {
            PsiCheckVO result = psiCheckResultBusiness.checkPsi(modelId);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            log.error("模型调优-检测PSI查询异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @RequiresPermissions("model:better")
    @GetMapping("/selectPsiList/{modelId}")
    @ApiOperation(value = "模型调优-点击检测PSI获取PSI结果集")
    @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<List<PsiResultVO>> selectPsiList(@PathVariable Long modelId) {
        try {
            List<PsiResultVO> list = psiCheckResultBusiness.selectList(modelId);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), list);
        } catch (Exception e) {
            log.error("根据模型ID获取PSI结果集异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @RequiresPermissions("model:better")
    @PostMapping("/checkSecurityKey")
    @ApiOperation(value = "模型调优-强制调优校验密钥")
    @ApiImplicitParam(name = "pkstr", value = "密钥串", required = true, dataType = "String", paramType = "query")
    @ResponseBody
    public Result<Void> checkSecurityKey(@RequestParam String pkstr) {
        try {
            serviceLicenseCheck.checkServiceLic(pkstr);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            log.error("根据模型ID获取PSI结果集异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @RequiresPermissions("model:better")
    @PostMapping("/psiTuning")
    @ApiOperation(value = "模型调优-PSI调优")
    @ApiImplicitParam(name = "modelId", value = "调优模型ID", required = true, dataType = "Long", paramType = "query")
    @ResponseBody
    public Result<Void> psiTuning(@RequestParam Long modelId) {
        try {
            tuningRecordBussiness.createTuningRecord(modelId, null);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            log.error("模型调优-PSI调优异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @RequiresPermissions("model:better")
    @PostMapping("/forceTuning")
    @ApiOperation(value = "模型调优-强制调优")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "modelId", value = "调优模型ID", required = true, dataType = "Long", paramType = "query"),
        @ApiImplicitParam(name = "pkstr", value = "密钥串", required = true, dataType = "String", paramType = "query")
    })
    @ResponseBody
    public Result<Void> forceTuning(@RequestBody @Validated TuningKeyVo tuningKeyVo) {
        try {
            tuningRecordBussiness.createTuningRecord(tuningKeyVo.getModelId(), tuningKeyVo.getPkstr());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            log.error("模型调优-强制调优异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

}
