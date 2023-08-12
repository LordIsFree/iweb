package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blog.data.param.PageParams;
import com.blog.data.pojo.Archives;
import com.blog.data.pojo.Article;
import com.blog.data.pojo.ArticleBody;
import com.blog.data.vo.ArticleBodyVo;
import com.blog.data.vo.ArticleVo;
import com.blog.data.vo.CategoryVo;
import com.blog.data.vo.TagVo;
import com.blog.mapper.ArticleBodyMapper;
import com.blog.mapper.ArticleMapper;
import com.blog.service.*;
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
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryServiceImpl;
    @Autowired
    ThreadService threadService;

    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {
        //数据库拿Articles数据
        Page page = new Page(pageParams.getPage(), pageParams.getPageSize());
        Page pages = articleMapper.selectPage(page, new QueryWrapper<Article>());
        //page对象转为list集合
        List<Article> articleList = pages.getRecords();
        //articleList转为articleVoList
        List<ArticleVo> articleVoList = copyArticleList(articleList,true
                ,false,true,true,false);
        return articleVoList;
    }

    /**
     * 把数据库查到的文章结果集经行处理，仅仅向前端展现部分
     * article转为articleVo
     *
     * @param articleList 文章列表
     * @return {@link List}<{@link ArticleVo}>
     */
    private List<ArticleVo> copyArticleList(List<Article> articleList
            ,boolean isAuthor,boolean isBody,boolean isTag,boolean isDate
            ,boolean isCategory) {
        ArrayList<ArticleVo> articleVoList = new ArrayList<>();
        //articleList转为articleList
        for (Article article : articleList) {
            //article转为articleVo
            ArticleVo articleVo = copyArticleVo(article,isAuthor,isBody,isTag
                    ,isDate,isCategory);
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
    private ArticleVo copyArticleVo(Article article,boolean isAuthor,boolean isBody
            ,boolean isTag,boolean isDate,boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        if(isAuthor){
            //通过 作者id AuthorId在数据库中查询作者名 id->name
            String nickName = sysUserService.findNickNameByAuthorId(article.getAuthorId());
            articleVo.setAuthor(nickName);
        }
        //article转为articleVo
        BeanUtils.copyProperties(article, articleVo);
        if(isDate) {
            //毫秒日期转成正常日期
            Date date = new Date(article.getCreateDate());
            //调整提日期格式
            String createDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            //更新日期
            articleVo.setCreateDate(createDate);
        }
        if(isTag){
            List<TagVo> tagVoList = tagService.findTagsByArticleId(article.getAuthorId());
            articleVo.setTags(tagVoList);
        }
        if(isBody){
            ArticleBodyVo articleBodyVo = findArticleBody(article.getId());
            articleVo.setBody(articleBodyVo);
        }
        if(isCategory){
            CategoryVo categoryVo = findCategory(article.getCategoryId());
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }

    private CategoryVo findCategory(Long categoryId) {
        CategoryVo categoryVo = categoryServiceImpl.findCategoryById(categoryId);
        return categoryVo;
    }

    //通过文章id 获取文章主体
    private ArticleBodyVo findArticleBody(Long id) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId,id);
        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
        //ArticleBody 转换 BodyVo
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    public List<ArticleVo> findArticles(List<Long> articleIds){
        //一组文章id ,查询整个articleVo对象
        List<Article> articleList = articleMapper.findArticles(articleIds);
        List<ArticleVo> articleVoList = copyArticleList(articleList,true
                ,false,true,true,false);
        return articleVoList;
    }

    @Override
    public List<ArticleVo> hot(int limit) {
        //根据浏览点击数量，查询文章
        List<Article> articleList = articleMapper.hot(limit);
        List<ArticleVo> articleVoList = copyArticleList(articleList,true
                ,false,true,true,false);
        return articleVoList;
    }

    @Override
    public List<ArticleVo> news(int limit) {
        //根据时间倒序查询最新文章
        List<Article> articleList = articleMapper.news(limit);
        //articleList转化articleVoList
        List<ArticleVo> articleVoList = copyArticleList(articleList,true
                ,false,true,true,false);
        return articleVoList;
    }

    @Override
    public List<Archives> listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return archivesList;
    }
    //根据文章id 获得文章的所有容 即浏览文章 文章点击量加一
    @Override
    public ArticleVo findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        //更新阅读次数
        threadService.updateViewCount(articleMapper,article);
        ArticleVo articleVo = copyArticleVo(article,true,true
                ,true,true,true);
        return articleVo;
    }
}
