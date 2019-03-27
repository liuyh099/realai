package cn.realai.online.core.controller;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.*;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.bussiness.ModelBussiness;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.query.FaceListDataQuery;
import cn.realai.online.core.query.IdQuery;
import cn.realai.online.core.vo.*;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "发布管理 - 千人千面")
@RequestMapping("/thousandsFaces")
public class ThousandsFacesController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ModelBussiness modelBussiness;
    @Autowired
    private ExperimentalTrainBussiness experimentalTrainBussiness;

//    @GetMapping("/selectServerOption")
//    @ApiOperation("获得选则服务选项")
//    public Result<List<ServerNameSelectVO>> getServerOption() {
//        try {
//            Object result = null;
//            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
//        } catch (Exception e) {
//            logger.error("发布管理-千人千面-获得选则服务选项异常", e);
//            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
//        }
//    }
//    @GetMapping("/selectModelOption")
//    @ApiOperation("获得选则服务选项")
//    public Result<List<ModelNameSelectVO>> getModelOptionName() {
//        try {
//            Object result = null;
//            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
//        } catch (Exception e) {
//            logger.error("发布管理-千人千面-获得选则服务选项异常", e);
//            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
//        }
//    }

    @GetMapping()
    @ApiOperation("获得千人千面数据 传入模型ID")
    public Result<PageBO<PersonalInformationVO>> getModelOptionName(@Validated FaceListDataQuery query) {
        try {
            Model model = modelBussiness.getTrainByModelId(query.getId());
            if (ObjectUtils.isEmpty(model)) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            query.setId(model.getExperimentId());
            PageBO<PersonalInformationBO> page = experimentalTrainBussiness.personalInformationPage(query, null);
            if (page == null) {
                return null;
            }
            List<PersonalInformationVO> result = JSON.parseArray(JSON.toJSONString(page.getPageContent()), PersonalInformationVO.class);
            PageBO<PersonalInformationVO> pageBO = new PageBO<PersonalInformationVO>(result, query.getPageSize(), query.getPageNum(), page.getCount(), page.getTotalPage());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), pageBO);
        } catch (Exception e) {
            logger.error("千人千面-获得千人千面数据异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @GetMapping("/list/detail")
    @ApiOperation(value = "千人千面-列表数据-详情(传数据列表的id(不是ID字段))")
    public Result<PersonalInformationDetailVO> listDataDetail(@Validated IdVO idVo) {
        try {
            PersonalInformationBO personalInformationBO = experimentalTrainBussiness.listDataDetail(idVo.getId());
            PersonalInformationDetailVO result = new PersonalInformationDetailVO();
            BeanUtils.copyProperties(personalInformationBO, result);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("千人千面列表数据-详情异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @GetMapping("/list/topGroup")
    @ApiOperation(value = "千人千面-列表数据-详情-异质最强组合特征(传数据id))")
    public Result<List<PersonalComboResultSetVO>> listDataDetailTopGroup(@Validated IdVO idVo) {
        try {
            List<PersonalComboResultSetBO> list = experimentalTrainBussiness.listDataDetailTopGroup(idVo.getId());
            List<PersonalComboResultSetVO> result = JSON.parseArray(JSON.toJSONString(list), PersonalComboResultSetVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("千人千面列表数据-详情-异质最强组合特征异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

        @GetMapping("/list/topTen")
    @ApiOperation(value = "千人千面-列表数据-详情-异质最强TOP10(传数据id))")
    public Result<List<PersonalHetroResultSetTopTenVO>> listDataDetailTopTen(@Validated IdVO idVo) {
        try {
            List<PersonalHetroResultSetBO> list = experimentalTrainBussiness.listDataDetailTopTen(idVo.getId());
            List<PersonalHetroResultSetTopTenVO> result = JSON.parseArray(JSON.toJSONString(list), PersonalHetroResultSetTopTenVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("千人千面列表数据-详情-异质最强TOP10异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @GetMapping("/list/sameCharts")
    @ApiOperation(value = "千人千面-列表数据-详情-同质特征数据(echarts)(传数据id))")
    public Result<PersonalHomoResultChartsVO> listDataDetailSameCharts(@Validated IdVO idVo) {
        try {
            List<PersonalHomoResultSetBO> list = experimentalTrainBussiness.listDataDetailSameCharts(idVo.getId());
            PersonalHomoResultChartsVO result = new PersonalHomoResultChartsVO();
            if (!CollectionUtils.isEmpty(list)) {
                List<Integer> x = new ArrayList<>();
                List<String> y = new ArrayList<>();
                List<List<Object>> data = new ArrayList<>(list.size());

                for(int i=0;i<list.size();i++){
                    if(!x.contains(list.get(i).getK())){
                        x.add(list.get(i).getK());
                    }
                    if(!y.contains(list.get(i).getVariableName())){
                        y.add(list.get(i).getVariableName());
                    }
                    List<Object> dataItem = new ArrayList<>(3);
                    dataItem.add(y.indexOf(list.get(i).getVariableName()));
                    dataItem.add(x.indexOf(list.get(i).getK()));
                    dataItem.add(list.get(i).getWeight());
                    data.add(dataItem);
                }
                result.setX(x);
                result.setY(y);
                result.setData(data);
            }
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("千人千面-列表数据-详情-同质特征数据(echarts)异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @GetMapping("/list/diff")
    @ApiOperation(value = "千人千面-列表数据-详情-所有异质数据(传数据id))")
    public Result<PageBO<PersonalHetroResultSetTopTenVO>> listDiff(@Validated IdQuery query) {
        try {
            PageBO<PersonalHetroResultSetBO> page = experimentalTrainBussiness.listPersonalHetroResultSet(query);
            if (page == null) {
                return null;
            }
            List<PersonalHetroResultSetTopTenVO> result = JSON.parseArray(JSON.toJSONString(page.getPageContent()), PersonalHetroResultSetTopTenVO.class);
            PageBO<PersonalHetroResultSetTopTenVO> pageBO = new PageBO<PersonalHetroResultSetTopTenVO>(result, query.getPageSize(), query.getPageNum(), page.getCount(), page.getTotalPage());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), pageBO);
        } catch (Exception e) {
            logger.error("千人千面-列表数据-详情-所有异质数据异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @GetMapping("/list/same")
    @ApiOperation(value = "千人千面-列表数据-详情-所有同质数据(传数据id))")
    public Result<List<PersonalHomoResultSetVO>> listSame(IdQuery query) {
        try {
            PageBO<PersonalHomoResultSetBO> page = experimentalTrainBussiness.listPersonalHomoResultSet(query);
            if (page == null) {
                return null;
            }
            List<PersonalHomoResultSetVO> result = JSON.parseArray(JSON.toJSONString(page.getPageContent()), PersonalHomoResultSetVO.class);
            PageBO<PersonalHomoResultSetVO> pageBO = new PageBO<PersonalHomoResultSetVO>(result, query.getPageSize(), query.getPageNum(), page.getCount(), page.getTotalPage());
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), pageBO);

        } catch (Exception e) {
            logger.error("千人千面-列表数据-详情-所有同质数据异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @GetMapping("getGroupOption")
    @ApiOperation("获得下拉组选项 传入模型ID")
    public Result<List<GroupSelectNameVO>> getGroupOptionName(@Validated IdVO idVO) {
        try {
            Model model = modelBussiness.getTrainByModelId(idVO.getId());
            if (ObjectUtils.isEmpty(model)) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            List<SampleGroupingBO> list = experimentalTrainBussiness.getGroupOptionName(model.getExperimentId(), true,false);
            List<GroupSelectNameVO> result = JSON.parseArray(JSON.toJSONString(list), GroupSelectNameVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("发布管理-千人千面-获得下拉组选项异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


    @GetMapping("getBatchOption")
    @ApiOperation("获得下拉批次选项 传入模型ID")
    public Result<List<BatchSelectNameVO>> getBatchOption(@Validated IdVO idVO) {
        try {
            Model model = modelBussiness.getTrainByModelId(idVO.getId());
            if (ObjectUtils.isEmpty(model)) {
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
            }
            BatchRecordBO batchRecordBO = new BatchRecordBO();
            batchRecordBO.setExperimentId(model.getExperimentId());
            List<BatchRecordBO> batchList = experimentalTrainBussiness.findBatchRecordBOList(batchRecordBO, false);
            List<BatchSelectNameVO> result = JSON.parseArray(JSON.toJSONString(batchList), BatchSelectNameVO.class);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), result);
        } catch (Exception e) {
            logger.error("发布管理-千人千面-获得下拉批次选项异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }


}
