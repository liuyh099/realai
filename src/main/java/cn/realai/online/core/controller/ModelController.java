package cn.realai.online.core.controller;


import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.vo.ModelDetailVO;
import cn.realai.online.core.vo.ModelListVO;
import cn.realai.online.core.vo.PsiResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：模型管理
 *
 * @author admin
 * @create 2019-03-18 14:13
 */

@RestController
@RequestMapping("/model")
@Api(tags="模型管理API")
public class ModelController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/list")
    @ApiOperation(value="查询模型列表")
    @ResponseBody
    public Result<PageBO<ModelListVO>> list(ModelListQuery query){
        return null;
    }

    @GetMapping("/detail/{modelId}")
    @ApiOperation(value="模型详情")
    @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<ModelDetailVO> detail(){
        return  null;
    }

    @PostMapping("/update")
    @ApiOperation(value="模型详情编辑")
    @ApiImplicitParams ({
        @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "form"),
        @ApiImplicitParam(name = "name", value = "模型名称", required = true, dataType = "String", paramType = "form"),
        @ApiImplicitParam(name = "remark", value = "备注", required = false, dataType = "String", paramType = "form")
    })
    @ResponseBody
    public Result<Void> update(){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("编辑成功");
        return  result;
    }


    @GetMapping("/checkPsi/{modelId}")
    @ApiOperation(value="模型调优-检测PSI")
    @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<PsiResultVO> checkPsi(){
        return  null;
    }


    @PostMapping("/forceUpdateModel")
    @ApiOperation(value="模型调优-强制调优")
    @ApiImplicitParam(name = "pkstr", value = "密钥串", required = true, dataType = "String", paramType = "query")
    @ResponseBody
    public Result<Void> forceUpdateModel(@RequestParam String pkstr){
        return  null;
    }
}
