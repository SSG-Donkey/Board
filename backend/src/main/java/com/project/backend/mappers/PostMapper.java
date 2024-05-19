package com.project.backend.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.project.backend.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Mapper
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
    int insertPost(String post_title , String post_content, String post_file, Integer user_no, Integer post_views ,Integer post_category,Integer region_no,Integer post_status,Integer point);
    int insertPost2(String post_title , String post_content, String post_file, Integer user_no, Integer post_views ,Integer post_category,Integer region_no,Integer post_status,Integer point,String user_nickname);
    int validatePost(Integer post_no,Integer user_no);
    int deletePost(Integer post_no,Integer user_no);
    int editPost(Integer post_no,String post_title, String post_content,Integer user_no, Integer post_category, Integer region_no,  Integer point);

}


