package cn.realai.online.core.controller;

import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.ModelBO;
import cn.realai.online.core.bo.ModelPerformanceBO;
import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.bussiness.ModelBussiness;
import cn.realai.online.core.bussiness.ServiceBussiness;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.Service;
import cn.realai.online.core.vo.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;  
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/experimental/publish")
@Api(tags = "实验发布API")
public class ExperimentalPublishController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExperimentalTrainBussiness experimentalTrainBussiness;

    @Autowired
    private ModelBussiness modelBussiness;

    @Autowired
    private ServiceBussiness serviceBussiness;

    @RequiresPermissions("experimental:publish")
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

    @RequiresPermissions("experimental:publish")
    @ApiOperation("实验发布详细信息")
    @GetMapping("/detail")
    public Result<ExperimentalPublishDetailVO> getCanPublishTrainDetail(@Validated IdVO idVO) {
        try {
            ExperimentBO experimentBO = experimentalTrainBussiness.selectById(idVO.getId());

            ExperimentalPublishDetailVO result = new ExperimentalPublishDetailVO();
            BeanUtils.copyProperties(experimentBO, result);
            List<ModelPerformanceBO> modelPerformanceBOList = experimentalTrainBussiness.findModelPerformance(idVO.getId());
            List<ModelPerformanceVO> modelPerformanceList = JSON.parseArray(JSON.toJSONString(modelPerformanceBOList), ModelPerformanceVO.class);
            result.setModelPerformanceList(modelPerformanceList);
            ServiceBO service =new ServiceBO();
            service.setId(experimentBO.getServiceId());
            service=serviceBussiness.getServiceDetails(service);
            result.setType(service.getBusinessType());
            result.setTypeName(Service.getServiceTypeName(service.getType(), service.getBusinessType()));
            result.setServiceName(service.getName());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("实验发布详细信息异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);

        }
    }

   // @RequiresPermissions("experimental:publish")
    @ApiOperation("实验发布")
    @PostMapping()
    public Result publish(@Validated @RequestBody ExperimentalPublishVO experimentalPublishVO) {
        try {
            Boolean flag = modelBussiness.checkName(experimentalPublishVO.getName());
            if (!flag) {
                return new Result(ResultCode.DATA_ERROR.getCode(), "模型名称已存在", null);
            }
            ModelBO modelBO = new ModelBO();
            BeanUtils.copyProperties(experimentalPublishVO, modelBO);
            if(2==experimentalPublishVO.getStatus()){
                modelBO.setReleaseStatus(Model.RELEASE_STATUS.OFFLINE.value);
            }else {
                modelBO.setReleaseStatus(Model.RELEASE_STATUS.ONLINE.value);
            }
            Map<String,Object> result = modelBussiness.publish(modelBO);
            if ((boolean)result.get("status")) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            return new Result(ResultCode.DATA_ERROR.getCode(),result.get("msg").toString(), null);
        } catch (Exception e) {
            logger.error("实验发布异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);

        }
    }

    @RequiresPermissions("experimental:publish")
    @ApiOperation("实验发布检查实验名称 true:表示检查通过")
    @GetMapping("/checkName/{name}")
    @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "String", paramType = "path")
    public Result checkName(@PathVariable String name) {
        try {
            Boolean flag = modelBussiness.checkName(name);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), flag);
        } catch (Exception e) {
            logger.error("实验发布检查实验名称", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);

        }
    }


}
