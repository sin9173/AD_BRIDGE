package com.adbridge.dto.response.visit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class VisitListResDto {

    private LocalDate date;

    private Long count;

    public VisitListResDto(LocalDate date, Long count) {
        this.date = date;
        this.count = count;
    }
}
