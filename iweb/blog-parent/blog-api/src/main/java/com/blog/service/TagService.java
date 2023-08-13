package com.blog.service;

import com.blog.data.pojo.Tag;
import com.blog.data.vo.Result;
import com.blog.data.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long id);
    List<TagVo> hot(int limit);

    Result findAll();
}
