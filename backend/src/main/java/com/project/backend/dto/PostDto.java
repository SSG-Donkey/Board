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
    private String postContent;
    private String postFile;
    private String userNo;
    private int point;
    private String postViews;
    private String postCategory;
    private String postCategoryName;
    private String regionNo;
    private String regionName;
    private String postDate;
    private String postStatus;
    private boolean showAlert; // Indicates whether to show an alert on the client-side
    private String alertMessage; // Message to display in the alert

    public boolean setShowAlert(boolean showAlert) {
        this.showAlert=showAlert;
        return showAlert;
    }
    public String alertMessage(String msg) {
        this.alertMessage=msg;
        return alertMessage;
    }

    // Getters and setters for the above properties
}
