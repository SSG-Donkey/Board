package com.project.backend.service;

import com.project.backend.dto.CommentDto;
import com.project.backend.mappers.CommentMapper;
import com.project.backend.mappers.PostMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    PostMapper postMapper;
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
    public int postChosen(String postNo){
        int res=commentMapper.postChosen(postNo);
        return res;
    }

    public int deleteComment(CommentDto commentDto){
        int res = commentMapper.deleteComment(commentDto);

        return res;
    }
    public int cutPoint(String user_no,String point){
        int res= commentMapper.cutPoint(user_no,point);
        return res;
    }
    public int givePoint(String userNo,String Point){

        int res= commentMapper.givePoint(userNo,Point);
        System.out.printf("나눔시 user :%s 에게 point :%s 추가\n",userNo,Point);
        return res;
    }
    public int chooseComment(String comment_no){
        int res1=commentMapper.nochooseComment(comment_no); //채택되지않은 댓글이면
        if(res1==0){
            int res2=commentMapper.chooseComment(comment_no);
            return res2;
        }

        return 1;
    }
}
