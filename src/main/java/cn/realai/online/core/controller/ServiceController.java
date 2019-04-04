package cn.realai.online.core.controller;


import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.bo.ServiceDeployRecordBO;
import cn.realai.online.core.bussiness.ServiceBussiness;
import cn.realai.online.core.bussiness.ServiceDeployRecordBussiness;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.Service;
import cn.realai.online.core.query.service.*;
import cn.realai.online.core.service.ServiceService;
import cn.realai.online.core.vo.ServerNameSelectVO;
import cn.realai.online.core.vo.ServiceDeployRecordVO;
import cn.realai.online.core.vo.service.*;
import cn.realai.online.lic.FileLicenseInfo;
import cn.realai.online.lic.LicenseConstants;
import cn.realai.online.lic.LicenseException;
import cn.realai.online.lic.ServiceLicenseCheck;
import cn.realai.online.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/service")
@Api(tags="服务API")
public class ServiceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServiceBussiness serviceBussiness;

    @Autowired
    private ServiceDeployRecordBussiness serviceDeployRecordBussiness;

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/select")
    @ApiOperation(value="查询服务下拉选项")
    @ApiImplicitParam(name = "status", value = "服务状态 0:未发布 1:线上发布 2：线下发布 3: 线上或者线下发布 不传或其他值则取全部", required = false, dataType = "Integer", paramType = "query")
    public Result<List<ServerNameSelectVO>> getSelect(@RequestParam(value="status", required = false) Integer status){
        List<ServerNameSelectVO> serverNameSelectVOs = new ArrayList<>();
        try {
            String statusStr = null;
            List<Service> services = null;
            if (status != null) {
                if (status == 1) {
                    statusStr = Model.RELEASE_STATUS.ONLINE.value;
                } else if (status == 2) {
                    statusStr = Model.RELEASE_STATUS.OFFLINE.value;
                } else if (status == 0) {
                    statusStr = Model.RELEASE_STATUS.NONE.value;
                }
            }
            if (status != null && status == 3) { //所有已发布
                services = serviceService.findListByAlreadyPublishModel();
            } else {
                 services = serviceService.findListByModelStatus(statusStr);
            }
            if(services != null && services.size() > 0) {
                for (Service s : services) {
                    ServerNameSelectVO sv = new ServerNameSelectVO();
                    sv.setName(s.getName());
                    sv.setServerType(s.getType()+"");
                    sv.setId(s.getId());
                    serverNameSelectVOs.add(sv);
                }
            }
        }catch (Exception e) {
            logger.error("查询服务下拉选项异常！", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(),null);
        }


        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), serverNameSelectVOs);
    }

    @RequiresPermissions("service")
    @PostMapping("/list")
    @ApiOperation(value="新增服务")
    public Result addService(@RequestBody AddServiceQuery addServiceQuery){
        ServiceBO serviceBO = new ServiceBO();
        try {
            BeanUtils.copyProperties(addServiceQuery, serviceBO);
            if(!serviceBussiness.addService(serviceBO)) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
        } catch (Exception e) {
            logger.error("新增服务异常！", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg()+e.getMessage(), null);
        }
        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(),null);
    }

    @RequiresPermissions("service")
    @PutMapping("/list")
    @ApiOperation(value="编辑服务")
    public Result editService(@RequestBody EditServiceQuery editServiceQuery){
        ServiceBO serviceBO = new ServiceBO();
        BeanUtils.copyProperties(editServiceQuery, serviceBO);
        try {
            serviceBO.setId(editServiceQuery.getServiceId());
            if(!serviceBussiness.editService(serviceBO)) {
                return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
            }
        } catch (Exception e) {
            logger.error("编辑服务异常！", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg()+e.getMessage(), null);
        }
        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(),null);
    }

    @RequiresPermissions("service")
    @GetMapping("/list/item")
    @ApiOperation(value="获取服务详情")
    public Result<ServiceDetailVO> getServiceDetails(GetServiceDetailsQuery getServiceDetailsQuery){
        ServiceBO serviceBO = new ServiceBO();
        ServiceDetailVO serviceVO = new ServiceDetailVO();
        try {
            serviceBO.setId(getServiceDetailsQuery.getServiceId());
            serviceBO = serviceBussiness.getServiceDetails(serviceBO);
            BeanUtils.copyProperties(serviceBO, serviceVO);
            serviceVO.setServiceTypeName(Service.getServiceTypeName(serviceBO.getType(), serviceBO.getBusinessType()));

            List<ServiceDeployRecordBO> serviceDeployRecordBOs = serviceDeployRecordBussiness.findServiceDeployRecordByServiceId(serviceVO.getId());
            List<ServiceDeployRecordVO> serviceDeployRecordVOs = new ArrayList<>();
            if(serviceDeployRecordBOs != null && !serviceDeployRecordBOs.isEmpty()) {
                serviceDeployRecordBOs.forEach(serviceDeployRecordBO -> {
                    ServiceDeployRecordVO serviceDeployRecordVO = new ServiceDeployRecordVO();
                    BeanUtils.copyProperties(serviceDeployRecordBO, serviceDeployRecordVO);
                    serviceDeployRecordVOs.add(serviceDeployRecordVO);
                });
            }
            serviceVO.setServiceDeployRecordVOs(serviceDeployRecordVOs);
        }catch (Exception e) {
            logger.error("获取服务详情异常！", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(),null);
        }

        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), serviceVO);
    }

    @RequiresPermissions("service")
    @GetMapping("/list")
    @ApiOperation(value="获取服务列表")
    public Result<List<ServiceVO>> getServiceList(GetServiceListQuery getServiceListQuery){
        ServiceBO serviceBO = new ServiceBO();
        List<ServiceVO> serviceVOs = new ArrayList<>();
        try {
            BeanUtils.copyProperties(getServiceListQuery, serviceBO);
            List<ServiceBO> serviceBOs = serviceBussiness.getServiceList(serviceBO);
            if(serviceBOs != null && !serviceBOs.isEmpty()) {
                for (ServiceBO s : serviceBOs) {
                    ServiceVO vo = new ServiceVO();
                    BeanUtils.copyProperties(s, vo);
                    vo.setServiceTypeName(Service.getServiceTypeName(s.getType(), s.getBusinessType()));
                    serviceVOs.add(vo);
                }
            }
        }catch (Exception e) {
            logger.error("获取服务详情异常！", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(),null);
        }

        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), serviceVOs);
    }

    @RequiresPermissions("service")
    @PostMapping("/list/key")
    @ApiOperation(value="获取秘钥信息")
    public Result<SecretKeyInfoVO> getSecretKeyInfo(@RequestBody GetSecretKeyInfoQuery getSecretKeyInfoQuery){
        SecretKeyInfoVO secretKeyInfoVO = new SecretKeyInfoVO();
        try {
            FileLicenseInfo fileLicenseInfo = serviceBussiness.getSecretKeyInfo(getSecretKeyInfoQuery.getServiceKey());
            secretKeyInfoVO.setStartTime(DateUtil.stringToLong(fileLicenseInfo.getRangeTimeLower(), LicenseConstants.DATE_FORMART));
            secretKeyInfoVO.setExpireDate(DateUtil.stringToLong(fileLicenseInfo.getRangeTimeUpper(), LicenseConstants.DATE_FORMART));
            secretKeyInfoVO.setType(Integer.parseInt(fileLicenseInfo.getServiceType()));
            if(StringUtils.isNotBlank(fileLicenseInfo.getBusinessType())) {
                secretKeyInfoVO.setBusinessType(Integer.parseInt(fileLicenseInfo.getBusinessType()));
            }
            String serviceTypeName = Service.getServiceTypeName(secretKeyInfoVO.getType(), secretKeyInfoVO.getBusinessType());
            secretKeyInfoVO.setServiceTypeName(serviceTypeName);
        }catch (Exception e) {
            logger.error("获取服务详情异常！", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(),null);
        }

        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(),secretKeyInfoVO);
    }

    @RequiresPermissions("service")
    @PostMapping("/list/renewal")
    @ApiOperation(value="服务续期")
    public Result renewalService(@RequestBody RenewalServiceQuery renewalServiceQuery){
        ServiceBO serviceBO = new ServiceBO();
        try {
            Service serviceEntity = serviceService.selectServiceById(renewalServiceQuery.getId());
            Assert.notNull(serviceEntity, "服务不存在");
            BeanUtils.copyProperties(serviceEntity, serviceBO);
            serviceBO.setSecretKey(renewalServiceQuery.getSecretKey());
            serviceBussiness.renewalService(serviceBO);
        } catch (Exception e) {
            logger.error("服务续期异常！", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg()+e.getMessage(), null);
        }
        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(),null);
    }

    @RequiresPermissions("service")
    @DeleteMapping("/list")
    @ApiOperation(value="删除服务")
    public Result deleteService(@RequestBody DeleteServiceQuery deleteServiceQuery){
        try {
            serviceBussiness.deleteService(deleteServiceQuery.getServiceIds());
        } catch (Exception e) {
            logger.error("删除服务异常！", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg() + e.getMessage(), null);
        }
        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(),null);
    }



    @Autowired
    ServiceLicenseCheck serviceLicenseCheck;

    @PostMapping("/test/applyService")
    public Result applyService(@RequestBody ApplyServiceQuery applyServiceQuery){
        try {
            serviceLicenseCheck.applyService(applyServiceQuery.getId(), applyServiceQuery.getSecretKey());
        } catch (Exception e) {
            logger.error("部署调优异常！", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg()+e.getMessage(), null);
        }
        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(),null);
    }

}
