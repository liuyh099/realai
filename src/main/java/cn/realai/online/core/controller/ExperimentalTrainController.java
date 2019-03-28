package cn.realai.online.core.controller;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.ExperimentalTrainDetailBO;
import cn.realai.online.core.bo.ExperimentalTrainDoubleCreateBO;
import cn.realai.online.core.bo.VariableDataBO;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.bussiness.SSHBusinness;
import cn.realai.online.core.bussiness.VariableDataBussiness;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.query.ExperimentalTrainCreateModelDataQuery;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.query.VariableDataQuery;
import cn.realai.online.core.vo.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/experimental/train")
@Api(tags = "实验训练API")
public class ExperimentalTrainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExperimentalTrainBussiness experimentalTrainBussiness;

    @Autowired
    private VariableDataBussiness variableDataBusiness;

    @Autowired
    private SSHBusinness sSHBusinness;


    @RequiresPermissions("experimental:train")
    @GetMapping
    @ApiOperation(value = "查询实验训练列表")
    @ResponseBody
    public Result<PageBO<ExperimentalTrainVO>> list(ExperimentalTrainQuery query) {
        try {
            PageBO<ExperimentBO> page = experimentalTrainBussiness.pageList(query);
            if (page == null) {
                return null;
            }
            List<ExperimentalTrainVO> result = JSON.parseArray(JSON.toJSONString(page.getPageContent()), ExperimentalTrainVO.class);
            PageBO<ExperimentalTrainVO> pageBO = new PageBO<ExperimentalTrainVO>(result, query.getPageSize(), query.getPageNum(), page.getCount(), page.getTotalPage());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), pageBO);
        } catch (Exception e) {
            logger.error("查询实验列表异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }

    @RequiresPermissions("experimental:train")
    @DeleteMapping()
    @ApiOperation(value = "删除实验训练列表")
    @ResponseBody
    public Result delete(@RequestBody List<Long> ids) {

        try {
            if (CollectionUtils.isEmpty(ids)) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            if (experimentalTrainBussiness.deleteExperimentByIds(ids) > 0) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            } else {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
        } catch (Exception e) {
            logger.error("删除实验训练列表异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @RequiresPermissions("experimental:train")
    @GetMapping("/detail/{trainId}")
    @ApiOperation(value = "实验详情")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<ExperimentalTrainDetailVO> detail(@PathVariable Long trainId) {
        try {
            ExperimentalTrainDetailBO experimentalTrainDetailBO = experimentalTrainBussiness.detail(trainId);
            ExperimentalTrainDetailVO result = JSON.parseObject(JSON.toJSONString(experimentalTrainDetailBO), ExperimentalTrainDetailVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("获得实验详情异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @RequiresPermissions("experimental:train")
    @GetMapping("/detail/variableData")
    @ApiOperation(value = "实验详情-获取同质异质数据schema")
    @ResponseBody
    public Result<VariableDataVO> variableData(@Validated VariableDataQuery variableDataQuery) {
        try {
            if (ObjectUtils.isEmpty(variableDataQuery)) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            PageBO<VariableDataVO> page = variableDataBusiness.pageList(variableDataQuery);

            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), page);
        } catch (Exception e) {
            logger.error("实验详情-获取同质异质数据schema", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @RequiresPermissions("experimental:train")
    @GetMapping("/select/{serverId}")
    @ApiOperation(value = "根据服务id活得实验名称列表")
    @ApiImplicitParam(name = "serverId", value = "服务ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<List<TrainNameSelectVO>> getSelect(@PathVariable Long serverId) {
        try {
            List<ExperimentBO> experimentBOS = experimentalTrainBussiness.findExperimentByServerId(serverId);
            List<TrainNameSelectVO> result = JSON.parseArray(JSON.toJSONString(experimentBOS), TrainNameSelectVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("根据服务id活得实验名称列表异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("experimental:train")
    @PostMapping("/selectFile")
    @ApiOperation(value = "新增实验-选择文件")
    @ResponseBody
    public Result<IdVO> selectFileAdd(@RequestBody @Validated ExperimentalTrainSelectFileVO experimentalTrainSelectFileVo) {
        try {
            //检查文件名
            boolean flag = experimentalTrainBussiness.checkTrainName(experimentalTrainSelectFileVo.getName(), experimentalTrainSelectFileVo.getId());
            if (!flag) {
                return new Result(ResultCode.DATA_ERROR.getCode(), "实验名称不能重复", null);
            }
            ExperimentBO experimentBO = new ExperimentBO();
            BeanUtils.copyProperties(experimentalTrainSelectFileVo, experimentBO);
            experimentBO.setServiceId(experimentalTrainSelectFileVo.getServerId());
            Long id = experimentalTrainBussiness.selectFileAdd(experimentBO);
            if (id == null) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            IdVO idVO = new IdVO();
            idVO.setId(id);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), idVO);
        } catch (Exception e) {
            logger.error("添加实验-选择文件-新增异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("experimental:train")
    @GetMapping("/getFilePath")
    @ApiOperation(value = "新增实验-选择文件-获得文件地址")
    @ApiImplicitParam(name = "type", value = "文件地址类别[experiment：实验，offline：离线跑批 默认是实验]", required = false, dataType = "String", paramType = "query")
    @ResponseBody
    public Result<FileTreeVo> getFilePath(@RequestParam(name="type", required = false) String type) {
        try {
            type = StringUtils.isEmpty(type) ? "experiment":type;
            Object o = sSHBusinness.getFilePath(type);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), o);
        } catch (Exception e) {
            logger.error("新增实验-选择文件-获得文件地址异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);

        }
    }


    @RequiresPermissions("experimental:train")
    @GetMapping("/selectFile/{trainId}")
    @ApiOperation(value = "活得选择文件的结果")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<ExperimentalTrainSelectFileVO> selectFileQuery(@PathVariable @NotNull(message = "查询条件不能为空") Long trainId) {
        try {
            if (trainId == null) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            ExperimentBO experimentBO = experimentalTrainBussiness.selectById(trainId);
            ExperimentalTrainSelectFileVO experimentalTrainSelectFileVO = new ExperimentalTrainSelectFileVO();
            BeanUtils.copyProperties(experimentBO, experimentalTrainSelectFileVO);
            experimentalTrainSelectFileVO.setServerId(experimentBO.getServiceId());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), experimentalTrainSelectFileVO);
        } catch (Exception e) {
            logger.error("添加实验-选择文件-获得详细异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("experimental:train")
    @PutMapping("/selectFile")
    @ApiOperation(value = "更新选择文件内容")
    @ResponseBody
    public Result selectFileUpdate(@RequestBody @Validated ExperimentalTrainSelectFileVO experimentalTrainSelectFileVo) {
        try {
            if (experimentalTrainSelectFileVo.getId() == null) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            //检查文件名
            boolean flag = experimentalTrainBussiness.checkTrainName(experimentalTrainSelectFileVo.getName(), experimentalTrainSelectFileVo.getId());
            if (!flag) {
                return new Result(ResultCode.DATA_ERROR.getCode(), "实验名称不能重复", null);
            }
            Integer count;
            ExperimentBO experimentBO = new ExperimentBO();
            BeanUtils.copyProperties(experimentalTrainSelectFileVo, experimentBO);
            experimentBO.setServiceId(experimentalTrainSelectFileVo.getServerId());
            boolean updateFlag = checkCanUpdate(experimentalTrainSelectFileVo.getId());
            if(updateFlag){
                count = experimentalTrainBussiness.selectFileUpdate(experimentBO);
            }else {
                count = experimentalTrainBussiness.updateName(experimentBO);
            }
            if (count != null && count > 0) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        } catch (Exception e) {
            logger.error("添加实验-选择文件-新增异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }


//    @RequiresPermissions("experimental:train")
//    @GetMapping("/editDetail")
//    @ApiOperation(value = "获得修改详情")
//    @ResponseBody
//    public Result editDetail(@RequestBody @Validated IdVO idVO) {
//        try {
//            //检查文件名
//            boolean flag = experimentalTrainBussiness.checkTrainName(experimentalUpdateNameAndRemarkVo.getName(), experimentalUpdateNameAndRemarkVo.getId());
//            if (!flag) {
//                return new Result(ResultCode.DATA_ERROR.getCode(), "实验名称不能重复", null);
//            }
//            ExperimentBO experimentBO = new ExperimentBO();
//            BeanUtils.copyProperties(experimentalUpdateNameAndRemarkVo, experimentBO);
//            Integer count = experimentalTrainBussiness.updateNameAndRemark(experimentBO);
//            if (count != null && count > 0) {
//                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
//            }
//            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
//        } catch (Exception e) {
//            logger.error("更新实验名称和备注异常", e);
//            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
//        }
//
//    }

    @RequiresPermissions("experimental:train")
    @PutMapping("/updateNameAndRemark")
    @ApiOperation(value = "更新实验名称和备注")
    @ResponseBody
    public Result updateNameAndRemark(@RequestBody @Validated ExperimentalUpdateNameAndRemarkVo experimentalUpdateNameAndRemarkVo) {
        try {
            //检查文件名
            boolean flag = experimentalTrainBussiness.checkTrainName(experimentalUpdateNameAndRemarkVo.getName(), experimentalUpdateNameAndRemarkVo.getId());
            if (!flag) {
                return new Result(ResultCode.DATA_ERROR.getCode(), "实验名称不能重复", null);
            }
            ExperimentBO experimentBO = new ExperimentBO();
            BeanUtils.copyProperties(experimentalUpdateNameAndRemarkVo, experimentBO);
            Integer count = experimentalTrainBussiness.updateNameAndRemark(experimentBO);
            if (count != null && count > 0) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        } catch (Exception e) {
            logger.error("更新实验名称和备注异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }

    }


    private boolean checkCanUpdate(Long id ) {
        ExperimentBO experiment = experimentalTrainBussiness.selectById(id);
        if(experiment.getStatus()== Experiment.STATUS_TRAINING || experiment.getStatus()==Experiment.STATUS_TRAINING_OVER){
            return false;
        }
        return true;
    }

    @RequiresPermissions("experimental:train")
    @PostMapping("/selectParam")
    @ApiOperation(value = "新增实验-选择参数")
    @ResponseBody
    public Result<IdVO> selectParamAdd(@RequestBody @Validated ExperimentalTrainSelectParamVO experimentalTrainSelectParamVo) {
        try {
            return updateParam(experimentalTrainSelectParamVo, Experiment.STATUS_PARAM);
        } catch (Exception e) {
            logger.error("添加实验-选择参数-新增异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("experimental:train")
    @GetMapping("/selectParam/{trainId}")
    @ApiOperation(value = "获得选择参数的结果")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<ExperimentalTrainSelectParamVO> selectParamQuery(@PathVariable Long trainId) {
        try {
            if (trainId == null) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            ExperimentBO experimentBO = experimentalTrainBussiness.selectById(trainId);
            ExperimentalTrainSelectParamVO experimentalTrainSelectParamVO = new ExperimentalTrainSelectParamVO();
            BeanUtils.copyProperties(experimentBO, experimentalTrainSelectParamVO);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), experimentalTrainSelectParamVO);
        } catch (Exception e) {
            logger.error("添加实验-选择参数-获得详细异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("experimental:train")
    @PutMapping("/selectParam")
    @ApiOperation(value = "更新选择参数内容")
    @ResponseBody
    public Result selectParamUpdate(@RequestBody @Validated ExperimentalTrainSelectParamVO experimentalTrainSelectParamVo) {
        try {
            return updateParam(experimentalTrainSelectParamVo, null);
        } catch (Exception e) {
            logger.error("添加实验-选择参数-更新异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    private Result updateParam(ExperimentalTrainSelectParamVO experimentalTrainSelectParamVo, Integer status) {
        boolean updateFlag = checkCanUpdate(experimentalTrainSelectParamVo.getId());
        if(!updateFlag){
            return new Result(ResultCode.DATA_ERROR.getCode(), "实验已经完成或者正在进行中，不可以修改实验", null);
        }
        ExperimentBO experimentBO = new ExperimentBO();
        BeanUtils.copyProperties(experimentalTrainSelectParamVo, experimentBO);
        experimentBO.setStatus(status);
        Integer count = experimentalTrainBussiness.updateParam(experimentBO);
        if (count != null && count > 0) {
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        }
        return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
    }

    @RequiresPermissions("experimental:train")
    @GetMapping("/createModel/{trainId}")
    @ApiOperation(value = "新增实验-生成模型（获得实验名称和是否可以建立模型）")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<ExperimentalTrainCreateModelCheckVO> createModelQuery(@PathVariable Long trainId) {
        try {
            if (trainId == null) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            ExperimentBO experimentBO = experimentalTrainBussiness.selectById(trainId);
            ExperimentalTrainCreateModelCheckVO experimentalTrainCreateModelCheckVO = new ExperimentalTrainCreateModelCheckVO();
            if (experimentBO != null) {
                experimentalTrainCreateModelCheckVO.setName(experimentBO.getName());
                if (experimentBO.getPreFinish() != null && 2 == experimentBO.getPreFinish()) {
                    experimentalTrainCreateModelCheckVO.setCreateModelFlag(true);
                } else {
                    experimentalTrainCreateModelCheckVO.setCreateModelFlag(false);
                }
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), experimentalTrainCreateModelCheckVO);
        } catch (Exception e) {
            logger.error("添加实验-生成模型-查询异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("experimental:train")
    @DeleteMapping("/createModel")
    @ApiOperation(value = "删除同质或者异质量数据")
    @ResponseBody
    public Result createModelDelete(@RequestBody @Validated ExperimentalTrainCreateModelVO experimentalTrainCreateModelVo) {
        try {
            boolean updateFlag = checkCanUpdate(experimentalTrainCreateModelVo.getId());
            if(!updateFlag){
                return new Result(ResultCode.DATA_ERROR.getCode(), "实验已经完成或者正在进行中，不可以修改实验", null);
            }
            experimentalTrainBussiness.deleteVariableData(experimentalTrainCreateModelVo.getId(), experimentalTrainCreateModelVo.getIds());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            logger.error("新增实验-生成模型-删除同质或者异质量数据异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("experimental:train")
    @GetMapping("/createModel/getData")
    @ApiOperation(value = "新增实验-生成模型-一获取同质异质数据")
    @ResponseBody
    public Result<PageBO<ExperimentalTrainCreateModelDataVO>> createModelGetData(@Validated ExperimentalTrainCreateModelDataQuery query) {
        try {
            PageBO<VariableDataBO> page = experimentalTrainBussiness.pageHomOrHemeList(query);
            if (page == null) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            List<ExperimentalTrainCreateModelDataVO> result = JSON.parseArray(JSON.toJSONString(page.getPageContent()), ExperimentalTrainCreateModelDataVO.class);
            PageBO<ExperimentalTrainCreateModelDataVO> pageBO = new PageBO<ExperimentalTrainCreateModelDataVO>(result, query.getPageSize(), query.getPageNum(), page.getCount(), page.getTotalPage());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), pageBO);
        } catch (Exception e) {
            logger.error("新增实验-生成模型-一获取同质异质数据异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    /**
     * 实验训练接口
     * 一件建模
     *
     * @param trainId 实验id
     * @return
     */
    @RequiresPermissions("experimental:train")
    @PostMapping("/createModel/{trainId}")
    @ApiOperation(value = "新增实验-生成模型-一键建立模型")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result createModel(@PathVariable Long trainId) {
        try {
            boolean updateFlag = checkCanUpdate(trainId);
            if(!updateFlag){
                return new Result(ResultCode.DATA_ERROR.getCode(), "实验已经完成或者正在进行中，不可以修改实验", null);
            }
            int ret = experimentalTrainBussiness.train(trainId, null);
            if (ret == -1) { //返回-1表示有实验正在进行，现在不能进行实验
            	return new Result(ResultCode.PYTHON_WAIT.getCode(), ResultMessage.PYTHON_WAIT.getMsg(), 1);
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
    }

    @RequiresPermissions("experimental:result")
    @PostMapping("/doubleCreate")
    @ApiOperation(value = "二次创建实验")
    @ResponseBody
    public Result doubleCreate(@Validated @RequestBody ExperimentalTrainDoubleCreateVO experimentalTrainDoubleCreateVo) {
        try {
            ExperimentalTrainDoubleCreateBO bo = JSON.parseObject(JSON.toJSONString(experimentalTrainDoubleCreateVo), ExperimentalTrainDoubleCreateBO.class);
            Long newExperimentId = experimentalTrainBussiness.doubleCreate(bo);
            if (newExperimentId == null) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), 1);
            }
            int ret = experimentalTrainBussiness.train(newExperimentId, bo.getTrainId());
            if (ret == -1) {
            	return new Result(ResultCode.PYTHON_WAIT.getCode(), ResultMessage.PYTHON_WAIT.getMsg(), 1);
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), 1);
        } catch (Exception e) {
            logger.error("二次创建实验异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), 1);
        }
    }

    @PutMapping("/testPreprocess/{experimentId}")
    @ResponseBody
    public Result testPreprocess(@PathVariable long experimentId) {
        try {
            experimentalTrainBussiness.testPreprocess(experimentId);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), 1);
        } catch (Exception e) {
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), 1);
        }
    }

}
