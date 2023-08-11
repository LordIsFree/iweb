package com.blog.data.pojo;

import lombok.Data;

@Data
public class Tag {
    //标签id
    private Long id;
    private String avatar;
    //标签名字
    private String tagName;
}
