package com.project.backend.controller;

import com.project.backend.dto.CommentDto;
import com.project.backend.dto.PostDto;
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


@Log
@RestController
@RequestMapping("/api_post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/getAllPosts")
    public ResponseEntity<Map<String, Object>> getAllPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> result = postService.findPostsWithPagination(page, pageSize);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getRecentPost")
    public ResponseEntity<List<PostDto>> getRecentPosts() {
        List<PostDto> posts = postService.getRecentPost();
        

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/getposttitle")
    public ResponseEntity<PostDto> findPostByTitle(@RequestParam String getPostTitle) {
        return new ResponseEntity<>(postService.findPostByTitle(getPostTitle), HttpStatus.OK);
    }

    @GetMapping("/getpostno")
    public ResponseEntity<PostDto> findPostByNo(@RequestParam String getPostNo) {
        return new ResponseEntity<>(postService.findPostByNo(getPostNo), HttpStatus.OK);
    }

    //3. 카테고리별 게시글 조회
    //http://localhost:8080/board.html?category=1
    @GetMapping("/category/{categoryNo}")
    @ResponseBody
    public List<PostDto> getCategoryPosts(@PathVariable String categoryNo) {
        log.info("category 선택 ");
        List<PostDto> posts;

        if(categoryNo.equals("null"))
            posts=postService.findPostByCategory(null);
        else
            posts=postService.findPostByCategory(categoryNo);

        return posts;
    }

    // 게시글 상세 페이지
    @GetMapping("/post/{postNo}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> post(@PathVariable String postNo) {
        log.info("게시물 상세 페이지 ");
        PostDto content = postService.findPostByNo(postNo);
        List<CommentDto> comment = commentService.selectCommentByPostNo(postNo);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("content", content);
        if (comment != null) {
            responseData.put("comment", comment);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

//    @GetMapping("/board")
//    public String showBoard(@RequestParam(value = "category", required = false, defaultValue = "all") String categoryNo, Model model) {
//        // categoryName 값을 사용하여 데이터를 가져오거나 처리하는 로직 구현
//        System.out.println("categoryNo :: " + categoryNo);
//        model.addAttribute("categoryNo", categoryNo);
//        return "board";
//    }
}