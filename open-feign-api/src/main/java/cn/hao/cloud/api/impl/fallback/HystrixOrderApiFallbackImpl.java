package cn.hao.cloud.api.impl.fallback;

import cn.hao.cloud.api.HystrixOrderApi;
import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Result;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * openFeign的降级处理的实现
 */
@Component
public class HystrixOrderApiFallbackImpl implements HystrixOrderApi {
    @Override
    public Result<Map<String, Object>> get(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("msg", "对方系统无反应");
        return ResultTool.error(map);
    }

    @Override
    public Result responseInThreeSecondLater() {
        return ResultTool.success("对方系统无反应");
    }

    @Override
    public Result runtimeException() {
        return ResultTool.success("对方系统无反应");
    }
}
