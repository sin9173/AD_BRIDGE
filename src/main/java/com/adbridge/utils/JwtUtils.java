package com.adbridge.utils;

import com.adbridge.dto.auth.AuthDto;
import com.adbridge.entity.Member;
import com.adbridge.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;

@Component
public class JwtUtils implements InitializingBean {

    private Key key;

    private final String secret; //시크릿

    public final long TOKEN_VALIDATE_MILLIS; //액세스 토큰 유효시간

    public final long REFRESH_TOKEN_VALID_MILLIS; //리프레시 토큰 유효시간

    public final String ACCESS_TOKEN_KEY = "token";
    public final String REFRESH_TOKEN_KEY = "refresh_token";

    private final RequestUtils requestUtils;

    public JwtUtils(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.token-valid-millis}") long TOKEN_VALIDATE_MILLIS,
                    @Value("${jwt.refresh-token-valid-millis}") long REFRESH_TOKEN_VALID_MILLIS,
                    RequestUtils requestUtils) {
        this.secret = secret;
        this.TOKEN_VALIDATE_MILLIS = TOKEN_VALIDATE_MILLIS;
        this.REFRESH_TOKEN_VALID_MILLIS = REFRESH_TOKEN_VALID_MILLIS;
        this.requestUtils = requestUtils;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /** JWT 토큰 생성 */
    public String createAccessToken(Member member) { //액세스 토큰 생성
        Claims claims = Jwts.claims();
        claims.setSubject(member.getUsername());
        claims.put("username", member.getUsername());
        claims.put("memberId", member.getId());
        claims.put("memberRole", member.getRole().getCode());

        return createToken(claims, TOKEN_VALIDATE_MILLIS);
    }

    public String createAccessToken(AuthDto dto) { //액세스 토큰 생성
        Claims claims = Jwts.claims();
        claims.setSubject(dto.getUsername());
        claims.put("username", dto.getUsername());
        claims.put("memberId", dto.getId());
        claims.put("memberRole", dto.getMemberRole().getCode());

        return createToken(claims, TOKEN_VALIDATE_MILLIS);
    }
    
    public String createRefreshToken(String username) { //리프레시 토큰 생성
        Claims claims = Jwts.claims();
        claims.setSubject(username);
        return createToken(claims, REFRESH_TOKEN_VALID_MILLIS);
    }

    private String createToken(Claims claims, long expireTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + expireTime)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** 쿠키에 리프레시 토큰 저장 */
    public void refreshTokenCookie(String refresh_token, HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_KEY, refresh_token);
        cookie.setMaxAge((int) REFRESH_TOKEN_VALID_MILLIS);
        if(refresh_token==null) cookie.setMaxAge(0);
        cookie.setPath("/adbridge");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }


    /** 리프레시 토큰 가져오기 */
    public String refreshTokenParse() {
        HttpServletRequest request = requestUtils.getRequest();
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(REFRESH_TOKEN_KEY))
                .findAny().orElseGet(() -> new Cookie(REFRESH_TOKEN_KEY, "")).getValue();
    }

    /** 토큰 정보 가져오기 */
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** 헤더에서 토큰 가져오기 */
    public String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("authentication");
        if(header==null) header = request.getHeader("Authentication");
        return header != null ? header.replace("Bearer ", "") : "";
    }

    /** 토큰 정보가 본인의 것인지 확인 */
    public void personalCheck(Long memberId, HttpServletRequest request) {
        Long claimId = getClaims(getAccessToken(request)).get("memberId", Long.class);
        if(claimId!=memberId) throw new BadCredentialsException("본인의 계정이 아닙니다.");
    }

    /** 유효성 체크 */
    public void validateToken(String token) throws Exception {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new InvalidTokenException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("JWT 토큰이 잘못되었습니다.");
        }
    }
}
