package com.adbridge.filter;

import com.adbridge.auth.PrincipalDetails;
import com.adbridge.exception.InvalidTokenException;
import com.adbridge.repository.MemberRepository;
import com.adbridge.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final MemberRepository memberRepository;

    /** JWT ACCESS TOKEN 인증 */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessToken(request);
        try {
            jwtUtils.validateToken(accessToken);
            Authentication authentication = getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            request.setAttribute("exception", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    /** 회원 정보 조회 후 Authentication 객체 가져오기 */
    private Authentication getAuthentication(String token) throws InvalidTokenException {
        Claims claims = jwtUtils.getClaims(token);
        PrincipalDetails principalDetails = new PrincipalDetails(memberRepository.findByUsernameAndDeleteYnFalse(claims.getSubject())
                .orElseThrow(() -> new InvalidTokenException("토큰의 회원정보가 잘못되었습니다.")));
        return new UsernamePasswordAuthenticationToken(principalDetails, token, principalDetails.getAuthorities());
    }

    /** 헤더에서 토큰 가져오기 */
    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("authentication");
        if(header==null) header = request.getHeader("Authentication");
        return header != null ? header.replace("Bearer ", "") : "";
    }
}
