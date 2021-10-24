package com.velog.config.exception;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.velog.exception.errorCode.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private static final String UTF8 = "application/json;charset=UTF-8";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            setErrorResponse(response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType(UTF8);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("code", ErrorCode.JWT_UNAUTHORIZED_EXCEPTION.getCode());
        responseJson.put("message", e.getMessage());
        responseJson.put("description", "유효하지 않은 토큰입니다.");

        response.getWriter().print(responseJson);
    }

}
