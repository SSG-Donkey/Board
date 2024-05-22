package com.project.backend.mappers;

import com.project.backend.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<CommentDto> selectCommentByPostNo(String postNo);
    int insertComment(CommentDto commentDto);
    int updateComment(CommentDto commentDto);
    int deleteComment(CommentDto commentDto);
    int  validateComment(CommentDto commentDto);
    int cutPoint(String user_no,String point);

    int givePoint(String user_no,String point);
    int postChosen(String postNo);
    int nochooseComment(String comment_no);
    int chooseComment(String comment_no);



}
