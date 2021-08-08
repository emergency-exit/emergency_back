package com.velog.controller.upload;

import com.velog.controller.ApiResponse;
import com.velog.service.upload.UploadService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @ApiOperation(value = "업로드", notes = "이미지 파일 업로드")
    @PostMapping("/image/upload")
    public ApiResponse<String> imageUpload(@RequestPart MultipartFile file) {
        return ApiResponse.success(uploadService.imageUpload(file));
    }

}
