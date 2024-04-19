package com.project.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.backend.dto.PageResultDto;
import com.project.backend.dto.PostDto;
import com.project.backend.service.PostService;


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

    //카테고리별 게시글 조회
    //http://localhost:8080/board.html?category=1
    @GetMapping("/category/{categoryNo}")
    public ResponseEntity<PageResultDto<PostDto>> getCategoryPosts(@PathVariable String categoryNo, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResultDto<PostDto>  posts;

        if (categoryNo.equals("null"))
            posts = postService.findPostByCategory(null,page, size);
        else
            posts = postService.findPostByCategory(categoryNo,page, size);

        return ResponseEntity.ok(posts);
    }

    // 게시글작성 시작
    @PostMapping("/write")
    public String insertPosts(@RequestParam("post_title") String post_title,
                                     @RequestParam("post_content") String post_content,
                                     @RequestParam("post_file") MultipartFile post_file,
                                     @RequestParam("user_no") Integer user_no,
                                     @RequestParam("post_views") Integer post_views,
                                     @RequestParam("post_category") Integer post_category,
                                     @RequestParam("region_no") Integer region_no,
                                     @RequestParam("post_status") Integer post_status,
                                     @RequestParam("point") Integer point) throws Exception {

        //현재 예외처리없게 하드코딩 함 write 부분 user_no =1로 고정해놨음
        int posts = postService.insertPost(post_title,post_content,post_file,user_no,post_views,post_category,region_no,post_status,point);


        //DB에 값 저장된경우
        if (posts==1){
            String html = "<script type=\"text/javascript\">\n" +
                    "\t\talert(\"게시글 추가 되었습니다. \");\n" +
                    "\t\tlocation.href = \"/board.html\";\n" +
                    "</script>";
            return html;
        }
        //DB에 값 저장안된경우
        else{
            String html = "<script type=\"text/javascript\">\n" +
                    "\t\talert(\"게시글 등록 실패 하였습니다. \");\n" +
                    "\t\tlocation.href = \"/board.html\";\n" +
                    "</script>";
            return html;
        }
    }
    //게시글 작성 끝

    //검색하기
    @GetMapping("/search")
    public ResponseEntity<PageResultDto<PostDto>> search(@RequestParam String searchTerm, @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        PageResultDto<PostDto>  posts;

        posts = postService.searchPost(searchTerm,page, size);

        return ResponseEntity.ok(posts);
    }
}