package com.project.backend.mappers;

import com.project.backend.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PostMapper {

    List<PostDto> findAllPosts(Map<String, Object> params);
    int getTotalPostCount();
    List<PostDto> getRecentPost();
    PostDto findPostByTitle(String getPostTitle);
    PostDto findPostByNo(String getPostNo);
    List<PostDto> findPostByCategory(String categoryNo);
}


