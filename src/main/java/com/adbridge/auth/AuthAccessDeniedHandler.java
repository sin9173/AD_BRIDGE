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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

    private final ResponseUtils responseUtils;

    /** 접근에 대해 권한이 없는 경우 */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if(request.getAttribute("exception")!=null) {
            responseUtils.setResponse(HttpStatus.FORBIDDEN.value(),
                    new ResponseDto(ResponseResult.INVALID_TOKEN.getCode(), request.getAttribute("exception").toString()), response);
            log.error(request.getAttribute("exception").toString());
        } else {
            responseUtils.setResponse(HttpStatus.FORBIDDEN.value(),
                    new ResponseDto(ResponseResult.ACCESS_DENIED), response);
        }
    }
}
