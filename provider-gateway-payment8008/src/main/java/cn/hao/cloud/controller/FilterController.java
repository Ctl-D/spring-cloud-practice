package cn.hao.cloud.controller;

import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("filter")
public class FilterController {


    @GetMapping("addRequestParameter")
    public Result<String> addRequestParameter(String userName, HttpServletRequest request) {
        return ResultTool.success(String.format("filter addRequestParameter success：userName=%s", userName));
    }

    @GetMapping("prefixPath")
    public Result<String> prefixPath(HttpServletRequest request) {
        return ResultTool.success("filter prefixPath uri ：/prefixPath add prefix /filter success");
    }

    @GetMapping("modifyResponseBody")
    public Result<String> modifyResponseBody() {
        return ResultTool.success("filter modify response body success");
    }
}
