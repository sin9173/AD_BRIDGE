package com.adbridge.advice;

import com.adbridge.dto.response.ResponseDto;
import com.adbridge.exception.InvalidTokenException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(value = 100)
@RestControllerAdvice
public class ForbiddenAdvice {

    @Order(value = 100)
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ResponseDto> invalidToken(InvalidTokenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseDto("40301", e.getMessage()));
    }
}
