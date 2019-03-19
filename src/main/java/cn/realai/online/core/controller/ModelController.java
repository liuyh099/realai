package cn.realai.online.core.controller;


import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bussiness.ModelBusiness;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.service.ModelPerformanceService;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.vo.ModelDetailVO;
import cn.realai.online.core.vo.ModelListVO;
import cn.realai.online.core.vo.ModelSelectVO;
import cn.realai.online.core.vo.PsiResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
    private ModelBusiness modelBusiness;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelPerformanceService modelPerformanceService;

    @GetMapping("/list")
    @ApiOperation(value = "查询模型列表")
    @ResponseBody
    public Result<PageBO<ModelListVO>> list(ModelListQuery query) {
        try {
            PageBO<ModelListVO> page = modelBusiness.pageList(query);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), page);
        } catch (Exception e) {
            log.error("查询模型列表异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

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
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "模型详情编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "模型名称", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataType = "String", paramType = "form")
    })
    @ResponseBody
    public Result<Void> update() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("编辑成功");
        return result;
    }


    @GetMapping("/selectModel")
    @ApiOperation(value = "根据服务ID查询模型信息或者查询最近一次发布的服务模型")
    @ApiImplicitParam(name = "serviceId", value = "服务ID", required = false, dataType = "Long", paramType = "query")
    @ResponseBody
    public Result<ModelSelectVO> selectModel() {
        return null;
    }


    @GetMapping("/checkPsi/{modelId}")
    @ApiOperation(value = "模型调优-检测PSI")
    @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<PsiResultVO> checkPsi() {
        return null;
    }


    @PostMapping("/forceUpdateModel")
    @ApiOperation(value = "模型调优-强制调优")
    @ApiImplicitParam(name = "pkstr", value = "密钥串", required = true, dataType = "String", paramType = "query")
    @ResponseBody
    public Result<Void> forceUpdateModel(@RequestParam String pkstr) {
        return null;
    }
}
