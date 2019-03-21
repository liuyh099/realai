package cn.realai.online.core.controller;


import cn.realai.online.common.page.PageBO;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.core.bo.BatchDetailBO;
import cn.realai.online.core.bussiness.BatchRecordBussiness;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.query.OfflineBatchListQuery;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.core.vo.OfflineBatchCompleteVO;
import cn.realai.online.core.vo.OfflineBatchCreateVO;
import cn.realai.online.core.vo.OfflineBatchDetailVO;
import cn.realai.online.core.vo.OfflineBatchListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能描述：离线跑批管理
 *
 * @author admin
 * @create 2019-03-18 14:13
 */

@RestController
@RequestMapping("/offlineBatch")
@Api(tags = "离线跑批管理API")
public class OfflineBatchController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BatchRecordBussiness batchRecordBussiness;
    @Autowired
    private BatchRecordService batchRecordService;

    @PostMapping("/create")
    @ApiOperation(value="新建离线跑批")
    @ResponseBody
    public Result<Long> create(OfflineBatchCreateVO createVO){
        try {
            Long recordId = batchRecordBussiness.createBatchRecord(createVO);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), recordId);
        } catch (Exception e) {
            log.error("新建离线跑批异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询离线跑批列表")
    @ResponseBody
    public Result<PageBO<OfflineBatchListVO>> list(OfflineBatchListQuery query) {
        try {
            PageBO<OfflineBatchListVO> page = batchRecordBussiness.pageList(query);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), page);
        } catch (Exception e) {
            log.error("查询离线跑批列表异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping("delete")
    @ApiOperation(value="批量删除离线跑批记录")
    @ResponseBody
    public Result<Void> delete(@RequestBody List<Long> idList) {
        try {
            Assert.notEmpty(idList, "待删除的跑批记录不能为空");
            batchRecordService.delete(idList);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            log.error("删除跑批记录异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }



    @GetMapping("/download/{batchId}")
    @ApiOperation(value = "下载离线跑批运算结果")
    @ApiImplicitParam(name = "batchId", value = "离线跑批Id", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public void download(@PathVariable("batchId") Long batchId){
        try {

        } catch (Exception e) {
            log.error("下载离线跑批结果异常", e);
        }
    }

    @GetMapping("/getComplete/{batchId}")
    @ApiOperation(value="查询离线跑批是否计算完成")
    @ApiImplicitParam(name = "batchId", value = "离线跑批Id", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<OfflineBatchCompleteVO> getComplete(@PathVariable("batchId") Long batchId){
        try {
            BatchRecord record = new BatchRecord();
            record.setId(batchId);
            record = batchRecordService.getByEntity(record);
            Assert.notNull(record, "该离线跑批记录不存在");
            OfflineBatchCompleteVO resultVO = new OfflineBatchCompleteVO();
            resultVO.setBatchid(batchId);
            resultVO.setComplete(record != null && StringUtils.isNotEmpty(record.getDownUrl()));
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), resultVO);
        } catch (Exception e) {
            log.error("查询离线跑批是否计算完成异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }


    @GetMapping("/detail/{batchId}")
    @ApiOperation(value="查询离线跑批详情")
    @ApiImplicitParam(name = "batchId", value = "离线跑批Id", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<OfflineBatchDetailVO> detail(@PathVariable("batchId") Long batchId){
        try {
            BatchDetailBO resultBO = batchRecordService.selectDetail(batchId);
            Assert.notNull(resultBO, "未找到对应跑批记录详情");
            OfflineBatchDetailVO resultVO = new OfflineBatchDetailVO();
            BeanUtils.copyProperties(resultBO, resultVO);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), resultVO);
        } catch (Exception e) {
            log.error("查询离线跑批是否计算完成异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }
}
