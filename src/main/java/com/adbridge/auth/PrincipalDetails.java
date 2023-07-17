package com.adbridge.auth;

import com.adbridge.dto.auth.AuthDto;
import com.adbridge.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class PrincipalDetails implements UserDetails {

    private AuthDto authDto;

    public PrincipalDetails(Member member) {
        this.authDto = new AuthDto(member);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> authDto.getMemberRole().getRole());
        return authorities;
    }

    @Override
    public String getPassword() {
        return authDto.getPassword();
    }

    @Override
    public String getUsername() {
        return authDto.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
