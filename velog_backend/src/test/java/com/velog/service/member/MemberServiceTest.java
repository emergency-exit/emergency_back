package com.velog.service.member;

import com.velog.domain.member.Email;
import com.velog.domain.member.Password;
import com.velog.domain.testObject.MemberCreator;
import com.velog.dto.member.request.UpdateMemberRequest;
import com.velog.enumData.ProviderType;
import com.velog.exception.ConflictException;
import com.velog.exception.NotFoundException;
import com.velog.exception.ValidationException;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.dto.member.request.CreateMemberRequest;
import com.velog.dto.member.request.LoginRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    void 회원가입_요청() {
        // given
        String email = "tnswh2023@naver.com";
        String password = "tnswh2023@";
        String name = "tnswh";
        CreateMemberRequest request = new CreateMemberRequest(email, password, name, null, ProviderType.LOCAL);

        // when
        memberService.createMember(request);

        // then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(1);
        assertThat(memberList.get(0).getEmail().getEmail()).isEqualTo(email);
    }

    @Test
    void 이미_존재하는_회원일경우_예외처리() {
        // given
        String email = "tnswh2023@naver.com";
        String password = "tnswh2023@";
        String name = "tnswh";
        Member member = MemberCreator.create(email, password);
        memberRepository.save(member);
        CreateMemberRequest request = new CreateMemberRequest(email, password, name, null, ProviderType.LOCAL);

        // when & then
        assertThatThrownBy(
            () -> memberService.createMember(request)
        ).isInstanceOf(ConflictException.class);
    }

    @Test
    void 로그인_하면_토큰이_생성된다() {
        // given
        Member member = Member.builder().email(Email.of("tnswh2023@gmail.com"))
                .name("tnswh")
                .password(Password.of(passwordEncoder.encode(Password.of("tnswh2023@").getPassword())))
                .build();
        memberRepository.save(member);

        LoginRequest request = new LoginRequest("tnswh2023@gmail.com", "tnswh2023@");

        // when
        String token = memberService.login(request);

        // then
        assertThat(token).startsWith("ey");
    }

    @Test
    void 로그인_이메일정보가_틀릴경우_예외발생() {
        // given
        Member member = memberCreate();
        memberRepository.save(member);

        LoginRequest request = new LoginRequest("tnswh2023@gmail.co", "tnswh2023@");

        // then
        assertThatThrownBy(
            () -> memberService.login(request)
        ).isInstanceOf(NotFoundException.class);
    }

    @Test
    void 로그인_비밀번호가_틀릴경우_예외발생() {
        // given
        Member member = memberCreate();
        memberRepository.save(member);

        LoginRequest request = new LoginRequest("tnswh2023@gmail.com", "tnswh202@");

        // then
        assertThatThrownBy(
                () -> memberService.login(request)
        ).isInstanceOf(ValidationException.class);
    }

    @Test
    void 유저_정보_변경() {
        //given
        Member member = MemberCreator.create();
        memberRepository.save(member);
        UpdateMemberRequest request = new UpdateMemberRequest("name", "velogname", "description");

        //when
        memberService.updateMember(request, member.getEmail().getEmail());

        //then
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(1);
        assertThat(memberList.get(0).getDescription()).isEqualTo(request.getDescription());
    }

    private Member memberCreate() {
        return Member.builder().email(Email.of("tnswh2023@gmail.com"))
                .name("tnswh")
                .password(Password.of(passwordEncoder.encode(Password.of("tnswh2023@").getPassword())))
                .build();
    }

}
