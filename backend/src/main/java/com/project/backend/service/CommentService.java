package com.project.backend.service;

import com.project.backend.dto.CommentDto;
import com.project.backend.mappers.CommentMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    public List<CommentDto> selectCommentByPostNo(String postNo){
        List<CommentDto> res = commentMapper.selectCommentByPostNo(postNo);
        return res;
    }

    public int insertComment(CommentDto commentDto){
        int res = commentMapper.insertComment(commentDto);

        return res;
    }

    public int updateComment(CommentDto commentDto){
        int res = commentMapper.updateComment(commentDto);

        return res;
    }
    public int validateComment(CommentDto commentDto){
        int res = commentMapper.validateComment(commentDto);
        return res;
    }

    public int deleteComment(CommentDto commentDto){
        int res = commentMapper.deleteComment(commentDto);

        return res;
    }
}
