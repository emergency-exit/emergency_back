package com.velog.service.auth;

import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.dto.auth.request.AuthRequest;
import com.velog.dto.auth.response.KaKaoUserInfoResponse;
import com.velog.dto.auth.response.KakaoAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthApi authApi;
    private final MemberRepository memberRepository;

    public KaKaoUserInfoResponse kakaoAuthentication(AuthRequest request) {
        KakaoAccessTokenResponse kakaoAccessTokenResponse = authApi.tokenAuthentication(request);
        KaKaoUserInfoResponse kaKaoUserInfoResponse = authApi.getUserInfo(kakaoAccessTokenResponse.getAccessToken());
        Member member = memberRepository.findByEmail(kaKaoUserInfoResponse.getKakaoAccount().getEmail());
        if (member == null) {
            return KaKaoUserInfoResponse.save(kaKaoUserInfoResponse.getKakaoAccount().getProfile().getNickname(), kaKaoUserInfoResponse.getKakaoAccount().getProfile().getProfileImageUrl(), kaKaoUserInfoResponse.getKakaoAccount().getEmail());
        }
        return kaKaoUserInfoResponse;
    }

}
