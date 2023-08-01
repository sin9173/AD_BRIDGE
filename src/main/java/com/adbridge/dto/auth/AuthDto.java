package com.adbridge.dto.auth;

import com.adbridge.entity.Member;
import com.adbridge.enums.MemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuthDto {

    private String username;

    private String password;

    private Long id;

    private MemberRole memberRole;

    private String email;

    private String name;

    private String phone;

    public AuthDto(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.id = member.getId();
        this.memberRole = member.getRole();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phone = member.getPhone();
    }
}
