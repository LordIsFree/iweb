package com.blog.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    //请求成功or失败
    private boolean success;
    //状态码
    private Integer code;
    //响应信息
    private String msg;
    //数据
    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(Integer code, String msg) {
        return new Result(false, code, msg, null);
    }

}
