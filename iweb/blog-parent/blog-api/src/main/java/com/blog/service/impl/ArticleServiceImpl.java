package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.data.param.PageParams;
import com.blog.data.pojo.Article;
import com.blog.data.vo.ArticleVo;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {
        //数据库拿Articles数据
        Page page = new Page(pageParams.getPage(), pageParams.getPageSize());
        Page pages = articleMapper.selectPage(page, new QueryWrapper<Article>());
        //page对象转为list集合
        List<Article> articleList = pages.getRecords();
        //articleList转为articleVoList
        List<ArticleVo> articleVoList = copyArticleList(articleList);
        return articleVoList;
    }

    /**
     * 把数据库查到的文章结果集经行处理，仅仅向前端展现部分
     * article转为articleVo
     *
     * @param articleList 文章列表
     * @return {@link List}<{@link ArticleVo}>
     */
    private List<ArticleVo> copyArticleList(List<Article> articleList) {
        ArrayList<ArticleVo> articleVoList = new ArrayList<>();
        //articleList转为articleList
        for (Article article : articleList) {
            //article转为articleVo
            ArticleVo articleVo = copyArticleVo(article);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }

    /**
     * 把article转为articleVo
     *
     * @param article 文章
     * @return {@link ArticleVo}
     */
    private ArticleVo copyArticleVo(Article article) {
        ArticleVo articleVo = new ArticleVo();
        //article转为articleVo
        BeanUtils.copyProperties(article, articleVo);
        //毫毛日期转成正常日期
        Date date = new Date(article.getCreateDate());
        //调整提日期格式
        String createDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        //更新日期
        articleVo.setCreateDate(createDate);
        return articleVo;
    }
}
