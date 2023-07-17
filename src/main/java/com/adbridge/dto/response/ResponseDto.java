package com.adbridge.dto.response;

import com.adbridge.enums.ResponseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDto { //응답데이터

    private String code; //응답코드

    private String message; //응답메세지

    public ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDto(ResponseResult result) {
        this.code = result.getCode();
        this.message = result.getMessage();
    }


}
