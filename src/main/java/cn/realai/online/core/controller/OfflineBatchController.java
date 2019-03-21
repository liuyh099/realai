package cn.realai.online.core.controller;


import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.query.OfflineBatchListQuery;
import cn.realai.online.core.vo.ModelDetailVO;
import cn.realai.online.core.vo.ModelListVO;
import cn.realai.online.core.vo.OfflineBatchDetailVO;
import cn.realai.online.core.vo.OfflineBatchListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：离线跑批管理
 *
 * @author admin
 * @create 2019-03-18 14:13
 */

@RestController
@RequestMapping("/offlineBatch")
@Api(tags = "离线跑批管理API")
public class OfflineBatchController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/create")
    @ApiOperation(value = "离线跑批运算")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "form"),
            @ApiImplicitParam(name = "serviceId", value = "服务ID", required = true, dataType = "Long", paramType = "form"),
            @ApiImplicitParam(name = "xtableHomoDataSource", value = "X表同质数据源", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "xtableHeteroDataSource", value = "X表异质数据源", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "ytableDataSource", value = "Y表数据源", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "downUrl", value = "结果下载路径", required = false, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, dataType = "String", paramType = "form")
    })
    @ResponseBody
    public Result<Long> create() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("执行离线跑批运算成功");
        result.setData(111L);
        return result;
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询离线跑批列表")
    @ResponseBody
    public Result<PageBO<OfflineBatchListVO>> list(OfflineBatchListQuery query) {
        return null;
    }

    @GetMapping("/download")
    @ApiOperation(value = "下载离线跑批运算结果")
    @ApiImplicitParam(name = "downUrl", value = "离线跑批下载Url", required = true, dataType = "String", paramType = "form")
    @ResponseBody
    public void download(OfflineBatchListQuery query) {
        try {

        } catch (Exception e) {
            log.error("下载离线跑批结果异常", e);
        }
    }

    @GetMapping("/detail/{batchId}")
    @ApiOperation(value = "离线跑批实验详情")
    @ApiImplicitParam(name = "batchId", value = "离线跑批Id", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<OfflineBatchDetailVO> detail() {
        return null;
    }

}
