package com.project.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.backend.dto.PostDto;
import com.project.backend.mappers.PostMapper;

@Service
public class PostService {
    @Autowired
    PostMapper postMapper;

    public List<PostDto> find_post_All() {
        List<PostDto> res = postMapper.find_post_All();

        //이미지가 없으면 게시글의 기본 이미지를 당나귀 로고로 수정
        for (PostDto post : res) {
            if( null == post.getPostFile())
            {
                post.setPostFile("https://ssg-donkey-bucket.s3.ap-northeast-2.amazonaws.com/%EB%A1%9C%EA%B3%A0%EC%B5%9C%EC%A2%85.png");
            }   
        }
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
    public List<PostDto> findPostByCategory(String getPostCategory) {
        System.out.println("디버그 시작");
        System.out.println(getPostCategory);
        //List<PostDto> res = postMapper.find_post_All();
        List<PostDto> postDto = postMapper.findPostByCategory(getPostCategory);
        return postDto;
    }

}