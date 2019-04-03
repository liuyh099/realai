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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @RequiresPermissions("model:offlinerun")
    @PostMapping("/create")
    @ApiOperation(value="新建离线跑批")
    @ResponseBody
    public Result<Long> create(@RequestBody @Validated OfflineBatchCreateVO createVO){
        try {
        	BatchRecord batchRecord = batchRecordBussiness.createBatchRecord(createVO);
        	if (batchRecord != null && false) {
        		int ret = batchRecordBussiness.executeBatchRecord(batchRecord);
        		if (ret == -1) {
        			return new Result(ResultCode.PYTHON_WAIT.getCode(), ResultMessage.PYTHON_WAIT.getMsg(), batchRecord.getId());
        		}
        		return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), batchRecord.getId());
        	} 
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        } catch (Exception e) {
            log.error("新建离线跑批异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

    @RequiresPermissions("model:offlinerun")
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

    @RequiresPermissions("model:offlinerun")
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


    @RequiresPermissions("model:offlinerun")
    @GetMapping("/download/{batchId}")
    @ApiOperation(value = "下载离线跑批运算结果")
    @ApiImplicitParam(name = "batchId", value = "离线跑批Id", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<Void> download(@PathVariable("batchId") Long batchId, HttpServletRequest request, HttpServletResponse response) {
        try {
            BatchRecord record = new BatchRecord();
            record.setId(batchId);
            record = batchRecordService.getByEntity(record);
            Assert.notNull(record, "跑批记录不存在");
            Assert.hasLength(record.getDownUrl(), "跑批记录尚未生成下载结果");
            String filename = new File(record.getDownUrl()).getName();

            URL path = new URL(record.getDownUrl());
            HttpURLConnection conn = (HttpURLConnection) path.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

            response.addHeader("Content-Disposition","attachment;filename="+ new String(filename.getBytes("UTF-8"),"ISO8859_1"));
            response.addHeader("Content-Length", "" + conn.getContentLength());
            response.setContentType("application/octet-stream");

            byte[] bytes = new byte[1024];
            int length;
            while ((length = bis.read(bytes)) > 0) {
                out.write(bytes, 0, length);
            }
            bis.close();
            out.flush();
            out.close();
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
        } catch (Exception e) {
            log.error("下载离线跑批结果异常:", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }

    }

    @RequiresPermissions("model:offlinerun")
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
            resultVO.setComplete(record != null && record.getStatus() == BatchRecord.BATCH_STATUS_OVER);
            return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), resultVO);
        } catch (Exception e) {
            log.error("查询离线跑批是否计算完成异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @RequiresPermissions("model:offlinerun")
    @GetMapping("/execute/{batchId}")
    @ApiOperation(value="手动执行跑批运算")
    @ApiImplicitParam(name = "batchId", value = "离线跑批Id", required = true, dataType = "Long", paramType = "path")
    @ResponseBody
    public Result<Long> execute(@PathVariable Long batchId) {
        try {
            BatchRecord record = new BatchRecord();
            record.setId(batchId);
            record = batchRecordService.getByEntity(record);
            if (record.getStatus() == BatchRecord.BATCH_STATUS_OVER || record.getStatus() == BatchRecord.BATCH_STATUS_EXECUTING) {
                throw new Exception("该跑批记录不处于新建或者处理有误状态");
            }
            if (record != null) {
                int ret = batchRecordBussiness.executeBatchRecord(record);
                if (ret == -1) {
                    return new Result(ResultCode.PYTHON_WAIT.getCode(), ResultMessage.PYTHON_WAIT.getMsg(), record.getId());
                }
                return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), record.getId());
            }
            return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
        } catch (Exception e) {
            log.error("查询离线跑批是否计算完成异常", e);
            return new Result(ResultCode.DATA_ERROR.getCode(), e.getMessage(), null);
        }
    }

}
