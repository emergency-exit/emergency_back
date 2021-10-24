package com.velog.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velog.config.jwt.JwtTokenProvider;
import com.velog.domain.board.Board;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.domain.testObject.BoardCreator;
import com.velog.domain.testObject.MemberCreator;
import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.dto.member.request.LoginRequest;
import com.velog.dto.member.request.UpdateMemberRequest;
import com.velog.enumData.ProviderType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

import static com.velog.ApiDocumentUtils.getDocumentRequest;
import static com.velog.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    void 멤버회원가입() throws Exception {
        // given
        String email = "tnswh2023@naver.com";
        String password = "tnswh2023@";
        String name = "tnswh";
        CreateMemberRequest request = new CreateMemberRequest(email, password, name, null, ProviderType.LOCAL);

        // when
        final ResultActions resultActions = mockMvc.perform(
                        post("/signup")
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

    @Test
    void 회원_로그인() throws Exception {
        // given
        Member member = MemberCreator.create("tnswh2023@gmail.com", passwordEncoder.encode("tnswh2023@"));
        memberRepository.save(member);

        String email = "tnswh2023@gmail.com";
        String password = "tnswh2023@";
        LoginRequest request = LoginRequest.testInstance(email, password);

        // when
        final ResultActions resultActions = mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member/login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("email"),
                                fieldWithPath("password").description("password")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 내_정보_불러오기() throws Exception {
        // given
        String email = "tnswh2023@gmail.com";

        Member member = MemberCreator.create(email, passwordEncoder.encode("tnswh2023@"));
        memberRepository.save(member);

        String token = jwtTokenProvider.createToken(email);

        // when
        final ResultActions resultActions = mockMvc.perform(
                        get("/api/v1/myInfo")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member/myInfo",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.email").description("이메일"),
                                fieldWithPath("data.name").description("유저 이름"),
                                fieldWithPath("data.memberImage").description("유저의 프로필 사진"),
                                fieldWithPath("data.velogName").description("유저의 블로그 이름"),
                                fieldWithPath("data.description").description("유저의 블로그 설명")
                        )
                ));
        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 내_정보_수정() throws Exception {
        // given
        String email = "tnswh2023@gmail.com";

        Member member = MemberCreator.create("tnswh2023@gmail.com", passwordEncoder.encode("tnswh2023@"));
        memberRepository.save(member);

        String token = jwtTokenProvider.createToken(email);

        UpdateMemberRequest request = UpdateMemberRequest.testInstance("uName", "velogggg", "블로그 설명입니다.");

        // when
        final ResultActions resultActions = mockMvc.perform(
                        put("/api/v1/myInfo/update")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member/updateMyInfo",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.email").description("이메일"),
                                fieldWithPath("data.name").description("유저 이름"),
                                fieldWithPath("data.memberImage").description("유저의 프로필 사진"),
                                fieldWithPath("data.velogName").description("유저의 블로그 이름"),
                                fieldWithPath("data.description").description("유저의 블로그 설명")
                        )
                ));
        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 내가_쓴_게시글_리스트_가져오기() throws Exception {
        // given
        String email = "tnswh2023@gmail.com";

        Member member = MemberCreator.create(email, passwordEncoder.encode("tnswh2023@"));
        memberRepository.save(member);

        Board board1 = BoardCreator.create("title1", member.getId());
        Board board2 = BoardCreator.create("title2", member.getId());
        Board board3 = BoardCreator.create("title3", member.getId());

        boardRepository.saveAll(Arrays.asList(board1, board2, board3));

        String token = jwtTokenProvider.createToken(email);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("lastBoardId", board3.getId().toString());
        params.add("size", "2");
        params.add("period", "LATEST");

        // when
        final ResultActions resultActions = mockMvc.perform(
                        get("/api/v1/myInfo/board/list")
                                .params(params)
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member/myBoardList",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("lastBoardId").description("마지막 게시글 아이디"),
                                parameterWithName("size").description("마지막 게시글 도이디"),
                                parameterWithName("period").description("게시글 순서 [최신순, 오늘, 이번주, 이번달, 이번년도]")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data[].boardId").description("게시글 아이디"),
                                fieldWithPath("data[].title").description("블로그 제목"),
                                fieldWithPath("data[].content").description("블로그 컨텐츠"),
                                fieldWithPath("data[].likeCount").description("좋아요 개수"),
                                fieldWithPath("data[].boardThumbnailUrl").description("게시글 썸네일 url"),
                                fieldWithPath("data[].memberId").description("게시글 맴버 아이디"),
                                fieldWithPath("data[].name").description("게시글 멤버 이름"),
                                fieldWithPath("data[].memberImage").description("게시글 멤버 이미지"),
                                fieldWithPath("data[].boardId").description("게시글 아이디"),
                                fieldWithPath("data[].title").description("블로그 제목"),
                                fieldWithPath("data[].content").description("블로그 컨텐츠"),
                                fieldWithPath("data[].likeCount").description("좋아요 개수"),
                                fieldWithPath("data[].boardThumbnailUrl").description("게시글 썸네일 url"),
                                fieldWithPath("data[].memberId").description("게시글 맴버 아이디"),
                                fieldWithPath("data[].name").description("게시글 멤버 이름"),
                                fieldWithPath("data[].memberImage").description("게시글 멤버 이미지")
                        )
                ));
        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 내가_쓴_특정_게시글_가져오기() throws Exception {
        // given
        String email = "tnswh2023@gmail.com";

        Member member = MemberCreator.create("tnswh2023@gmail.com", passwordEncoder.encode("tnswh2023@"));
        memberRepository.save(member);

        Board board1 = BoardCreator.create("title1", member.getId());

        boardRepository.save(board1);

        String token = jwtTokenProvider.createToken(email);

        // when
        final ResultActions resultActions = mockMvc.perform(
                        get("/api/v1/myInfo/board/detail/{boardId}", board1.getId())
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("member/updateMyInfo",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("boardId").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data.boardInfoResponse.boardId").description("게시글 아이디"),
                                fieldWithPath("data.boardInfoResponse.seriesId").description("시리즈 아이디"),
                                fieldWithPath("data.boardInfoResponse.title").description("제목"),
                                fieldWithPath("data.boardInfoResponse.content").description("컨텐츠"),
                                fieldWithPath("data.boardInfoResponse.isPrivate").description("공개/비공개"),
                                fieldWithPath("data.boardInfoResponse.likeCount").description("좋아요 개수"),
                                fieldWithPath("data.boardInfoResponse.boardThumbnailUrl").description("게시글 썸네일 개수"),
                                fieldWithPath("data.memberInfoResponse.email").description("이메일"),
                                fieldWithPath("data.memberInfoResponse.name").description("이름"),
                                fieldWithPath("data.memberInfoResponse.memberImage").description("멤버 썸네일 이미지"),
                                fieldWithPath("data.memberInfoResponse.velogName").description("벨로그 이름"),
                                fieldWithPath("data.memberInfoResponse.description").description("블로그 설명"),
                                fieldWithPath("data.hashTagList[]").description("해시태그 리스트")
                        )
                ));
        // then
        resultActions.andExpect(status().isOk());
    }

}