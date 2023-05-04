package cn.hao.cloud.controller;

import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("predicate")
public class PredicateController {

    @GetMapping("after")
    public Result<String> after() {
        return ResultTool.success("predicate after success");
    }

    @GetMapping("before")
    public Result<String> before() {
        return ResultTool.success("predicate before success");
    }


    @GetMapping("between")
    public Result<String> between() {
        return ResultTool.success("predicate between success");
    }

    @GetMapping("cookie")
    public Result<String> cookie() {
        return ResultTool.success("predicate cookie success");
    }


    @GetMapping("query")
    public Result<String> query() {
        return ResultTool.success("predicate query success");
    }
}
