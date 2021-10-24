package com.velog.config.jwt;

import com.velog.config.prefix.JwtTokenComponent;
import com.velog.exception.ValidationException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Getter
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtTokenComponent jwtTokenComponent;
    private final UserDetailsService userDetailsService;

    public String createToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        Date now = new Date();

        long tokenValidTime = 1000L * 60 * 60;
        Date validity = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtTokenComponent.getSecret())
                .compact();
    }

    //토큰에서 값 추출
    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(jwtTokenComponent.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    //유효한 토큰인지 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtTokenComponent.getSecret()).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            throw new ValidationException(String.format("유효하지 않은 (%s) 토큰입니다.", token));
        }
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", null);
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

}
