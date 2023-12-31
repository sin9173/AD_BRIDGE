package com.adbridge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    USER("USER", "유저", "ROLE_USER"),
    ADMIN("ADMIN", "관리자", "ROLE_ADMIN");

    private String code;

    private String desc;

    private String role;
}
