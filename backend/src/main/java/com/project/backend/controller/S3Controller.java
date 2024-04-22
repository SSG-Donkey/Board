package com.project.backend.controller;

import com.project.backend.service.S3ImageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class S3Controller {
    @Setter(onMethod_ = @Autowired)
    private S3ImageService s3ImageService;

    @PostMapping("/s3/upload")
    public ResponseEntity<?> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        String profileImage = s3ImageService.upload(image);
        return ResponseEntity.ok("success : " + profileImage);
    }

    @GetMapping("/s3/delete")
    public ResponseEntity<?> s3Delete(@RequestParam String addr) throws Exception {
        s3ImageService.deleteImageFromS3(addr);
        return ResponseEntity.ok("Delete completed");
    }

    @GetMapping("/s3/download")
    public ResponseEntity<?> s3Download(@RequestParam String addr) throws Exception {
        // 사용자의 다운로드 폴더 경로 가져오기
        String defaultDownloadDir = System.getProperty("user.home") + File.separator + "Downloads\\" + addr;

        System.out.println("defaultDownloadDir ::: " + defaultDownloadDir); //C:\Users\TECH3-12\Downloads
        s3ImageService.downloadImageFromS3(addr, defaultDownloadDir );
        return ResponseEntity.ok("Download completed");
    }

}
