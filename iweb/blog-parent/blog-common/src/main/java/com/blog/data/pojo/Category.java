package com.blog.data.pojo;

import lombok.Data;
//Category 类别
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}