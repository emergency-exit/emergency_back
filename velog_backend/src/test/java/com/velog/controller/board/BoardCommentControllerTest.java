package com.velog.controller.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velog.controller.MemberSetUp;
import com.velog.domain.board.Board;
import com.velog.domain.board.BoardComment;
import com.velog.domain.board.repository.BoardCommentRepository;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.testObject.BoardCommentCreator;
import com.velog.domain.testObject.BoardCreator;
import com.velog.dto.boardComment.request.BoardCommentRequest;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs(uriHost = "52.79.226.150")
@AutoConfigureMockMvc
public class BoardCommentControllerTest extends MemberSetUp {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCommentRepository boardCommentRepository;

    @AfterEach
    void clean() {
        super.cleanup();
        boardRepository.deleteAll();
        boardCommentRepository.deleteAll();
    }

    @Test
    void 게시글_댓글_작성() throws Exception {
        // given
        Board board = BoardCreator.create("title1", member.getId());
        boardRepository.save(board);

        BoardCommentRequest.CreateBoardComment request = BoardCommentRequest.CreateBoardComment.testInstance(board.getId(), "content");

        // when
        final ResultActions resultActions = mockMvc.perform(
                        post("/api/v1/board/comment")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/comment/create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("boardId").description("게시글 아이디"),
                                fieldWithPath("content").description("댓글 컨텐츠")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.boardId").description("댓글 아이디"),
                                fieldWithPath("data.content").description("댓글 컨텐츠"),
                                fieldWithPath("data.memberInfoResponse.email").description("댓글 단 사람 이메일"),
                                fieldWithPath("data.memberInfoResponse.name").description("댓글 단 사람 이름"),
                                fieldWithPath("data.memberInfoResponse.memberImage").description("댓글 단 사람 이메일 사진"),
                                fieldWithPath("data.memberInfoResponse.velogName").description("댓글 단 사람 블로그 이름"),
                                fieldWithPath("data.memberInfoResponse.description").description("댓글 단 사람 이메일 블로그 설명")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 게시글_댓글_수정() throws Exception {
        // given
        Board board = BoardCreator.create("title1", member.getId());
        boardRepository.save(board);
        BoardComment comment = BoardCommentCreator.create(board.getId(), member.getId(), "content");
        boardCommentRepository.save(comment);

        BoardCommentRequest.UpdateBoardComment request = BoardCommentRequest.UpdateBoardComment.testInstance(comment.getId(), "updateContent");
        // when
        final ResultActions resultActions = mockMvc.perform(
                        post("/api/v1/board/comment/update")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/comment/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("boardCommentId").description("댓글 아이디"),
                                fieldWithPath("content").description("댓글 컨텐츠")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data.boardId").description("댓글 아이디"),
                                fieldWithPath("data.content").description("댓글 컨텐츠"),
                                fieldWithPath("data.memberInfoResponse.email").description("댓글 단 사람 이메일"),
                                fieldWithPath("data.memberInfoResponse.name").description("댓글 단 사람 이름"),
                                fieldWithPath("data.memberInfoResponse.memberImage").description("댓글 단 사람 이메일 사진"),
                                fieldWithPath("data.memberInfoResponse.velogName").description("댓글 단 사람 블로그 이름"),
                                fieldWithPath("data.memberInfoResponse.description").description("댓글 단 사람 이메일 블로그 설명")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 게시글_댓글_리스트_불러오기() throws Exception {
        // given
        Board board = BoardCreator.create("title1", member.getId());
        boardRepository.save(board);
        BoardComment comment = BoardCommentCreator.create(board.getId(), member.getId(), "content");
        boardCommentRepository.save(comment);

        // when
        final ResultActions resultActions = mockMvc.perform(
                        get("/board/comment/list/{boardId}", board.getId())
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board/comment/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("boardId").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").description("success"),
                                fieldWithPath("data").description("data"),
                                fieldWithPath("data[].boardId").description("댓글 아이디"),
                                fieldWithPath("data[].content").description("댓글 컨텐츠"),
                                fieldWithPath("data[].memberInfoResponse.email").description("댓글 단 사람 이메일"),
                                fieldWithPath("data[].memberInfoResponse.name").description("댓글 단 사람 이름"),
                                fieldWithPath("data[].memberInfoResponse.memberImage").description("댓글 단 사람 이메일 사진"),
                                fieldWithPath("data[].memberInfoResponse.velogName").description("댓글 단 사람 블로그 이름"),
                                fieldWithPath("data[].memberInfoResponse.description").description("댓글 단 사람 이메일 블로그 설명")
                        )
                ));

        // then
        resultActions.andExpect(status().isOk());
    }

}
