package com.project.backend.controller;

import com.project.backend.dto.PostDto;
import com.project.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api_post")
public class PostController {

    @Autowired
    private PostService postService;

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
        List<PostDto> posts;

        if(categoryNo.equals("null"))
            posts=postService.findPostByCategory(null);
        else
            posts=postService.findPostByCategory(categoryNo);

        return posts;
    }
    @PostMapping("/write")
    public String insertPosts(@RequestParam("post_title") String post_title,
                                     @RequestParam("post_content") String post_content,
                                     @RequestParam("post_file") String post_file,
                                     @RequestParam("user_no") Integer user_no,
                                     @RequestParam("post_views") Integer post_views,
                                     @RequestParam("post_category") Integer post_category,
                                     @RequestParam("region_no") Integer region_no,
                                     @RequestParam("post_status") Integer post_status) {
        //현재 예외처리없게 하드코딩 함 write 부분 user_no =1로 고정해놨음
        System.out.print("요청옴 \n");
        System.out.printf("지역: %s , 카테고리종류:%s\n",region_no,post_category);
        System.out.printf("제목 :%s, 내용:%s\n",post_title,post_content);
        System.out.printf("이미지명:%s\n",post_file);  //s3에 저장하는건 모르겠음

        int posts = postService.insertPost(post_title,post_content,post_file,user_no,post_views,post_category,region_no,post_status);
        //DB에 값 저장된경우
        if (posts==1){
            String html = "\n" +
                    "<html><h1>게시글 작성 완료</h1><br>\n" +
                    "<button onclick=\"location.href='/board.html'\">게시글 페이지로 이동</button ></html>";
            return html;
        }
        //DB에 값 저장안된경우
        else{
            String html = "\n" +
                    "<html><h1>게시글 작성 실패</h1><br>\n" +
                    "<button onclick=\"location.href='/board.html'\">게시글 페이지로 이동</button ></html>";
            return html;
        }
    }

//    @GetMapping("/board")
//    public String showBoard(@RequestParam(value = "category", required = false, defaultValue = "all") String categoryNo, Model model) {
//        // categoryName 값을 사용하여 데이터를 가져오거나 처리하는 로직 구현
//        System.out.println("categoryNo :: " + categoryNo);
//        model.addAttribute("categoryNo", categoryNo);
//        return "board";
//    }
}