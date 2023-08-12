package com.blog.data.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginParams {
@NotNull(message = "不能为空")
    private String account;
    private String password;
    private String nickname;
}
