package com.adbridge.auth;

import com.adbridge.dto.response.ResponseDto;
import com.adbridge.enums.ResponseResult;
import com.adbridge.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final ResponseUtils responseUtils;

    /** JWT 토큰 인증에 실패한 경우 */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(request.getAttribute("exception")!=null) {
            responseUtils.setResponse(HttpStatus.FORBIDDEN.value(),
                    new ResponseDto(ResponseResult.INVALID_TOKEN.getCode(), request.getAttribute("exception").toString()), response);
            log.error(request.getAttribute("exception").toString());
        } else {
            responseUtils.setResponse(HttpStatus.FORBIDDEN.value(),
                    new ResponseDto(ResponseResult.INVALID_TOKEN), response);
        }
    }
}
