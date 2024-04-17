package com.project.backend.controller;

import com.project.backend.dto.PostDto;
import com.project.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api_post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/getAllPost")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.find_post_All();
        

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
        // categoryName에 따라 DB에서 해당 카테고리의 데이터를 가져옵니다.
        System.out.println("getCategoryPosts 접근");
        List<PostDto> posts;

        if(categoryNo.equals("0"))
            posts=postService.findPostByCategory(null);
        else
            posts=postService.findPostByCategory(categoryNo);

        return posts;
    }

//    @GetMapping("/board")
//    public String showBoard(@RequestParam(value = "category", required = false, defaultValue = "all") String categoryNo, Model model) {
//        // categoryName 값을 사용하여 데이터를 가져오거나 처리하는 로직 구현
//        System.out.println("categoryNo :: " + categoryNo);
//        model.addAttribute("categoryNo", categoryNo);
//        return "board";
//    }
}