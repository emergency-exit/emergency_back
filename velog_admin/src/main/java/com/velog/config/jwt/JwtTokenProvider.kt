package com.velog.config.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.Getter
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import java.util.*

@Getter
@RequiredArgsConstructor
@Component
class JwtTokenProvider {

    val secretKey = "velog_admin"

    fun createToken(subject: String): String {
        val claims: Claims = Jwts.claims().setSubject(subject)
        val now = Date()
        val tokenValidTime = 1000L * 60 * 60
        val validity = Date(now.time + tokenValidTime)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun getSubject(token: String?): String? {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject()
    }

}
