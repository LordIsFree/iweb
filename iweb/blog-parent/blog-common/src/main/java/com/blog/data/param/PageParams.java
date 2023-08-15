package com.blog.data.param;

import lombok.Data;

@Data
public class PageParams {
    //当前页码
    private int page=1;
    //当前页面条数
    private int pageSize=3;

    private Long categoryId;

    private Long tagId;
}
