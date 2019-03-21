package cn.realai.online.core.controller;


import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.bussiness.ServiceBussiness;
import cn.realai.online.core.vo.service.GetServiceDetailsVO;
import cn.realai.online.core.vo.ServerNameSelectVO;
import cn.realai.online.core.vo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@Api(tags = "服务API")
public class ServiceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServiceBussiness serviceBussiness;

    @GetMapping("/select")
    @ApiOperation(value = "查询服务下拉选项")
    public Result<List<ServerNameSelectVO>> getSelect() {
        return null;
    }


    @PostMapping("/service/list")
    @ApiOperation(value = "新增服务")
    public Result addService(@RequestBody AddServiceVO addServiceVO) {
        ServiceBO serviceBO = new ServiceBO();
        BeanUtils.copyProperties(addServiceVO, serviceBO);
        if (!serviceBussiness.addService(serviceBO)) {
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
    }

    @PutMapping("/service/list")
    @ApiOperation(value = "编辑服务")
    public Result editService(@RequestBody EditServiceVO editServiceVO) {
        ServiceBO serviceBO = new ServiceBO();
        BeanUtils.copyProperties(editServiceVO, serviceBO);
        if (!serviceBussiness.editService(serviceBO)) {
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
    }

    @GetMapping("/service/list/item")
    @ApiOperation(value = "获取服务详情")
    public Result<ServiceVO> getServiceDetails(GetServiceDetailsVO getServiceDetailsVO) {
        ServiceBO serviceBO = new ServiceBO();
        ServiceVO serviceVO = new ServiceVO();
        try {
            BeanUtils.copyProperties(getServiceDetailsVO, serviceBO);
            serviceBO = serviceBussiness.getServiceDetails(serviceBO);
            BeanUtils.copyProperties(serviceBO, serviceVO);
        } catch (Exception e) {
            logger.error("获取服务详情异常！", e);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        }

        return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), serviceVO);
    }

    @PostMapping("/service/list/key")
    @ApiOperation(value = "获取秘钥信息")
    public Result<SecretKeyInfoVO> getSecretKeyInfo(@PathVariable String serviceKey) {
        SecretKeyInfoVO secretKeyInfoVO = new SecretKeyInfoVO();
        try {
            secretKeyInfoVO = serviceBussiness.getSecretKeyInfo(serviceKey);
        } catch (Exception e) {
            logger.error("获取服务详情异常！", e);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        }

        return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), secretKeyInfoVO);
    }

}
