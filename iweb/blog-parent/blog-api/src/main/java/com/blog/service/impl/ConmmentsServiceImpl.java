package com.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.data.param.CommentParam;
import com.blog.data.pojo.Comment;
import com.blog.data.pojo.SysUser;
import com.blog.data.util.UserThreadLocal;
import com.blog.data.vo.CommentVo;
import com.blog.data.vo.Result;
import com.blog.data.vo.UserVo;
import com.blog.mapper.CommentMapper;
import com.blog.service.ConmmentsService;
import com.blog.service.SysUserService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConmmentsServiceImpl implements ConmmentsService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    SysUserService sysUserServiceImpl;
    @Override
    public Result commentsByArticleId(long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        //一级评论 即对文章的评论
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return Result.success(copyCommentList(comments));
    }

    /**
     * comment转化CommentVo
     * 有两种评论：
     * 1 对文章的评论
     * 2 对评论的评论
     * 两种评论的复制方式略有不同 不同在评论等大于1时的
     * if (comment.getLevel() > 1)
     *
     * @param comment 评论
     * @return {@link CommentVo}
     */
    private CommentVo copyComment(Comment comment){
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //毫秒日期转为正常日期 日期格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        Long authorId = comment.getAuthorId();
        //作者id 查作者信息
        UserVo userVo = sysUserServiceImpl.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //评论的评论(回复) 三个个函数形成的递归 无限套娃
        // copyComment -> findCommentcsByParentId
        // findCommentcsByParentId -> copyCommentList
        // copyCommentList -> copyComment
        List<CommentVo> commentVoList = findCommentcsByParentId(comment.getId());
        commentVo.setChildrens(commentVoList);
        //如果复制的评论是二级评论 还要做处理
        if (comment.getLevel() > 1) {
            //二级评论 回复 的评论对象
            Long toUid = comment.getToUid();
            //回复 评论的作者
            UserVo toUserVo = sysUserServiceImpl.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;

    }

    private List<CommentVo> findCommentcsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //找到有父亲评论的评论 表示该评论是 他人评论的回复
        queryWrapper.eq(Comment::getParentId,id);
        //二级评论 即对文章评论的回复
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> commentList = commentMapper.selectList(queryWrapper);
        return copyCommentList(commentList);
    }
    private List<CommentVo> copyCommentList(List<Comment> commentList){
        ArrayList<CommentVo> commentVosList= new ArrayList<>();
        for (Comment comment:commentList){
            //复制评论
            commentVosList.add(copyComment(comment));
        }
        return commentVosList;
    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        //评论分级
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        //添加数据库
        commentMapper.insert(comment);
        return Result.success(null);
//        @JsonSerialize(using = ToStringSerializer.class)
//        private Long id;
    }


}
