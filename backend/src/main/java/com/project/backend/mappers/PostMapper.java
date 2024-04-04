package com.project.backend.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.project.backend.dto.PostDto;
import java.util.List;

@Mapper
@Repository
public interface PostMapper {
    List<PostDto> find_post_All();
    PostDto findPostByTitle(String getPostTitle);
    PostDto findPostByNo(String getPostNo);
    List<PostDto> findPostByCategory(String getPostCategory);
}


