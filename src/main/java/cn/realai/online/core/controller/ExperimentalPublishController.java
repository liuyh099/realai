package cn.realai.online.core.controller;

import cn.realai.online.common.vo.Result;
import cn.realai.online.core.vo.ExperimentalPublishDetailVO;
import cn.realai.online.core.vo.IdVO;
import cn.realai.online.core.vo.TrainNameSelectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/experimental/publish")
@Api(tags = "实验发布API")
public class ExperimentalPublishController {


    @ApiOperation("获得可以发布的实验列表")
    @GetMapping("/trainList")
    public Result<TrainNameSelectVO> getCanPublishTrain() {

        try {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @ApiOperation("实验发布详细信息")
    @GetMapping("/detail")
    public Result<ExperimentalPublishDetailVO> getCanPublishTrainDetail(@RequestBody IdVO idVO) {
        return null;
    }

    @ApiOperation("实验发布")
    @PutMapping()
    public Result<ExperimentalPublishDetailVO> publish(@RequestBody IdVO idVO) {

        return null;
    }


}
