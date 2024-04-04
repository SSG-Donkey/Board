package com.project.backend.controller;

import com.project.backend.dto.CommentDto;
import com.project.backend.service.CommentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/selectCommentByPostNo")
    public ResponseEntity<List<CommentDto>> selectCommentByPostNo(@RequestParam String postNo){
        return new ResponseEntity<>(commentService.selectCommentByPostNo(postNo), HttpStatus.OK);
    }

    //댓글 등록하기
    @PostMapping("/insertComment")
    public int insertComment(@RequestBody CommentDto commentDto){
        return commentService.insertComment(commentDto);
    }

    //댓글 수정하기
    @PostMapping("/updateComment")
    public int updateComment(@RequestBody CommentDto commentDto){
        return commentService.updateComment(commentDto);
    }

    //댓글 삭제하기
    @PostMapping("/deleteComment")
    public int deleteComment(@RequestBody CommentDto commentDto){
        return commentService.deleteComment(commentDto);
    }
}
