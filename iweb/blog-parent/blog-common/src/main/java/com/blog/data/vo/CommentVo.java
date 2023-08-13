package com.blog.data.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo  {
    //品论id
    private Long id;
    //评论人信息
    private UserVo author;
    //评论内容
    private String content;
    //评论的评论链条
    private List<CommentVo> childrens;
    //评论日期
    private String createDate;
    //几级评论 该评论是 对别人评论的回复 还是对文章的评论
    private Integer level;
    //被评论的用户
    private UserVo toUser;
}