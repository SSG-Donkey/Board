package com.project.backend.service;

import com.project.backend.dto.CommentDto;
import com.project.backend.mappers.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    public CommentDto selectCommentByPostNo(String postNo){
        CommentDto res = commentMapper.selectCommentByPostNo(postNo);

        return res;
    }
}
