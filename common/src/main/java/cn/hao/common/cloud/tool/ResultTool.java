package cn.hao.common.cloud.tool;


import cn.hao.common.entity.Result;

public class ResultTool<T> {

    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "success", data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(Integer code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(500, msg, null);
    }

    public static <T> Result<T> error(T data) {
        return new Result<T>(500, "error", data);
    }

    public static <T> Result<T> error(Integer code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }

}
