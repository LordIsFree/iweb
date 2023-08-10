package com.blog.service.impl;

import com.blog.data.pojo.Tag;
import com.blog.data.vo.TagVo;
import com.blog.mapper.TagMapper;
import com.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagMapper tagMapper;
    @Override
    public List<TagVo> findTagsByArticleId(Long id) {
        //根据id 查询出多个Tag对象
        List<Tag> tagList = tagMapper.fingTags(id);
        //Tag->TagVo
        List<TagVo> tagVoList = copyList(tagList);
        return tagVoList;
    }

    @Override
    public List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag: tagList) {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tag,tagVo);
            tagVoList.add(tagVo);
        }
        return tagVoList;
    }
}
