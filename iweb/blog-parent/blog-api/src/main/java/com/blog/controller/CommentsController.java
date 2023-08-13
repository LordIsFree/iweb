package com.blog.controller;

import com.blog.data.param.CommentParam;
import com.blog.data.vo.Result;
import com.blog.service.ConmmentsService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    ConmmentsService conmmentsServiceImpl;
    @GetMapping("article/{id}")
    public Result conmments(@PathVariable("id") long articleId){
        return conmmentsServiceImpl.commentsByArticleId(articleId);
    }
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return conmmentsServiceImpl.comment(commentParam);
    }
}