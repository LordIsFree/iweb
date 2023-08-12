package com.blog.data.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleVo implements Serializable {
    private Long id;
    private String title;
    private String summary;
    private int commentCounts;
    private int viewCounts;
    private int weight;
    private String createDate;
    private String author;
    private List<TagVo> tags;
    //以上为老师上课讲的属性  以下为看文档后新增加属性
    private ArticleBodyVo body;
    private CategoryVo category;
}
