package com.project.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.backend.dto.PageResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.backend.dto.PostDto;
import com.project.backend.mappers.PostMapper;

@Service
public class PostService {
    @Autowired
    PostMapper postMapper;


    //page : 0 size : 10
    public PageResultDto<PostDto> getPosts(int page, int size) {
        int offset = (page) * size;
        List<PostDto> posts = postMapper.getPosts(offset, size);
        long totalCount = postMapper.getPostCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return new PageResultDto<>(posts, page, size, totalPages, totalCount);
    }

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

    public PageResultDto<PostDto> findPostByCategory(String categoryNo, int page, int size) {
        int offset = (page) * size;
        List<PostDto> posts = postMapper.findPostByCategory(categoryNo,offset, size);
        long totalCount = postMapper.getPostCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return new PageResultDto<>(posts, page, size, totalPages, totalCount);
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