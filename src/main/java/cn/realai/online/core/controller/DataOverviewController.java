package cn.realai.online.core.controller;

import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.ModelBO;
import cn.realai.online.core.bo.SampleGroupingBO;
import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.bussiness.ModelBussiness;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.service.ServiceService;
import cn.realai.online.core.vo.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ModelBussiness modelBussiness;

    @RequiresPermissions("data:over")
    @GetMapping("/getLastServerId")
    @ApiModelProperty("获得服务下拉列表")
    public Result<List<DataOverviewServiceSelectOptionVO>> getServiceSelect() {
        try {
            List<ServiceBO> list = serviceService.getAlreadyPublishService();
            List<DataOverviewServiceSelectOptionVO> ret = JSON.parseArray(JSON.toJSONString(list), DataOverviewServiceSelectOptionVO.class);
            if (!CollectionUtils.isEmpty(ret)) {
                ret.get(0).setCheckFlag(true);
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), ret);
        } catch (Exception e) {
            logger.error("数据概览Api - 获得服务下拉列表异常");
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("data:over")
    @GetMapping("/getModelSelect")
    @ApiModelProperty("获得模型下拉列表")
    public Result<List<DataOverviewModelSelectVO>> getModelSelect() {
        try {
            List<ModelBO> list = modelBussiness.findLastModelSelect();
            List<DataOverviewModelSelectVO> ret = JSON.parseArray(JSON.toJSONString(list), DataOverviewModelSelectVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), ret);
        } catch (Exception e) {
            logger.error("数据概览Api - 获得模型下拉列表异常");
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("data:over")
    @GetMapping("/images")
    @ApiModelProperty("获得图片(传模型ID)")
    public Result<DataOverImageVO> images(@Validated IdVO idVO) {
        try {
            DataOverImageVO result = null;
            Model model = modelBussiness.getTrainByModelId(idVO.getId());
            if(ObjectUtils.isEmpty(model)){
                return new Result(ResultCode.SUCCESS.getCode(), "模型不存在", null);
            }
            ExperimentBO experiment = experimentalTrainBussiness.selectById(model.getExperimentId());
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

    @RequiresPermissions("data:over")
    @GetMapping("/chars")
    @ApiModelProperty("获得chars(传模型ID)")
    public Result<List<EchartsTotalDataVo>> chars(@Validated IdVO idVO) {
        try {
            EchartsTotalDataVo result = new EchartsTotalDataVo();
            Model model = modelBussiness.getTrainByModelId(idVO.getId());
            if(ObjectUtils.isEmpty(model)){
                return new Result(ResultCode.SUCCESS.getCode(), "模型不存在", null);
            }
            ExperimentBO experiment = experimentalTrainBussiness.selectById(model.getExperimentId());
            if (experiment != null) {
                List<SampleGroupingBO> sampleGroupingBOList = experimentalTrainBussiness.getGroupOptionName(experiment.getId(), true, false);
                List<EchartsDataVo> datas = null;
                List<Integer> total = null;
                List<Double> positiveRatioList = null;
                if (!CollectionUtils.isEmpty(sampleGroupingBOList)) {
                    datas = new ArrayList<>();
                    total = new ArrayList<>();
                    positiveRatioList = new ArrayList<Double>();
                    for (SampleGroupingBO sampleGroupingBO : sampleGroupingBOList) {
                        total.add(sampleGroupingBO.getCount());
                        positiveRatioList.add(sampleGroupingBO.getPositiveRatio());
                        EchartsDataVo data = new EchartsDataVo();
                        data.setName(sampleGroupingBO.getGroupName());
                        data.setValue(sampleGroupingBO.getPercentage());
                        datas.add(data);
                    }
                }
                result.setPositiveRatioList(positiveRatioList);
                result.setCountList(total);
                result.setData(datas);
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("数据概览Api - 获得chars异常");
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }

}
