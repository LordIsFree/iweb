package com.blog.service;

import com.blog.data.vo.CategoryVo;
import com.blog.data.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryById(Long id);

    Result findAll();
}
