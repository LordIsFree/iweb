package com.blog.data.pojo;

import lombok.Data;

@Data
public class Comment {
    //评论的id
    private Long id;
    //评论的内容
    private String content;
    //评论时间
    private Long createDate;
    //评论的文章id
    private Long articleId;
    //评论人
    private Long authorId;
    //？？？
    private Long parentId;
    //？？？
    private Long toUid;
    //？？？ 楼层？
    private Integer level;
}