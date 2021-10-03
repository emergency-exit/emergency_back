package com.velog.controller.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velog.controller.MemberSetUp;
import com.velog.domain.board.Board;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.testObject.BoardCreator;
import com.velog.dto.board.request.BoardRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collections;

import static com.velog.ApiDocumentUtils.getDocumentRequest;
import static com.velog.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs(uriHost = "52.79.226.150")
@AutoConfigureMockMvc
public class BoardControllerTest extends MemberSetUp {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    @AfterEach
    void clean() {
        super.cleanup();
        boardRepository.deleteAll();
    }

    @Test
    void 블로그_시리즈_생성() throws Exception {
        // given
        BoardRequest.CreateSeries request = BoardRequest.CreateSeries.testInstance("자바시리즈");

        // when
        final ResultActions resultActions = mockMvc.perform(
                        post("/api/v1/series")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/series",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("seriesName").description("블로그 시리즈 이름")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.seriesId").description("시리즈 아이디"),
                                fieldWithPath("data.seriesName").description("시리즈 이름")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 블로그_게시글_생성() throws Exception {
        // given
        BoardRequest.CreateBoard request = BoardRequest.CreateBoard.testBuilder()
                .title("title")
                .content("content")
                .boardThumbnailUrl("boardThumbnailUrl")
                .isPrivate(true)
                .seriesId(1L)
                .hashTagList(Collections.emptyList())
                .build();

        // when
        final ResultActions resultActions = mockMvc.perform(
                        post("/api/v1/board")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("title").description("블로그 제목"),
                                fieldWithPath("content").description("블로그 컨텐츠"),
                                fieldWithPath("boardThumbnailUrl").description("게시글 썸네일"),
                                fieldWithPath("isPrivate").description("공개 / 비공개"),
                                fieldWithPath("seriesId").description("시리즈 아이디 null 가능 optional"),
                                fieldWithPath("hashTagList").description("해쉬태그 리스트")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.seriesId").description("시리즈 아이디"),
                                fieldWithPath("data.boardId").description("게시글 아이디"),
                                fieldWithPath("data.title").description("블로그 제목"),
                                fieldWithPath("data.content").description("블로그 컨텐츠"),
                                fieldWithPath("data.isPrivate").description("공개 / 비공개"),
                                fieldWithPath("data.likeCount").description("좋아요 개수"),
                                fieldWithPath("data.boardThumbnailUrl").description("게시글 썸네일 url")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 블로그_게시글_리스트_조회() throws Exception {
        // given
        Board title1 = BoardCreator.create("title1", member.getId());
        Board title2 = BoardCreator.create("title2", member.getId());
        Board title3 = BoardCreator.create("title3", member.getId());
        Board title4 = BoardCreator.create("title4", member.getId());
        Board title5 = BoardCreator.create("title5", member.getId());
        boardRepository.saveAll(Arrays.asList(title1, title2, title3, title4, title5));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("lastBoardId", title3.getId().toString());
        params.add("size", "1");
        params.add("period", "LATEST");

        // when
        final ResultActions resultActions = mockMvc.perform(
                        get("/board/list")
                                .params(params)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("lastBoardId").description("게시글 마지막 id"),
                                parameterWithName("size").description("불러올 사이즈"),
                                parameterWithName("period").description("조회순, 최신순 같은 순서")
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
                                fieldWithPath("data[].memberImage").description("게시글 멤버 이미지")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 블로그_게시글_상세보기() throws Exception {
        // given
        Board board = BoardCreator.create("title1", member.getId());
        boardRepository.save(board);

        // when
        final ResultActions resultActions = mockMvc.perform(
                        get("/board/detail/{boardId}", board.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/detail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("boardId").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.boardInfoResponse.boardId").description("게시글 아이디"),
                                fieldWithPath("data.boardInfoResponse.seriesId").description("시리즈 아이디"),
                                fieldWithPath("data.boardInfoResponse.title").description("게시글 제목"),
                                fieldWithPath("data.boardInfoResponse.content").description("게시글 컨텐츠"),
                                fieldWithPath("data.boardInfoResponse.isPrivate").description("공개 / 비공개"),
                                fieldWithPath("data.boardInfoResponse.likeCount").description("좋아요 개수"),
                                fieldWithPath("data.boardInfoResponse.boardThumbnailUrl").description("게시글 썸네일"),
                                fieldWithPath("data.memberInfoResponse.email").description("이메일"),
                                fieldWithPath("data.memberInfoResponse.name").description("이름"),
                                fieldWithPath("data.memberInfoResponse.memberImage").description("이미지"),
                                fieldWithPath("data.memberInfoResponse.velogName").description("벨로그 이름"),
                                fieldWithPath("data.memberInfoResponse.description").description("블로그 설명"),
                                fieldWithPath("data.hashTagList").description("해쉬태그 리스트")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 블로그_게시글_좋아요() throws Exception {
        // given
        Board board = BoardCreator.create("title1", member.getId());
        boardRepository.save(board);

        BoardRequest.GetBoardRequest request = BoardRequest.GetBoardRequest.testInstance(board.getId());

        // when
        final ResultActions resultActions = mockMvc.perform(
                        post("/api/v1/board/like")
                                .header("Authorization", token)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/like",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("boardId").description("좋아요 할 게시글 아이디")
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
    void 블로그_게시글_좋아요취소() throws Exception {
        // given
        Board board = BoardCreator.create("title1", member.getId());
        board.boardAddLike(member.getId());
        boardRepository.save(board);

        BoardRequest.GetBoardRequest request = BoardRequest.GetBoardRequest.testInstance(board.getId());

        // when
        final ResultActions resultActions = mockMvc.perform(
                        post("/api/v1/board/unlike")
                                .header("Authorization", token)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/like",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("boardId").description("좋아요 할 게시글 아이디")
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
