package com.project.backend.controller;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.backend.dto.*;
import com.project.backend.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.backend.service.CommentService;
import com.project.backend.service.PostService;

import lombok.extern.java.Log;


@RestController
@RequestMapping("/api_post")
@Log
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PointService pointService;

    @GetMapping("/getAllPosts")
    public ResponseEntity<Map<String, Object>> getAllPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> result = postService.findPostsWithPagination(page, pageSize);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getRecentPosts")
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

        log.info("page : " + page);
        log.info("size : " + size);

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
         System.out.print("write1 요청옴 \n");
        // System.out.println("File Name: " + post_file.getOriginalFilename());
        //  System.out.println("File Size: " + post_file.getSize() + " bytes");
        //  System.out.println("Content Type: " + post_file.getContentType());
        //  System.out.print(post_file);
        System.out.printf("제목: %s\n",post_title);
        System.out.printf("내용 %s\n",post_content);
        System.out.printf("유저넘버:%d\n",user_no);

        System.out.printf("포스트뷰:%d\n",post_views);
        System.out.printf("스태이터스:%d\n",post_status);
        System.out.printf("카테고리:%d\n",post_category);
        System.out.printf("포인트:%d\n",point);
        System.out.printf("리전:%d\n",region_no);


        //    System.out.printf("이미지명:%s\n",post_file);  //s3에 저장하는건 모르겠음
        int posts = postService.insertPost(post_title, post_content, post_file, user_no, post_views, post_category, region_no, post_status, point);
        System.out.println("글 등록완료");


        //DB에 값 저장된경우
        if (posts == 1) {
            String html = "<script type=\"text/javascript\">" +
                    "alert(\"게시글 추가 되었습니다. \");" +
                    "location.href = \"/board.html\";" +
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

    @PostMapping("/write2")
    public String insertPosts2(@RequestParam("post_title") String post_title,
                              @RequestParam("post_content") String post_content,
                              @RequestParam("post_file") MultipartFile post_file,
                              @RequestParam("user_no") Integer user_no,
                              @RequestParam("post_views") Integer post_views,
                              @RequestParam("post_category") Integer post_category,
                              @RequestParam("region_no") Integer region_no,
                              @RequestParam("post_status") Integer post_status,
                              @RequestParam("point") Integer point,
                              @RequestParam("user_nickname")String user_nickname) throws Exception {

        //현재 예외처리없게 하드코딩 함 write 부분 user_no =1로 고정해놨음
        System.out.print("write 2요청옴 \n");
        // System.out.println("File Name: " + post_file.getOriginalFilename());
        //  System.out.println("File Size: " + post_file.getSize() + " bytes");
        //  System.out.println("Content Type: " + post_file.getContentType());
        //  System.out.print(post_file);


        //    System.out.printf("이미지명:%s\n",post_file);  //s3에 저장하는건 모르겠음
        int posts = postService.insertPost2(post_title, post_content, post_file, user_no, post_views, post_category, region_no, post_status, point,user_nickname);
        System.out.println("글 등록완료");

        //DB에 값 저장된경우
        if (posts == 1) {
            String html = "<script type=\"text/javascript\">" +
                    "alert(\"게시글 추가 되었습니다. \");" +
                    "location.href = \"/board.html\";" +
                    "</script>";
            return html;
        }
        else{
            String html = "<script type=\"text/javascript\">" +
                    "alert(\"로그인 해주시기 바랍니다. \");" +
                    "location.href = \"/board.html\";" +
                    "</script>";
            return html;

        }
        //DB에 값 저장안된경우

    }
    //게시글 삭제



    @PostMapping("/delete")
    public Map<String, String>  deletePosts(@RequestParam("postNo") String postNo,
                              @RequestParam("userNo") String userNo) throws Exception {

        int post_no=Integer.parseInt(postNo);
        int user_no=Integer.parseInt(userNo);

        System.out.print("delete 요청옴! \n");
        System.out.printf("post_no:%d\n",post_no);
        System.out.printf("user_no:%d\n",user_no);


        int posts = postService.deletePost(post_no,user_no);
        System.out.printf("posts :%d\n",posts);
        Map<String, String> response = new HashMap<>();
        //DB에 값 저장된경우
        if (posts == 1) {
            response.put("message", "게시글 삭제 완료 되었습니다.");
            response.put("redirectUrl", "/board.html");
        } else {
            response.put("message", "게시글 삭제 실패 하였습니다.");
            response.put("redirectUrl",  "/boardDetail.html?postNo=" + post_no);
        }
        //DB에 값 저장안된경우
        return response;

    }

    //게시글 수정 유효성검증
    @PostMapping("/update")
    public Map<String, String> updatePost( @RequestParam("postNo") String postNo,
                                @RequestParam("userNo") String userNo) throws Exception {
        int post_no=Integer.parseInt(postNo);
        int user_no=Integer.parseInt(userNo);
        //현재 예외처리없게 하드코딩 함 write 부분 user_no =1로 고정해놨음
        System.out.print("update옴 \n");
        Map<String, String> response = new HashMap<>();
        int validate =postService.validatePost(post_no,user_no); //작성자 게시글 유효성 검증
        String redirectUrl="https://www.dangnagwi.store/editpost.html?postNo="+postNo;
        if(validate >=1){
            response.put("message", "게시글 수정 페이지로 넘어갑니다.");
            response.put("redirectUrl", redirectUrl);
            return response;
        }
        else{
            response.put("message", "게시글 수정 권한이 없습니다.");
            response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);
            return response;
        }

        //DB에 값 저장안된경우

    }

    @PostMapping("/finish")
    public Map<String, String> finishPost( @RequestParam("postNo") String postNo,
                                           @RequestParam("userNo") String userNo) throws Exception {
        int post_no=Integer.parseInt(postNo);
        int user_no=Integer.parseInt(userNo);
        //현재 예외처리없게 하드코딩 함 write 부분 user_no =1로 고정해놨음
        System.out.print("finish옴 \n");
        Map<String, String> response = new HashMap<>();
        int validate =postService.validatePost(post_no,user_no); //작성자 게시글 유효성 검증
        int finish =postService.finishPost(post_no); //유효성끝나면 업데이트
        String redirectUrl="https://www.dangnagwi.store/boardDetail.html?postNo="+postNo;
        if(validate >=1){
            response.put("message", "나눔 완료 되었습니다.");
            response.put("redirectUrl", redirectUrl);
            return response;
        }
        else{
            response.put("message", "나눔 완료 실패하였습니다.");
            response.put("redirectUrl", "/boardDetail.html?postNo=" + postNo);
            return response;
        }

        //DB에 값 저장안된경우

    }
    @PostMapping("/edit")
    public String editPost(@RequestParam("postNo") String postNo,
                               @RequestParam("post_title") String post_title,
                               @RequestParam("post_content") String post_content,
                               @RequestParam("post_category") Integer post_category,
                               @RequestParam("region_no") Integer region_no,
                               @RequestParam("point") Integer point) throws Exception {

        //현재 예외처리없게 하드코딩 함 write 부분 user_no =1로 고정해놨음
        System.out.print("edit \n");
        int post_no=Integer.parseInt(postNo);


        int posts = postService.editPost(post_no,post_title,post_content,post_category,region_no,point);
        System.out.println("글 수정완료");



        String html = "<script type=\"text/javascript\">" +
                    "alert(\"게시글 수정 되었습니다. \");" +
                    "location.href = \"/board.html\";" +
                    "</script>";
        return html;



    }


    //게시글 작성 끝// 게시글 상세페이지 시작
    @GetMapping("/post/{postNo}")
    @ResponseBody
        public ResponseEntity<Map<String, Object>> post(@PathVariable String postNo) {
        //log.info("게시물 상세 페이지 ");
        PostDto content = postService.findPostByNo(postNo);
        List<CommentDto> comment = commentService.selectCommentByPostNo(postNo);
        Map<String, Object> responseData = new HashMap<>();

        log.info(content.toString());
        responseData.put("content", content);
        if (comment != null) {
            responseData.put("comment", comment);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    //게시글 상세 페이지 끝
//    @GetMapping("/board")
//    public String showBoard(@RequestParam(value = "category", required = false, defaultValue = "all") String categoryNo, Model model) {
//        // categoryName 값을 사용하여 데이터를 가져오거나 처리하는 로직 구현
//        System.out.println("categoryNo :: " + categoryNo);
//        model.addAttribute("categoryNo", categoryNo);
//        return "board";
//    }
    //검색하기
    @GetMapping("/search")
    public ResponseEntity<PageResultDto<PostDto>> search(@RequestParam String searchTerm, @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        PageResultDto<PostDto>  posts;

        posts = postService.searchPost(searchTerm,page, size);

        return ResponseEntity.ok(posts);
    }

    //포인트 불러오기
    @GetMapping("/point")
    public ResponseEntity<PointDto> showUserPoint(@RequestParam("userNo") String userNo) {
        PointDto res = pointService.getUserPoint(userNo);
        
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}