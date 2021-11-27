package com.velog.config.argument

import com.velog.config.auth.Auth
import com.velog.exception.ValidationException
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class MemberIdArgumentResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasAnnotation: Boolean = parameter.getParameterAnnotation(MemberId::class.java) != null
        if (hasAnnotation && parameter.getMethodAnnotation(Auth::class.java) == null) {
            throw ValidationException("jwt 인증하는데 애러가 발생했습니다.")
        }
        return hasAnnotation
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        return webRequest.getAttribute("memberId", 0)
    }
}