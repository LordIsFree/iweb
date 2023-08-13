package com.blog.service;

import com.blog.data.param.CommentParam;
import com.blog.data.vo.Result;

public interface ConmmentsService {
    Result commentsByArticleId(long articleId);

    Result comment(CommentParam commentParam);
}
