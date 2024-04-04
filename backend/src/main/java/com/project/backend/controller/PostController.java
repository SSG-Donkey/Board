package com.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.backend.dto.PostDto;
import com.project.backend.service.PostService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RestController
@RequestMapping("/api_post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/namjyung")
    public String hello1() {
        return "hello namjyung!";
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
    @GetMapping("/getpostcategory")
    public ResponseEntity<List<PostDto>> findPostByCategory(@RequestParam String getPostCategory) {
        return new ResponseEntity<>(postService.findPostByCategory(getPostCategory), HttpStatus.OK);
    }
}