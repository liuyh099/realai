package cn.realai.online.core.controller;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bussiness.ExperimentalTrainBusiness;
import cn.realai.online.core.query.ExperimentalTrainCreateModelDataQuery;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experimental/train")
@Api(tags="实验训练API")
public class ExperimentalTrainController {


    @Autowired
    private ExperimentalTrainBusiness experimentalTrainBusiness;

    @GetMapping
    @ApiOperation(value="查询实验训练列表")
    @ResponseBody
    public Result<PageBO<ExperimentalTrainVO>> list(ExperimentalTrainQuery experimentalTrainQuery){
        PageBO<ExperimentalTrainVO> page = experimentalTrainBusiness.pageList(experimentalTrainQuery);
        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(),page);
    }

    @DeleteMapping
    @ApiOperation(value="删除实验训练列表")
    @ResponseBody
    public Result delete(@RequestBody List<Long> ids){
        return  null;
    }


    @GetMapping("/detail/{trainId}")
    @ApiOperation(value="实验详情")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result detail(){

        return  null;
    }


    @GetMapping("/select/{serverId}")
    @ApiOperation(value="根据服务id活得实验名称列表")
    @ApiImplicitParam(name = "serverId", value = "服务ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<TrainNameSelectVO> getSelect(){
        return  null;
    }


    @PostMapping("/selectFile")
    @ApiOperation(value="新增实验-选择文件")
    @ResponseBody
    public Result<IdVO> selectFileAdd(@RequestBody ExperimentalTrainSelectFileVO experimentalTrainSelectFileVo){
       return null;
    }

    @GetMapping("/selectFile/{trainId}")
    @ApiOperation(value="活得选择文件的结果")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<ExperimentalTrainSelectFileVO> selectFileQuery(@PathVariable  long trainId ){

        return  null;
    }

    @PutMapping("/selectFile")
    @ApiOperation(value="更新选择文件内容")
    @ResponseBody
    public Result selectFileUpdate(@RequestBody ExperimentalTrainSelectFileVO experimentalTrainSelectFileVo ){
        return  null;
    }

    @PostMapping("/selectParam")
    @ApiOperation(value="新增实验-选择参数")
    @ResponseBody
    public Result<IdVO> selectParamAdd(@RequestBody ExperimentalTrainSelectParamVO experimentalTrainSelectParamVo){
        return null;
    }

    @GetMapping("/selectParam/{trainId}")
    @ApiOperation(value="获得选择参数的结果")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<ExperimentalTrainSelectParamVO> selectParamQuery(@PathVariable  long trainId ){
        return  null;
    }

    @PutMapping("/selectParam")
    @ApiOperation(value="更新选择参数内容")
    @ResponseBody
    public Result selectParamUpdate(@RequestBody ExperimentalTrainSelectParamVO experimentalTrainSelectParamVo ){
        return  null;
    }

    @GetMapping("/createModel/{trainId}")
    @ApiOperation(value="新增实验-生成模型")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<ExperimentalTrainCreateModelCheckVO> createModelQuery(@PathVariable  long trainId ){
        return  null;
    }

    @DeleteMapping("/createModel")
    @ApiOperation(value="删除同质或者异质量数据")
    @ResponseBody
    public Result createModelDelete(@RequestBody ExperimentalTrainCreateModelVO experimentalTrainCreateModelVo ){
        return  null;
    }

    @PutMapping("/createModel/{trainId}")
    @ApiOperation(value="新增实验-生成模型-一键建立模型")
    @ApiImplicitParam(name = "trainId", value = "实验ID", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result createModel(@PathVariable  long trainId ){
        return  null;
    }


    @GetMapping("/createModel/getData")
    @ApiOperation(value="新增实验-生成模型-一获取同质异质数据")
    @ResponseBody
    public Result<PageBO<ExperimentalTrainCreateModelDataVO>> createModelGetData(@RequestBody ExperimentalTrainCreateModelDataQuery experimentalTrainCreateModelDataQuery){
        return  null;
    }


    @PutMapping("/doubleCreate")
    @ApiOperation(value="二次创建实验")
    @ResponseBody
    public Result doubleCreate(@RequestBody ExperimentalTrainDoubleCreateVO experimentalTrainDoubleCreateVo){
        return  null;
    }



}
