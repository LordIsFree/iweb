package com.blog.service;

import com.blog.data.param.ArticleParam;
import com.blog.data.param.PageParams;
import com.blog.data.pojo.Archives;
import com.blog.data.vo.ArticleVo;
import com.blog.data.vo.Result;

import java.util.List;

public interface ArticleService {
    List<ArticleVo> listArticlesPage(PageParams pageParams);
    List<ArticleVo> findArticles(List<Long> articleIds);
    List<ArticleVo> hot(int size);

    List<ArticleVo> news(int size);

    List<Archives> listArchives();

    ArticleVo findArticleById(Long id);

    Result publish(ArticleParam articleParam);
}
