package cn.realai.online.core.controller;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.SampleGroupingBO;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.bussiness.ModelBussiness;
import cn.realai.online.core.bussiness.SampleWeightBussiness;
import cn.realai.online.core.bussiness.WhiteBoxDecisioBussiness;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.query.ExperimentalResultWhileBoxQuery;
import cn.realai.online.core.query.WhileBoxDecisioGroupClusterQuery;
import cn.realai.online.core.vo.GroupSelectNameVO;
import cn.realai.online.core.vo.IdVO;
import cn.realai.online.core.vo.WhileBoxScoreCardVO;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/whiteBoxDecisio")
@Api(tags = "白盒决策API")
public class WhiteBoxDecisioController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
/*
    @Autowired
    private WhiteBoxDecisioBussiness whiteBoxDecisioBussiness;*/
    @Autowired
    private ModelBussiness modelBussiness;
    @Autowired
    private ExperimentalTrainBussiness experimentalTrainBussiness;

    private SampleWeightBussiness sampleWeightBussiness;

    @GetMapping("whiteBoxDecisio/getGroupOption")
    @ApiOperation("获得下拉组选项 传入模型ID")
    public Result<List<GroupSelectNameVO>> getGroupOptionName(@Validated IdVO idVO) {
        try {
            Model model = modelBussiness.getTrainByModelId(idVO.getId());
            if (ObjectUtils.isEmpty(model)) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            List<SampleGroupingBO> list = experimentalTrainBussiness.getGroupOptionName(model.getExperimentId());
            List<GroupSelectNameVO> result = JSON.parseArray(JSON.toJSONString(list),GroupSelectNameVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("发布管理-千人千面-获得下拉组选项异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }
/*
    @GetMapping("whiteBoxDecisio/groupCluster")
    @ApiOperation(value = "白盒决策-分组聚类")
    public Result<WhileBoxDecisioGroupClusterVO> groupCluster(@Validated WhileBoxDecisioGroupClusterQuery experimentalResultWhileBoxQuery) {
        try {
            WhileBoxScoreCardVO result = sampleWeightBussiness.pageBO(experimentalResultWhileBoxQuery);

            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("白盒决策-分组聚类取数据异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }*/


    @GetMapping("whiteBoxDecisio/groupScoreCard")
    @ApiOperation(value = "白盒决策-分组“评分卡")
    public Result<PageBO<WhileBoxScoreCardVO>> groupScoreCard(@Validated ExperimentalResultWhileBoxQuery experimentalResultWhileBoxQuery) {
        try {
            PageBO<WhileBoxScoreCardVO> pageBO = sampleWeightBussiness.pageBO(experimentalResultWhileBoxQuery);

            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), pageBO);
        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }
}
