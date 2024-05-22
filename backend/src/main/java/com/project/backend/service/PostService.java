package com.project.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.backend.dto.PageResultDto;
import io.swagger.v3.oas.annotations.links.Link;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.backend.dto.PostDto;
import com.project.backend.mappers.PostMapper;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log
public class PostService {
    @Autowired
    PostMapper postMapper;

    @Autowired
    S3ImageService s3ImageService;


    public int insertPost(String post_title,String post_content,MultipartFile post_file,Integer user_no,Integer post_views,Integer post_category,Integer region_no,Integer post_status,Integer point) throws Exception {
        System.out.println("디버그 시작");
        System.out.println(post_category);
        String post_file1;
        post_file1 = s3ImageService.upload(post_file);
//        if (post_file1 == null) {
//            post_file1 = "https://ssg-donkey-bucket.s3.ap-northeast-2.amazonaws.com/%EB%A1%9C%EA%B3%A0%EC%B5%9C%EC%A2%85.png";
//        }
        System.out.println("image 경로: " + post_file1);

        int res = postMapper.insertPost(post_title,post_content,post_file1,user_no,post_views,post_category,region_no,post_status,point);
        // addBasicImage(res);

        return res;
    }
    public int insertPost2(String post_title,String post_content,MultipartFile post_file,Integer user_no,Integer post_views,Integer post_category,Integer region_no,Integer post_status,Integer point,String user_nickname) throws Exception {
        System.out.println("디버그 시작");
        System.out.println(post_category);
        String post_file1;
        post_file1 = s3ImageService.upload(post_file);
//        if (post_file1 == null) {
//            post_file1 = "https://ssg-donkey-bucket.s3.ap-northeast-2.amazonaws.com/%EB%A1%9C%EA%B3%A0%EC%B5%9C%EC%A2%85.png";
//        }
        System.out.println("image 경로: " + post_file1);

        int res = postMapper.insertPost2(post_title,post_content,post_file1,user_no,post_views,post_category,region_no,post_status,point,user_nickname);
        // addBasicImage(res);

        return res;
    }
    //게시글 삭제 서비스
    public int deletePost(Integer post_no,Integer user_no) throws Exception {
        System.out.println("삭제디버그 시작");

         int res1 = postMapper.validatePost(post_no,user_no); //작성자 , 게시글 유효성 검증
         System.out.printf("유효성 검증 = %d\n",res1);
         if(res1>=1){
             System.out.println("삭제 시작");
          int res2 = postMapper.deletePost(post_no,user_no);  // 삭제
             System.out.println("삭제 완료");
             return res2;
         }

          else{

           return 0;
          }


    }
    //게시글 검증  (작성자가 해당게시글 맞는지)
    public int validatePost(Integer post_no,Integer user_no) throws Exception {
        System.out.println("유효디버그 시작");

        int res1 = postMapper.validatePost(post_no,user_no); //작성자 , 게시글 유효성 검증

        return res1;


    }

    //게시글 수정
    public int editPost(Integer post_no, String post_title, String post_content, Integer post_category, Integer region_no, Integer point, MultipartFile postFile) throws Exception {
        log.info("----게시글수정 디버깅 시작----");

        String new_post_file = null;

        if(null != postFile)
            new_post_file = s3ImageService.upload(postFile);

        int res = postMapper.editPost(post_no, post_title, post_content, post_category, region_no, point, new_post_file);

        return res;
    }
    // 게시글 나눔완료

    public int finishPost(int post_no)  {
        log.info("finish디버그 시작");

        int res = postMapper.finishPost(post_no);
        // addBasicImage(res);

        return res;
    }
    //게시글 나눔중으로 되돌리기 변경
    public int sharePost(int post_no)  {
        log.info("share디버그 시작");
        int find_no = postMapper.findShareUser(post_no);  //채택유저 찾기
        System.out.printf("user_no =%d \n",find_no);

        int find_point=postMapper.findPoint(post_no);     //포인트 찾기
        System.out.printf("point =%d\n ",post_no);
        int give_point=postMapper.givePoint(find_no,find_point); //채택유저 포인트 업데
        int res = postMapper.sharePost(post_no);
        // addBasicImage(res);

        return res;
    }

    public Map<String, Object> findPostsWithPagination(int page, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        int offset = (page) * pageSize;
        params.put("offset", offset);
        params.put("limit", pageSize);

        List<PostDto> posts = postMapper.findAllPosts(params);
        int totalCount = postMapper.getTotalPostCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("currentPage", page);
        result.put("totalPages", totalPages);
        result.put("totalCount", totalCount);

        return result;
    }

    //최근 매물 조회
    public List<PostDto> getRecentPost() {
        List<PostDto> res = postMapper.getRecentPost();

        addBasicImage(res);
        return res;
    }

    public PostDto findPostByTitle(String getPostTitle) {
        System.out.println("디버그 시작");
        System.out.println(getPostTitle);
        PostDto postDto = postMapper.findPostByTitle(getPostTitle);
        System.out.println("여기!!");
        //없을때 예외처리 or 조건문 해줘야함
        return postDto;
    }

    public PostDto findPostByNo(String getPostNo) {
        System.out.println("findPostByNo 진입");
        System.out.println(getPostNo);
        PostDto postDto = postMapper.findPostByNo(getPostNo);

        return postDto;
    }

    //카테고리별 조회
    public PageResultDto<PostDto> findPostByCategory(String categoryNo, int page, int size) {
        int offset = (page) * size;
        List<PostDto> posts = postMapper.findPostByCategory(categoryNo,offset, size);
        addBasicImage(posts);
        long totalCount = postMapper.getPostCountByCategory(categoryNo);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return new PageResultDto<>(posts, page, size, totalPages, totalCount);
    }

    //검색하기
    public PageResultDto<PostDto> searchPost(String searchTerm, int page, int size) {
        int offset = (page) * size;
        List<PostDto> posts = postMapper.findPostBySearch(searchTerm,offset, size);
        addBasicImage(posts);
        long totalCount = postMapper.getPostCountBySearch(searchTerm);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return new PageResultDto<>(posts, page, size, totalPages, totalCount);
    }

    //
    public int isWriter(String user_no,String post_no){

        int res = postMapper.isWriter(user_no,post_no);
        return res;
    }

    //이미지가 없으면 게시글의 기본 이미지를 당나귀 로고로 수정
    public void addBasicImage(List<PostDto> res)
    {
        for (PostDto post : res) {
            if( null == post.getPostFile())
            {
                post.setPostFile("https://ssg-donkey-bucket.s3.ap-northeast-2.amazonaws.com/%EB%A1%9C%EA%B3%A0%EC%B5%9C%EC%A2%85.png");
            }
        }
    }

}