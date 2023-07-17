package com.adbridge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    USER("USER", "유저"),
    ADMIN("ADMIN", "관리자");

    private String code;

    private String desc;
}
