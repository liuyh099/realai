package cn.realai.online.core.controller;


import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.*;
import cn.realai.online.core.bussiness.ExperimentalTrainBusiness;
import cn.realai.online.core.query.ExperimentalResultWhileBoxQuery;
import cn.realai.online.core.query.FaceListDataQuery;
import cn.realai.online.core.query.GlobalVariableQuery;
import cn.realai.online.core.query.IdQuery;
import cn.realai.online.core.vo.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experimental/result")
@Api(tags = "实验结果API")
public class ExperimentalResultController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExperimentalTrainBusiness experimentalTrainBusiness;

    @GetMapping("/group/{trainId}")
    @ApiOperation(value = "实验结果-根据实验ID活得组集合(传实验的id)")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    public Result<List<GroupSelectNameVO>> group() {

        return null;
    }


    @GetMapping("assessment/image")
    @ApiOperation(value = "实验评估-图片(传实验的id)")
    public Result<ExperimentalResultImageVO> assessment(@RequestBody @Validated IdVO idVo) {
        try {
            ExperimentBO experimentBO = experimentalTrainBusiness.selectById(idVo.getId());
            ExperimentalResultImageVO experimentalResultImageVO = new ExperimentalResultImageVO();
            BeanUtils.copyProperties(experimentBO, experimentalResultImageVO);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), experimentalResultImageVO);
        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @GetMapping("assessment/quota")
    @ApiOperation(value = "实验评估-业务指标(传实验的id)")
    public Result<ExperimentalResultQuatoVO> quota(@Validated IdVO idVo) {
        try {
            ExperimentalResultQuatoBO experimentalResultQuatoBO = experimentalTrainBusiness.quota(idVo.getId());
            ExperimentalResultQuatoVO experimentalResultQuatoVO = JSON.parseObject(JSON.toJSONString(experimentalResultQuatoBO), ExperimentalResultQuatoVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), experimentalResultQuatoVO);
        } catch (Exception e) {
            logger.error("实验评估-业务指标异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @GetMapping("assessment/quota/{groupId}")
    @ApiOperation(value = "实验评估-业务指标查看")
    @ApiImplicitParam(name = "groupId", value = "实验组ID", required = true, dataType = "Long", paramType = "path")
    public Result<List<ExperimentalResultQuatoDataVO>> quotaGroup(@PathVariable Long groupId) {
        try {
            List<ExperimentResultSetBO> listBO = experimentalTrainBusiness.quotaGroup(groupId);
            ExperimentalResultQuatoDataVO experimentalResultQuatoDataVO = JSON.parseObject(JSON.toJSONString(listBO), ExperimentalResultQuatoDataVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), experimentalResultQuatoDataVO);
        } catch (Exception e) {
            logger.error("实实验评估-业务指标查看异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @GetMapping("assessment/top")
    @ApiOperation(value = "实验评估-TOP(传实验的id)")
    public Result<ExperimentalResultTopVO> quotaTopGroup(@Validated IdVO idVo) {
        try {
            ExperimentalResultTopBO resultTopBO = experimentalTrainBusiness.quotaTopGroup(idVo.getId());
            ExperimentalResultTopVO result = JSON.parseObject(JSON.toJSONString(resultTopBO), ExperimentalResultTopVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("实验评估-TOP异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @GetMapping("assessment/summary")
    @ApiOperation(value = "实验评估-摘要-(传实验的id)")
    public Result<List<ExperimentalResultSummaryVO>> summary(@Validated IdVO idVo) {
        try {
            List<SampleSummaryBO> bo = experimentalTrainBusiness.summary(idVo.getId());
            ExperimentalResultSummaryVO result = JSON.parseObject(JSON.toJSONString(bo), ExperimentalResultSummaryVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("实验评估-摘要异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @GetMapping("whiledecision")
    @ApiOperation(value = "实验-白盒决策")
    public Result<PageBO<WhileBoxScoreCardVO>> whiledecision(@RequestBody ExperimentalResultWhileBoxQuery experimentalResultWhileBoxQuery) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    @GetMapping("globalVariable")
    @ApiOperation(value = "实验-全局变量")
    public Result<PageBO<WhileBoxScoreCardVO>> globalVariable(@RequestBody GlobalVariableQuery globalVariableQuery) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    //从实验来
    @GetMapping("thousandsFace/image")
    @ApiOperation(value = "实验-千人千面获取图片(传实验的id)")
    public Result<String> thousandsFace(@RequestBody IdVO idVo) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    @GetMapping("thousandsFace/echartsData")
    @ApiOperation(value = "实验-千人千面 获取echarts 数据")
    public Result echartsData(@RequestBody IdVO idVo) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    @GetMapping("thousandsFace/list")
    @ApiOperation(value = "实验-千人千面列表数据")
    public Result<PageBO<PersonalInformationVO>> listData(@RequestBody FaceListDataQuery faceListDataQuery) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    @GetMapping("thousandsFace/list/detail")
    @ApiOperation(value = "实验-千人千面列表数据-详情(传数据列表的id(不是ID字段))")
    public Result<PersonalInformationDetailVO> listDataDetail(@RequestBody IdVO idVo) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    @GetMapping("thousandsFace/list/topGroup")
    @ApiOperation(value = "实验-千人千面列表数据-详情-异质最强组合特征(传数据id))")
    public Result<List<PersonalComboResultSetVO>> listDataDetailTopGroup(@RequestBody IdVO idVo) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    @GetMapping("thousandsFace/list/topTen")
    @ApiOperation(value = "实验-千人千面列表数据-详情-异质最强TOP10(传数据id))")
    public Result<List<PersonalHetroResultSetTopTenVO>> listDataDetailTopTen(@RequestBody IdVO idVo) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    @GetMapping("thousandsFace/list/sameCharts")
    @ApiOperation(value = "实验-千人千面列表数据-详情-同质特征数据(echarts)(传数据id))")
    public Result<List<PersonalHetroResultSetTopTenVO>> listDataDetailSameCharts(@RequestBody IdVO idVo) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    @GetMapping("thousandsFace/list/diff")
    @ApiOperation(value = "实验-千人千面列表数据-详情-所有异质数据(传数据id))")
    public Result<PageBO<PersonalHetroResultSetTopTenVO>> listDiff(@RequestBody IdQuery idQuery) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }

    @GetMapping("thousandsFace/list/same")
    @ApiOperation(value = "实验-千人千面列表数据-详情-所有同质数据(传数据id))")
    public Result<List<PersonalHomoResultSetVO>> listSame(@RequestBody IdQuery idQuery) {
        try {

        } catch (Exception e) {
            logger.error("实验评估-图片异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return null;
    }
}
