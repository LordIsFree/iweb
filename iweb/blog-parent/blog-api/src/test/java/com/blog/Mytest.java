package com.blog;

import com.blog.data.param.PageParams;
import com.blog.data.pojo.Archives;
import com.blog.data.pojo.Tag;
import com.blog.data.vo.ArticleVo;
import com.blog.data.vo.TagVo;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.TagMapper;
import com.blog.service.ArticleService;
import com.blog.service.TagService;
import com.sun.xml.internal.ws.api.model.MEP;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

@SpringBootTest
public class Mytest {
    @Autowired
    ArticleService articleService;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    TagService tagServiceImpl;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test1() {
        List<ArticleVo> articleVos = articleService.listArticlesPage(new PageParams());
        System.out.println(articleVos);
    }

    @Test
    public void test2() {
        List<Tag> hot = tagMapper.hot(3);
        System.out.println(hot);
    }

    @Test
    public void test3() {
        List<Long> articleIds = tagMapper.findArticleIds(5L);
        List<ArticleVo> articles = articleService.findArticles(articleIds);
        System.out.println(articles);
    }

    @Test
    public void test4() {
        List<TagVo> hot = tagServiceImpl.hot(3);
        hot.forEach(System.out::println);
        Map map = redisTemplate.opsForHash().entries("hot");
        Set<Long> keySet = map.keySet();
        for (Long key : keySet){
            System.out.println(key);
            List<ArticleVo> value = (List<ArticleVo>) map.get(key);
            value.forEach(System.out::println);
        }
    }

    @Test
    public void test5() {
        List<Archives> archivesList = articleService.listArchives();
        archivesList.forEach(System.out::println);
    }
}
