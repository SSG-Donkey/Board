package com.project.backend.controller;

import com.project.backend.dto.PageResultDto;
import com.project.backend.dto.PostDto;
import com.project.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api_post")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping("/getAllPosts")
    public ResponseEntity<PageResultDto<PostDto>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResultDto<PostDto> pagedResult = postService.getPosts(page, size);
        return ResponseEntity.ok(pagedResult);
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
//    @ResponseBody
    public ResponseEntity<PageResultDto<PostDto>> getCategoryPosts(@PathVariable String categoryNo, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResultDto<PostDto>  posts;

        if (categoryNo.equals("null"))
            posts = postService.findPostByCategory(null,page, size);
        else
            posts = postService.findPostByCategory(categoryNo,page, size);

        return ResponseEntity.ok(posts);
    }
}