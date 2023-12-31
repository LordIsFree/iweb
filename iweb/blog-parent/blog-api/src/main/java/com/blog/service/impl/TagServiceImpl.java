package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.blog.data.pojo.Tag;
import com.blog.data.vo.ArticleVo;
import com.blog.data.vo.Result;
import com.blog.data.vo.TagVo;
import com.blog.mapper.TagMapper;
import com.blog.service.ArticleService;
import com.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<TagVo> findTagsByArticleId(Long id) {
        //根据id 查询出多个Tag对象
        List<Tag> tagList = tagMapper.fingTags(id);
        //Tag->TagVo
        List<TagVo> tagVoList = copyList(tagList);
        return tagVoList;
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tag, tagVo);
            tagVoList.add(tagVo);
        }
        return tagVoList;
    }

    /**
     * 获取最热的前limit个标签
     *
     * @param limit 表示查询前几个元素
     * @return {@link List}<{@link TagVo}>
     */
    public List<TagVo> hot(int limit) {
        //去数据库中查询寻最热的limit个标签
        List<Tag> tagList = tagMapper.hot(limit);
        //对于查出来的数据要经行，空处理
        if(CollectionUtils.isEmpty(tagList)){
            return Collections.emptyList();
        }
        //转化为List<TagVo>
        List<TagVo> tagVoList = copyList(tagList);
        //根据标签id 到中间表 查询文章id
        for (TagVo tagVo : tagVoList) {
            //根据标签id 到中间表 查询文章id集合
            List<Long> articleIds = tagMapper.findArticleIds(tagVo.getId());
            //根据文章id集合
            List<ArticleVo> articleVoList = articleService.findArticles(articleIds);
            //把文章按标签分类放入redis缓存中
            redisTemplate.opsForHash().put("hot", tagVo.getId(), articleVoList);
            //设置失效时间
            redisTemplate.expire("hot",10, TimeUnit.MINUTES);
        }
        return tagVoList;
    }

    @Override
    public Result findAll() {
        List<Tag> tagList = tagMapper.selectList(new QueryWrapper<Tag>());
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAllDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return Result.success(tagVo);
    }

}
