package com.blog.data.vo;

import lombok.Data;

@Data
public class ArticleVo {
    private Long id;
    private String title;
    private String summary;
    private int commentCounts;
    private int viewCounts;
    private int weight;
    private String createDate;
    private String author;
//    private List<TagVo> tags;
}
