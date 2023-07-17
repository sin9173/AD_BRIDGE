package com.adbridge.utils;

import com.adbridge.dto.response.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResponseUtils {

    // JSON 응답
    public void setResponse(int status, ResponseDto dto, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
            response.setStatus(status);
        } catch(Exception e) {
            log.error("응답에러", e);
        }
    }
}
