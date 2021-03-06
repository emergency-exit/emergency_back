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
    void ?????????_?????????_??????() throws Exception {
        // given
        BoardRequest.CreateSeries request = BoardRequest.CreateSeries.testInstance("???????????????");

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
                                fieldWithPath("seriesName").description("????????? ????????? ??????")
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
    void ?????????_?????????_?????????_????????????() throws Exception {
        // when
        final ResultActions resultActions = mockMvc.perform(
                        get("/api/v1/series")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/seriesList",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data[].seriesId").description("????????? ?????????"),
                                fieldWithPath("data[].seriesName").description("????????? ??????")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void ?????????_?????????_??????() throws Exception {
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
                                fieldWithPath("title").description("????????? ??????"),
                                fieldWithPath("content").description("????????? ?????????"),
                                fieldWithPath("boardThumbnailUrl").description("????????? ?????????"),
                                fieldWithPath("isPrivate").description("?????? / ?????????"),
                                fieldWithPath("seriesId").description("????????? ????????? null ?????? optional"),
                                fieldWithPath("hashTagList").description("???????????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.seriesId").description("????????? ?????????"),
                                fieldWithPath("data.boardId").description("????????? ?????????"),
                                fieldWithPath("data.title").description("????????? ??????"),
                                fieldWithPath("data.content").description("????????? ?????????"),
                                fieldWithPath("data.isPrivate").description("?????? / ?????????"),
                                fieldWithPath("data.likeCount").description("????????? ??????"),
                                fieldWithPath("data.boardThumbnailUrl").description("????????? ????????? url")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void ?????????_?????????_?????????_??????() throws Exception {
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
                                parameterWithName("lastBoardId").description("????????? ????????? id"),
                                parameterWithName("size").description("????????? ?????????"),
                                parameterWithName("period").description("?????????, ????????? ?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data[].boardId").description("????????? ?????????"),
                                fieldWithPath("data[].title").description("????????? ??????"),
                                fieldWithPath("data[].content").description("????????? ?????????"),
                                fieldWithPath("data[].likeCount").description("????????? ??????"),
                                fieldWithPath("data[].boardThumbnailUrl").description("????????? ????????? url"),
                                fieldWithPath("data[].memberId").description("????????? ?????? ?????????"),
                                fieldWithPath("data[].name").description("????????? ?????? ??????"),
                                fieldWithPath("data[].memberImage").description("????????? ?????? ?????????")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void ?????????_?????????_????????????() throws Exception {
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
                                parameterWithName("boardId").description("????????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.boardInfoResponse.boardId").description("????????? ?????????"),
                                fieldWithPath("data.boardInfoResponse.seriesId").description("????????? ?????????"),
                                fieldWithPath("data.boardInfoResponse.title").description("????????? ??????"),
                                fieldWithPath("data.boardInfoResponse.content").description("????????? ?????????"),
                                fieldWithPath("data.boardInfoResponse.isPrivate").description("?????? / ?????????"),
                                fieldWithPath("data.boardInfoResponse.likeCount").description("????????? ??????"),
                                fieldWithPath("data.boardInfoResponse.boardThumbnailUrl").description("????????? ?????????"),
                                fieldWithPath("data.memberInfoResponse.email").description("?????????"),
                                fieldWithPath("data.memberInfoResponse.name").description("??????"),
                                fieldWithPath("data.memberInfoResponse.memberImage").description("?????????"),
                                fieldWithPath("data.memberInfoResponse.velogName").description("????????? ??????"),
                                fieldWithPath("data.memberInfoResponse.description").description("????????? ??????"),
                                fieldWithPath("data.hashTagList").description("???????????? ?????????")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void ?????????_?????????_?????????() throws Exception {
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
                                fieldWithPath("boardId").description("????????? ??? ????????? ?????????")
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
    void ?????????_?????????_???????????????() throws Exception {
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
                                fieldWithPath("boardId").description("????????? ??? ????????? ?????????")
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
