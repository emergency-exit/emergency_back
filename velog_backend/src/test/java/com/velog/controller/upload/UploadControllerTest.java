package com.velog.controller.upload;

import com.velog.dto.board.request.BoardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.velog.ApiDocumentUtils.getDocumentRequest;
import static com.velog.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs(uriHost = "52.79.226.150")
@AutoConfigureMockMvc
public class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void 이미지_업로드() throws Exception {
//        // given
//        MockMultipartFile image = new MockMultipartFile("image", "image.png", "image/png",
//                "<<png data>>".getBytes());
//        MockMultipartFile metadata = new MockMultipartFile("metadata", "",
//                "application/json", "{ \"version\": \"1.0\"}".getBytes());
//
//        // when
//        final ResultActions resultActions = mockMvc.perform(multipart("/image/upload")
//                        .file(image)
//                        .file(metadata)
//                        .contentType("multipart/form-data")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andDo(document("image/upload",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        requestParts(
//                                partWithName("file").description("The file to upload")
//                        ),
//                        responseFields(
//                                fieldWithPath("code").description("success"),
//                                fieldWithPath("data").description("data")
//                        )
//                ));
//
//        // then
//        resultActions.andExpect(status().isOk());
//    }

}
