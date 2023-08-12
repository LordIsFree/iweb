package com.blog.data.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysUser {
//    @TableId(type = IdType.ASSIGN_ID) 默认就是雪花算法
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
