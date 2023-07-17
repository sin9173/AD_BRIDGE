package com.adbridge.auth;

import com.adbridge.dto.response.ResponseDto;
import com.adbridge.enums.ResponseResult;
import com.adbridge.repository.RefreshTokenRepository;
import com.adbridge.utils.JwtUtils;
import com.adbridge.utils.ResponseUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;

    private final ResponseUtils responseUtils;

    /** 로그아웃 처리 */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String refresh_token = jwtUtils.refreshTokenParse();
        if(!StringUtils.isEmpty(refresh_token)) refreshTokenRepository.findByUsername(jwtUtils.getClaims(refresh_token).getSubject());
        jwtUtils.refreshTokenCookie(null, response);
        responseUtils.setResponse(200, new ResponseDto(ResponseResult.SUCCESS), response);
    }
}
