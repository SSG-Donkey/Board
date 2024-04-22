package com.project.backend.service;

import com.project.backend.dto.PageResultDto;
import com.project.backend.dto.PostDto;
import com.project.backend.mappers.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostMapper postMapper;

    @Autowired
    S3ImageService s3ImageService;

    public int insertPost(String post_title,String post_content,MultipartFile post_file,Integer user_no,Integer post_views,Integer post_category,Integer region_no,Integer post_status,Integer point) throws Exception {
        String post_file1=s3ImageService.upload(post_file);

        int res = postMapper.insertPost(post_title,post_content,post_file1,user_no,post_views,post_category,region_no,post_status,point);
//        addBasicImage(res);

        return res;
    }

    //page : 0 size : 10
    public PageResultDto<PostDto> getPosts(int page, int size) {
        int offset = (page) * size;
        List<PostDto> posts = postMapper.getPosts(offset, size);
//        addBasicImage(posts);
        long totalCount = postMapper.getPostCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return new PageResultDto<>(posts, page, size, totalPages, totalCount);
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
        System.out.println("디버그 시작");
        System.out.println(getPostNo);
        PostDto postDto = postMapper.findPostByNo(getPostNo);

        return postDto;
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