package cn.realai.online.core.controller;

import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.SampleGroupingBO;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.vo.DataOverImageVO;
import cn.realai.online.core.vo.EchartsDataVo;
import cn.realai.online.core.vo.IdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dataOverview")
@Api(tags = "数据概览Api")
public class DataOverviewController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExperimentalTrainBussiness experimentalTrainBussiness;

    @GetMapping("/getLastServerId")
    @ApiModelProperty("获得最近发布模型的服务ID")
    public Result<Long> getLastServerId() {
        try {
            Long id = experimentalTrainBussiness.getLastServerId();
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            logger.error("数据概览Api - 获得最近发布模型的服务ID异常");
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }

    @GetMapping("/images")
    @ApiModelProperty("获得图片(传服务ID)")
    public Result<DataOverImageVO> images(@Validated IdVO idVO) {
        try {
            DataOverImageVO result = null;
            ExperimentBO experiment = experimentalTrainBussiness.getPublishExperimentByServerId(idVO.getId());
            if (experiment != null) {
                result = new DataOverImageVO();
                result.setStatisticalImage(experiment.getSegmentationStatisticsImageUrl());
                result.setTopImage(experiment.getBadTopCountImageUrl());
                result.setTestRocImage(experiment.getRocTestImageUrl());
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("数据概览Api - 获得图片异常");
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }

    @GetMapping("/chars")
    @ApiModelProperty("获得chars(传服务ID)")
    public Result<List<EchartsDataVo>> chars(@Validated IdVO idVO) {
        try {
            List<EchartsDataVo> result = null;
            ExperimentBO experiment = experimentalTrainBussiness.getPublishExperimentByServerId(idVO.getId());
            if (experiment != null) {
                List<SampleGroupingBO> sampleGroupingBOList = experimentalTrainBussiness.getGroupOptionName(experiment.getId(), true,true);
                if (!CollectionUtils.isEmpty(sampleGroupingBOList)) {
                    result = new ArrayList<>();
                    for (SampleGroupingBO sampleGroupingBO : sampleGroupingBOList) {
                        EchartsDataVo data = new EchartsDataVo();
                        data.setName(sampleGroupingBO.getGroupName());
                        data.setValue(sampleGroupingBO.getPercentage());
                        result.add(data);
                    }
                }
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("数据概览Api - 获得chars异常");
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }


}
