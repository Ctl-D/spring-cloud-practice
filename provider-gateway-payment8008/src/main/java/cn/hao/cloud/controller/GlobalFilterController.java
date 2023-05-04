package cn.hao.cloud.controller;

import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("checkGlobalFilter")
public class GlobalFilterController {

    @GetMapping("parameterCheck")
    public Result<String> parameterCheck() {
        return ResultTool.success("have require parameter");
    }
}
