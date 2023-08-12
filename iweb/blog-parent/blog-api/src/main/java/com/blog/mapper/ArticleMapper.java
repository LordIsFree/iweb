package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.data.pojo.Archives;
import com.blog.data.pojo.Article;
import com.blog.data.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> findArticles(List<Long> articleIds);
    List<Article> hot(int size);

    List<Article> news(int size);

    List<Archives> listArchives();
}
