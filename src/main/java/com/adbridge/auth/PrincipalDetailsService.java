package com.adbridge.auth;

import com.adbridge.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsernameAndDeleteYnFalse(username)
                .map(member -> new PrincipalDetails(member))
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않거나 탈퇴된 계정입니다."));
    }
}
