package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.blog.mapper")
public class BlogApi
{
    public static void main( String[] args ) {
        ConfigurableApplicationContext context = SpringApplication.run(BlogApi.class, args);
    }
}
