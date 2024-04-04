package com.project.backend.mappers;

import com.project.backend.dto.CommentDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    List<CommentDto> selectCommentByPostNo(String postNo);

    int insertComment(CommentDto commentDto);
    int updateComment(CommentDto commentDto);
    int deleteComment(CommentDto commentDto);
}
