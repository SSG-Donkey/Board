package com.project.backend.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostDto {
    private String postNo;
    private String postTitle;
    private String userNickname;
    private String postContent;
    private String postFile;
    private String userNo;
    private String postViews;
    private String postCategory;
    private String categoryName;
    private String regionNo;
    private String regionName;
    private String postDate;
    private String postStatus;
    private Integer point;

}
