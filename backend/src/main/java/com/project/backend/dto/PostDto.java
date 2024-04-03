package com.project.backend.dto;

public class PostDto {

    private String postNo;
    private String postTitle;
    private String postContent;
    private String postFile;
    private String userNo;
    private String postViews;
    private String postCategory;
    private String regionNo;
    private String postDate;
    private String postStatus;

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostFile() {
        return postFile;
    }

    public void setPostFile(String postFile) {
        this.postFile = postFile;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPostViews() {
        return postViews;
    }

    public void setPostViews(String postViews) {
        this.postViews = postViews;
    }

    public String getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(String postCategory) {
        this.postCategory = postCategory;
    }

    public String getRegionNo() {
        return regionNo;
    }

    public void setRegionNo(String regionNo) {
        this.regionNo = regionNo;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }
}
