package com.project.backend.mappers;

import com.project.backend.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    CommentDto selectCommentByPostNo(String postNo);
}
