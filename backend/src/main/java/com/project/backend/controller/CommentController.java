package com.project.backend.controller;

import com.project.backend.dto.CommentDto;
import com.project.backend.service.CommentService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log
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
    public String insertComment(@ModelAttribute CommentDto commentDto) {
        log.info("controller 진입");
        int posts = commentService.insertComment(commentDto);
        if (posts == 1) {
            String html = "<script type=\"text/javascript\">\n" +
                    "\t\talert(\"게시글 추가 되었습니다. \");\n" +
                    "\t\tlocation.href = \"/boardDetail.html?post=" + commentDto.getPostNo() + "\";\n" +
                    "</script>";
            return html;
        }
//DB에 값 저장안된경우
        else {
            String html = "<script type=\"text/javascript\">" +
                    "alert(\"게시글 등록 실패 하였습니다. \");" +
                    "location.href = \"/board.html\";" +
                    "</script>";
            return html;
        }
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
