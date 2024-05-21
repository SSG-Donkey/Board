package com.project.backend.controller;

import com.project.backend.dto.CommentDto;
import com.project.backend.service.CommentService;
import com.project.backend.service.PostService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    private CommentDto commentDto;

    @GetMapping("/selectCommentByPostNo")
    public ResponseEntity<List<CommentDto>> selectCommentByPostNo(@RequestParam String postNo){
        return new ResponseEntity<>(commentService.selectCommentByPostNo(postNo), HttpStatus.OK);
    }

    //댓글 등록하기
    @PostMapping("/insertComment")
    public Map<String, String> insertComment(@ModelAttribute CommentDto commentDto) {
        log.info("controller 진입");
        int posts = commentService.insertComment(commentDto);
        Map<String, String> response = new HashMap<>();
        if (posts == 1) {
            response.put("message", "댓글 추가 되었습니다.");
            response.put("redirectUrl", "/boardDetail.html?postNo=" + commentDto.getPostNo());
        } else {
            response.put("message", "게시글 등록 실패 하였습니다.");
            response.put("redirectUrl", "/board.html");
        }
        return response;
    }

    //댓글 수정하기
    @PostMapping("/updateComment")
    public int updateComment(@RequestBody CommentDto commentDto){
        return commentService.updateComment(commentDto);
    }

    //댓글 삭제하기
    @PostMapping("/deleteComment")
    public Map<String,String> deleteComment(@RequestParam("postNo")String postNo,
                                            @RequestParam("userNo")String userNo,
                                            @RequestParam("commentNo")String commentNo){

        System.out.printf("comment_no = %s , post_no =%s user_no =%s",commentNo,postNo,userNo);
        int comment_no=Integer.parseInt(commentNo);

        commentDto.setPostNo(postNo);
        commentDto.setUserNo(userNo);
        commentDto.setCommentNo(commentNo);
        int res2 =commentService.validateComment(commentDto);

        Map<String,String> response =new HashMap<>();
        System.out.printf(" res1=%d ,res2=%d\n",commentNo,res2);
        if(comment_no== res2){
            response.put("message", "댓글 삭제 완료하였습니다.");
            response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);

        }
        else{
            response.put("message", "댓글 삭제 실패하였습니다!");
            response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);

        }

        return response;
    }
}
