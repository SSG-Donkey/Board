package com.project.backend.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.project.backend.dto.PostDto;
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
    List<PostDto> findPostByCategory(@Param("categoryNo") String categoryNo, @Param("offset") int offset, @Param("limit") int limit);
    List<PostDto> findPostBySearch(@Param("searchTerm") String searchTerm, @Param("offset") int offset, @Param("limit") int limit);


    List<PostDto> getPosts(@Param("offset") int offset, @Param("limit") int limit);
    long getPostCount();
    long getPostCountByCategory(@Param("categoryNo") String categoryNo);
    long getPostCountBySearch(@Param("searchTerm") String searchTerm);
}


