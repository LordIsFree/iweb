package com.blog.controller;

import com.blog.data.vo.Result;
import com.blog.data.vo.TagVo;
import com.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tags")
@CrossOrigin
public class TagController {
    @Autowired
    TagService tagServiceImpl;
    /**
     * 最热标签请求
     *
     * @return {@link String}
     */

    @GetMapping("hot")
    public Result hot(){
        List<TagVo> hots = tagServiceImpl.hot(3);
        return Result.success(hots);
    }
    @GetMapping
    public Result findAll(){
        return tagServiceImpl.findAll();
    }
    @GetMapping("detail")
    public Result findAllDetail(){
        return tagServiceImpl.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagServiceImpl.findDetailById(id);
    }
}
