package com.blog.data.pojo;

import lombok.Data;

@Data
public class SysUser {
    private Long id;
    private String account;
    private Integer admin;
    private String avatar;
    private Long createDate;
    private Integer deleted;
    private String email;
    private Long lastLogin;
    private String nickname;
    private String password;
    private String salt;
    private String status;
}
