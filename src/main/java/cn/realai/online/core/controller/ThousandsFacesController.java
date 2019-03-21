package cn.realai.online.core.controller;

import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bussiness.ModelBussiness;
import cn.realai.online.core.query.IdQuery;
import cn.realai.online.core.vo.ModelNameSelectVO;
import cn.realai.online.core.vo.ServerNameSelectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "发布管理 - 千人千面")
@RequestMapping("/thousandsFaces")
public class ThousandsFacesController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ModelBussiness modelBussiness;

//    @GetMapping("/selectServerOption")
//    @ApiOperation("获得选则服务选项")
//    public Result<List<ServerNameSelectVO>> getServerOption() {
//        try {
//            Object result = null;
//            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
//        } catch (Exception e) {
//            logger.error("发布管理-千人千面-获得选则服务选项异常", e);
//            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
//        }
//    }
//    @GetMapping("/selectModelOption")
//    @ApiOperation("获得选则服务选项")
//    public Result<List<ModelNameSelectVO>> getModelOptionName() {
//        try {
//            Object result = null;
//            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
//        } catch (Exception e) {
//            logger.error("发布管理-千人千面-获得选则服务选项异常", e);
//            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
//        }
//    }

    @GetMapping()
    @ApiOperation("获得千人千面数据 传入模型ID")
    public Result<List<ModelNameSelectVO>> getModelOptionName(@Validated IdQuery idQuery) {
        try {
            Object result = null;
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("发布管理-千人千面-获得千人千面数据异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

}
