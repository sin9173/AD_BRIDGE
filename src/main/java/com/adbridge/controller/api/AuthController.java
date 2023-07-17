package com.adbridge.controller.api;

import com.adbridge.dto.request.auth.LoginReqDto;
import com.adbridge.dto.response.ResponseDto;
import com.adbridge.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginReqDto dto, HttpServletResponse response) { //로그인
        return ResponseEntity.ok(authService.login(dto, response));
    }

    @GetMapping("/token/renew")
    public ResponseEntity<ResponseDto> tokenRenew(HttpServletResponse response) throws Exception { //액세스 토큰 갱신
        return ResponseEntity.ok(authService.tokenRenew(response));
    }
}
