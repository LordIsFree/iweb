package com.blog;

import com.blog.data.param.PageParams;
import com.blog.data.vo.ArticleVo;
import com.blog.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Mytest {
    @Autowired
    ArticleService articleService;
    @Test
    public void test1(){
        List<ArticleVo> articleVos = articleService.listArticlesPage(new PageParams());
        System.out.println(articleVos);
    }
}
