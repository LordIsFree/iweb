package com.blog.controller;

import com.blog.data.vo.Result;
import com.blog.data.vo.TagVo;
import com.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagController {
    @Autowired
    TagService tagServiceImpl;
    /**
     * 最热标签请求
     *
     * @return {@link String}
     */
    @GetMapping("hot")
    public Result hot(int limit){
        List<TagVo> hots = tagServiceImpl.hot(limit);
        return Result.success(hots);
    }
}
