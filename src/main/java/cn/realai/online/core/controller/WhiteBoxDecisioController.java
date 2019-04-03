package cn.realai.online.core.controller;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.SampleGroupingBO;
import cn.realai.online.core.bo.SampleWeightBO;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.bussiness.GroupDifBussiness;
import cn.realai.online.core.bussiness.ModelBussiness;
import cn.realai.online.core.bussiness.SampleWeightBussiness;
import cn.realai.online.core.entity.GroupDif;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.query.ExperimentalResultWhileBoxQuery;
import cn.realai.online.core.query.WhileBoxDecisioGroupImageQuery;
import cn.realai.online.core.service.GroupDifService;
import cn.realai.online.core.vo.GroupSelectNameVO;
import cn.realai.online.core.vo.IdVO;
import cn.realai.online.core.vo.WhileBoxScoreCardVO;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/whiteBoxDecisio")
@Api(tags = "白盒决策API")
public class WhiteBoxDecisioController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ModelBussiness modelBussiness;

    @Autowired
    private ExperimentalTrainBussiness experimentalTrainBussiness;

    @Autowired
    private SampleWeightBussiness sampleWeightBussiness;

    @Autowired
    private GroupDifBussiness groupDifBussiness;

    @Autowired
    private GroupDifService groupDifService;

    @RequiresPermissions("white:decision")
    @GetMapping("whiteBoxDecisio/getGroupOption")
    @ApiOperation("白盒决策-获得下拉组选项 传入模型ID")
    public Result<List<GroupSelectNameVO>> getGroupOptionName(@Validated IdVO idVO) {
        try {
            Model model = modelBussiness.getTrainByModelId(idVO.getId());
            if (ObjectUtils.isEmpty(model)) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            List<SampleGroupingBO> list = experimentalTrainBussiness.getGroupOptionName(model.getExperimentId(), false,false);
            List<GroupSelectNameVO> result = JSON.parseArray(JSON.toJSONString(list), GroupSelectNameVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("白盒决策-获得下拉组选项异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("white:decision")
    @GetMapping("whiteBoxDecisio/getClassResources")
    @ApiOperation("白盒决策-获得根路径")
    public static String getClassResources() {
        String path = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }

    @RequiresPermissions("white:decision")
    @GetMapping("whiteBoxDecisio/groupScoreCard")
    @ApiOperation(value = "白盒决策-分组“评分卡")
    public Result<PageBO<WhileBoxScoreCardVO>> groupScoreCard(@Validated ExperimentalResultWhileBoxQuery experimentalResultWhileBoxQuery) {
        try {
            //开启分页
            Page page = PageHelper.startPage(experimentalResultWhileBoxQuery.getPageNum(), experimentalResultWhileBoxQuery.getPageSize());

            List<SampleWeightBO> boList = sampleWeightBussiness.getSampleWeightList(experimentalResultWhileBoxQuery);
            //处理查询结果
            List<WhileBoxScoreCardVO> result = JSON.parseArray(JSON.toJSONString(boList), WhileBoxScoreCardVO.class);
            PageBO<WhileBoxScoreCardVO> pageBO = new PageBO<WhileBoxScoreCardVO>(result, experimentalResultWhileBoxQuery.getPageSize(), experimentalResultWhileBoxQuery.getPageNum(), page.getTotal(), page.getPages());

            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), pageBO);
        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @RequiresPermissions("white:decision")
    @GetMapping("whiteBoxDecisio/getGroupList/{modelId}")
    @ApiOperation("白盒决策-获得分组对比图的分组")
    @ApiImplicitParam(name = "modelId", value = "模型ID", required = true, dataType = "Long", paramType = "path")
    public Result<List<String>> getGroupList(@PathVariable Long modelId) {
        try {
            Model model = modelBussiness.getTrainByModelId(modelId);
            Assert.notNull(model, "无效模型ID");
            List<String> groupNames = groupDifService.selectGroupNameList(model.getExperimentId());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), groupNames);
        } catch (Exception e) {
            logger.error("白盒决策-获得分组对比图的分组", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }



    @RequiresPermissions("white:decision")
    @GetMapping("whiteBoxDecisio/getGroupCompareImage")
    @ApiOperation("白盒决策-获得分组对比图数据")
    public Result<Map> getGroupCompareImage(@Validated WhileBoxDecisioGroupImageQuery query) {
        try {
            Model model = modelBussiness.getTrainByModelId(query.getModelId());
            Assert.notNull(model, "无效模型ID");
            GroupDif dif = new GroupDif();
            dif.setExperimentId(model.getExperimentId());
            dif.setGroupName1(query.getGroupName1());
            dif.setGroupName2(query.getGroupName2());
            Map result = groupDifBussiness.selectGroupImage(dif);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("白盒决策-获得分组对比图数据", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }
}
