package com.blog.data.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TagVo implements Serializable {
    private Long id;
    private String tagName;
}
