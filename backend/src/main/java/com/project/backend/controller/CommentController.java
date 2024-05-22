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
    @Autowired
    private PostService postService;

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

        System.out.printf("comment_no = %s , post_no =%s user_no =%s\n",commentNo,postNo,userNo);

        int comment_no=Integer.parseInt(commentNo);
        CommentDto commentDto = new CommentDto();
        commentDto.setPostNo(postNo);
        commentDto.setUserNo(userNo);
        commentDto.setCommentNo(commentNo);
        int res2 =commentService.validateComment(commentDto);

        Map<String,String> response =new HashMap<>();
        System.out.printf(" res2 결과 :%d\n",res2);
        if(comment_no== res2){
            int res1 =commentService.deleteComment(commentDto);
            response.put("message", "댓글 삭제 완료하였습니다.");
            response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);

        }
        else{
            response.put("message", "댓글 삭제 실패하였습니다!");
            response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);

        }

        return response;
    }

    //나눔 채택
    @PostMapping("select")
    public Map<String,String> selectUser(@RequestParam("postuserNo") String post_userno,
                                         @RequestParam("postNo")String postNo,
                                         @RequestParam("point")String point,
                                         @RequestParam("userNo")String userNo,
                                         @RequestParam("commentNo")String commentNo){
        System.out.printf("point :%s , userno: %s ,commentno:%s\n",point,userNo,commentNo);
        Map<String,String> response =new HashMap<>();
        int is_writer=postService.isWriter(post_userno,postNo);
        int user_no=Integer.parseInt(post_userno);
        System.out.printf("post_userno : %d\n",user_no);
        if(is_writer !=user_no){
            response.put("message", "채택권한이 없습니다.");
            response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);
            return response;
        }
        int post_status=commentService.postChosen(postNo); // 채택여부 알기
        if(post_status ==0){ //채택 안되어있으면

            int res1=commentService.cutPoint(userNo,point);
            System.out.printf("res1=%d\n",res1); //컷됨
            if(res1==1){

                int res2=commentService.chooseComment(commentNo);
                System.out.printf("res2=%d\n",res2);
                if(res2==1){
                    int post_no=Integer.parseInt(postNo);
                    int result=postService.sharePost(post_no);
                    response.put("message", "나눔중 상태로 변경 완료하였습니다.");
                    response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);

                }
                else{
                    response.put("message", "나눔중 상태로 변경 실패하였습니다.");
                    response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);
                }
                return response;
            }

        }

        response.put("message", "채택 실패하였습니다.");
        response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);
        return response;
    }
}
