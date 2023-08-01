package com.adbridge.entity;

import com.adbridge.dto.request.member.MemberModifyReqDto;
import com.adbridge.dto.request.member.SignUpReqDto;
import com.adbridge.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; //회원 아이디

    private String password; //회원 비밀번호
    
    private String name; //회원 이름

    private String email; //회원 이메일
    
    private String phone; //회원 전화번호

    @Enumerated(EnumType.STRING)
    private MemberRole role; //회원 권한


    public Member(SignUpReqDto dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.phone = dto.getPhone();
        this.role = MemberRole.USER;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {this.name = name; }

    public void updatePhone(String phone) {this.phone = phone; }

    public void updateRole(MemberRole role) {
        this.role = role;
    }

    public void updateInfo(MemberModifyReqDto dto) {
        if(StringUtils.hasText(dto.getEmail())) this.email = dto.getEmail();
    }

}
