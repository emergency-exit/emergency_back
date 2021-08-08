package com.velog.service.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.velog.config.prefix.S3Component;
import com.velog.config.prefix.S3CredentialsComponent;
import com.velog.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final S3Service s3Service;

    public String imageUpload(MultipartFile file) {
        UploadUtils.validateFileType(file.getOriginalFilename());
        String fileName = UploadUtils.createFileNameAndDirectory(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            return s3Service.uploadFile(inputStream, objectMetadata, fileName);
        } catch (IOException e) {
            throw new ValidationException(String.format("%s 파일을 업로드하는데 오류가 발생했습니다.", file.getOriginalFilename()));
        }
    }

}
