package com.velog.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velog.ApiDocumentUtils;
import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.enumData.ProviderType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.velog.ApiDocumentUtils.getDocumentRequest;
import static com.velog.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureRestDocs(uriHost = "52.79.226.150")
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 멤버회원가입() throws Exception {
        // given
        String email = "tnswh2023@naver.com";
        String password = "tnswh2023@";
        String name = "tnswh";
        CreateMemberRequest request = new CreateMemberRequest(email, password, name, null, ProviderType.LOCAL);

        // when
        final ResultActions resultActions = mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member/signup",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("email"),
                                fieldWithPath("password").description("password"),
                                fieldWithPath("name").description("name"),
                                fieldWithPath("memberImage").description("memberImage"),
                                fieldWithPath("provider").description("provider")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data")
                        )
                        ));

        // then
        resultActions.andExpect(status().isOk());
    }

}
//