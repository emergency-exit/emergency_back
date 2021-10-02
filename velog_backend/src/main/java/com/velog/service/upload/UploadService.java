package com.velog.service.upload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    String imageUpload(MultipartFile file);

}
