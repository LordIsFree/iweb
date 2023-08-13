package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.data.pojo.Category;
import com.blog.data.vo.CategoryVo;
import com.blog.data.vo.Result;
import com.blog.mapper.CategoryMapper;
import com.blog.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategoryById(Long id) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId,id);
        Category category = categoryMapper.selectOne(queryWrapper);
        //Category转化CategoryVo
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAll() {
        List<Category> categories = this.categoryMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyCategory(categories));
    }
    private List<CategoryVo> copyCategory(List<Category> categoryList){
        ArrayList<CategoryVo> categoryvoArrayList = new ArrayList<>();
        for (Category category: categoryList){
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            categoryvoArrayList.add(categoryVo);
        }
        return categoryvoArrayList;
    }
}
