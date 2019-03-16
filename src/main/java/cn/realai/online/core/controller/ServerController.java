package cn.realai.online.core.controller;


import cn.realai.online.common.vo.Result;
import cn.realai.online.core.vo.ServerNameSelectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/server")
@Api(tags="服务API")
public class ServerController {


    @GetMapping("/select")
    @ApiOperation(value="查询服务下拉选项")
    public Result<List<ServerNameSelectVO>> getSelect(){
        return null;
    }

}
