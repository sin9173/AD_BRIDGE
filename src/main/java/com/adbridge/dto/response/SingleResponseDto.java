package com.adbridge.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SingleResponseDto <T> extends ResponseDto { //객체 응답

    private T data; //데이터

    public SingleResponseDto(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }
}
