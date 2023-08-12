package com.blog.controller;

import com.blog.data.param.PageParams;
import com.blog.data.pojo.Archives;
import com.blog.data.vo.ArticleVo;
import com.blog.data.vo.Result;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("articles")
@CrossOrigin
public class ArticleController {
    @Autowired
    ArticleService articleServiceImpl;

    /**
     * 文章分页展示请求
     *
     * @param pageParams 页面参数
     * @return {@link Result}
     */
    @PostMapping
    public Result articles(@RequestBody PageParams pageParams) {
        //调service获得一个vo文章集合
        List<ArticleVo> articleVoList = articleServiceImpl.listArticlesPage(pageParams);
        //vo文章集合 放入放回前端的结果对象
        return Result.success(articleVoList);
    }
    @PostMapping ("hot")
    public Result hot(){
        List<ArticleVo> hot = articleServiceImpl.hot(5);
        return Result.success(hot);
    }

    @PostMapping ("new")
    public Result news(){
        List<ArticleVo> hot = articleServiceImpl.news(3);
        return Result.success(hot);
    }
    @PostMapping("listArchives")
    public Result listArchives(){
        List<Archives> archivesList = articleServiceImpl.listArchives();
        return Result.success(archivesList);
    }
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id) {
        ArticleVo articleVo = articleServiceImpl.findArticleById(id);
        return Result.success(articleVo);
    }
}
