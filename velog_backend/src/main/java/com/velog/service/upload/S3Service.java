package com.velog.service.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.velog.config.prefix.S3Component;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;
    private final S3Component s3Component;

    public String uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(s3Component.getBucket(), fileName, inputStream, objectMetadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getResourceUrl(s3Component.getBucket(), fileName);
    }

}
