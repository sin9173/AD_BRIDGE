package com.adbridge.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ListResponseDto <T> extends ResponseDto { //리스트 응답

    private List<T> list; //리스트 응답데이터

    public ListResponseDto(String code, String message, List<T> list) {
        super(code, message);
        this.list = list;
    }
}
