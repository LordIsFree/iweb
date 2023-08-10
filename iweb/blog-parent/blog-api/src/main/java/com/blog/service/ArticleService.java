package com.blog.service;

import com.blog.data.param.PageParams;
import com.blog.data.vo.ArticleVo;

import java.util.List;

public interface ArticleService {
    List<ArticleVo> listArticlesPage(PageParams pageParams);
}
