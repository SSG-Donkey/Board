package com.project.backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3ImageService {
    @Setter(onMethod_ = @Autowired)
    private AmazonS3 amazonS3; // Amazon S3 클라이언트를 주입받음

    @Value("${cloud.aws.s3.bucketName}") // application.properties에서 설정한 S3 버킷 이름을 주입받음
    private String bucketName;

    // 이미지 업로드 메서드
    public String upload(MultipartFile image) throws Exception {
        // 이미지가 비어 있거나 파일 이름이 없는 경우 예외를 던짐
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            // image가 null일 경우 logo image 입력
            String filename = "https://ssg-donkey-bucket.s3.ap-northeast-2.amazonaws.com/%EB%A1%9C%EA%B3%A0%EC%B5%9C%EC%A2%85.png";

            return null;
        }
        // 이미지 업로드 메서드 호출
        return this.uploadImage(image);
    }

    // 이미지 업로드 처리 메서드
    private String uploadImage(MultipartFile image) throws Exception {
        // 이미지 파일 확장자 유효성 검사
        this.validateImageFileExtention(image.getOriginalFilename());
        try {
            // S3에 이미지를 업로드하고 업로드된 이미지의 URL을 반환
            return this.uploadImageToS3(image);
        } catch (IOException e) {
            throw new Exception();
        }
    }

    // 이미지 파일 확장자 유효성 검사 메서드
    private void validateImageFileExtention(String filename) throws Exception {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new Exception();
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase(); // 슬래시(/) 제거 후 소문자로 변경
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extension)) {
            throw new Exception();
        }
    }

    // S3에 이미지를 업로드하는 메서드
    private String uploadImageToS3(MultipartFile image) throws Exception {
        String originalFilename = image.getOriginalFilename(); // 원본 파일명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); // 확장자명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; // 변경된 파일명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); // 이미지를 byte[]로 변환

        ObjectMetadata metadata = new ObjectMetadata(); // 메타데이터 생성
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);

        // S3에 요청할 때 사용할 ByteArrayInputStream 생성
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            // S3에 이미지를 업로드하기 위한 PutObjectRequest 생성
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);

            // 실제로 S3에 이미지 데이터를 넣는 부분
            amazonS3.putObject(putObjectRequest); // 이미지를 S3에 업로드
        } catch (Exception e) {
            throw new Exception();
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString(); // 업로드된 이미지의 URL 반환
    }

    // S3에서 이미지를 삭제하는 메서드
    public void deleteImageFromS3(String imageAddress) throws Exception {
        String key = getKeyFromImageAddress(imageAddress); // 이미지 주소로부터 S3 키 추출
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key)); // S3에서 이미지 삭제
        } catch (Exception e) {
            throw new Exception();
        }
    }

    // 이미지 주소로부터 S3 키를 추출하는 메서드
    private String getKeyFromImageAddress(String imageAddress) throws Exception {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (MalformedURLException e) {
            throw new Exception();
        }
    }

    // S3에서 이미지 다운로드
    public void downloadImageFromS3(String key, String localFilePath) throws IOException {

        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, key));
        try {
            // 로컬 파일로 이미지 저장
            File localFile = new File(localFilePath);
            FileOutputStream fos = new FileOutputStream(localFile);
            fos.write(s3Object.getObjectContent().readAllBytes());
            fos.close();
            log.info("Image downloaded successfully to {}", localFilePath);
        } finally {
            if (s3Object != null) {
                s3Object.close();
            }
        }
    }
}
