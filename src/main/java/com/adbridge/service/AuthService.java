package com.adbridge.service;

import com.adbridge.auth.PrincipalDetails;
import com.adbridge.dto.auth.AuthDto;
import com.adbridge.dto.request.auth.LoginReqDto;
import com.adbridge.dto.response.ResponseDto;
import com.adbridge.dto.response.SingleResponseDto;
import com.adbridge.dto.response.auth.LoginResDto;
import com.adbridge.entity.Member;
import com.adbridge.entity.RefreshToken;
import com.adbridge.enums.ResponseResult;
import com.adbridge.exception.InvalidTokenException;
import com.adbridge.repository.MemberRepository;
import com.adbridge.repository.RefreshTokenRepository;
import com.adbridge.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenRepository refreshTokenRepository;

    private final MemberRepository memberRepository;

    /** 로그인 */
    public SingleResponseDto<LoginResDto> login(LoginReqDto dto, HttpServletResponse response) {
        /** 회원 인증 */
        AuthDto authDto = authentication(dto);
        
        /** JWT 응답 */
        tokenResponse(jwtUtils.createAccessToken(authDto), authDto.getUsername(), response);
        return new SingleResponseDto(ResponseResult.SUCCESS.getCode(), "성공", LoginResDto.builder().dto(authDto).build());
    }

    /** Access Token 갱신 */
    public ResponseDto tokenRenew(HttpServletResponse response) throws Exception {
        Member member = refreshTokenCheck();
        tokenResponse(jwtUtils.createAccessToken(member), member.getUsername(), response);
        return new ResponseDto("20001", "성공");
    }

    //PRIVATE METHOD

    /** ACCESS TOKEN 재발급 */
    private Member refreshTokenCheck() throws Exception {
        String refresh_token = jwtUtils.refreshTokenParse().trim();
        jwtUtils.validateToken(refresh_token);
        String username = jwtUtils.getClaims(refresh_token).getSubject().trim();
        Member member = memberRepository.findByUsernameAndDeleteYnFalse(username)
                .orElseThrow(() -> new InvalidTokenException("토큰의 회원정보가 잘못되었습니다."));

        /** DB Refresh Token Check */
        List<RefreshToken> refreshTokenList = refreshTokenRepository.findByUsernameAndExpireDateAfter(username, LocalDateTime.now());
        if(refreshTokenList.size()==0) throw new InvalidTokenException("리프레시 토큰의 정보가 유효하지 않습니다.");
        return member;

    }

    /** 회원 정보 인증 후 Authentication 객체 반환 */
    private AuthDto authentication(LoginReqDto dto) {
        /** 인증 */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ((PrincipalDetails) authentication.getPrincipal()).getAuthDto();
    }
    
    /** JWT 토큰 응답 */
    private void tokenResponse(String access_token, String username, HttpServletResponse response) {
        response.setHeader(jwtUtils.ACCESS_TOKEN_KEY, access_token);

        /** 리프레시 토큰 쿠키 응답 */
        String refreshToken = jwtUtils.createRefreshToken(username);
        jwtUtils.refreshTokenCookie(refreshToken, response);

        /** 리프레시 토큰 DB 처리 */
        refreshTokenRepository.findByUsername(username)
                .forEach(r -> refreshTokenRepository.delete(r));
        refreshTokenRepository.save(new RefreshToken(username, refreshToken, jwtUtils.REFRESH_TOKEN_VALID_MILLIS));
    }
}
