package cn.realai.online.core.controller;

import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.ModelPerformanceBO;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.vo.ExperimentalPublishDetailVO;
import cn.realai.online.core.vo.IdVO;
import cn.realai.online.core.vo.ModelPerformanceVO;
import cn.realai.online.core.vo.TrainNameSelectVO;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experimental/publish")
@Api(tags = "实验发布API")
public class ExperimentalPublishController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExperimentalTrainBussiness experimentalTrainBussiness;

    @ApiOperation("获得可以发布的实验列表")
    @GetMapping("/trainList")
    public Result<List<TrainNameSelectVO>> getCanPublishTrain() {
        try {
            List<ExperimentBO> list = experimentalTrainBussiness.getCanPublishTrain();
            List<TrainNameSelectVO> result = JSON.parseArray(JSON.toJSONString(list), TrainNameSelectVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("获得可以发布的实验列表异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @ApiOperation("实验发布详细信息")
    @GetMapping("/detail")
    public Result<ExperimentalPublishDetailVO> getCanPublishTrainDetail(@Validated IdVO idVO) {
        try {
            ExperimentBO experimentBO=experimentalTrainBussiness.selectById(idVO.getId());
            ExperimentalPublishDetailVO result =new ExperimentalPublishDetailVO();
            BeanUtils.copyProperties(experimentBO,result);
            List<ModelPerformanceBO> modelPerformanceBOList=experimentalTrainBussiness.findModelPerformance(idVO.getId());
            List<ModelPerformanceVO> modelPerformanceList =JSON.parseArray(JSON.toJSONString(modelPerformanceBOList),ModelPerformanceVO.class);
            result.setModelPerformanceList(modelPerformanceList);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("实验发布详细信息异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);

        }
    }

    @ApiOperation("实验发布")
    @PutMapping()
    public Result<ExperimentalPublishDetailVO> publish(@RequestBody IdVO idVO) {

        try {
            // List<ExperimentBO> list=experimentalTrainBussiness.getCanPublishTrain();
            return null;
        } catch (Exception e) {
            logger.error("实验发布异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);

        }
    }


}
