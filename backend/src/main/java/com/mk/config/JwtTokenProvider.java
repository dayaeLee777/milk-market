package com.mk.config;


import com.mk.db.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PropertyKey;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
//@PropertySource("classpath:application-jwt.properties")
public class JwtTokenProvider {

    @Value("${jwt.secretekey}")
    private String secretKey;

    //토큰 유효시간30분 30
    private long tokenValidTime = 180 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    //객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());

    }

    //JWT 토큰 생성
    public String createToken(User user, List<String> roles){
        //JWT .playload 에 저장되는 정보단위
        Claims claims = Jwts.claims().setSubject(String.valueOf(user.getId()));
        //정보는 key/ value쌍으로 저장된다.
        
        claims.put("roles",roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)// 정보 저장
                .setIssuedAt(now)//토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime()+tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)//사용할 암호화 알고리즘과 signature에 들어갈 secrete값 세팅
                .compact();
    }

    //JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    // Request의 Header에서 token 값을 가져옵니다. 헤더 AUTHOIZATION을 파악한다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
    public boolean validateToken(String jwtToken) {
        try {
            validationAuthorizationHeader(jwtToken);
            jwtToken = extractToken(jwtToken);
            //키, 만료시간 확인
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            log.info("error : " +"토큰이 없거나 불명확한 토큰");
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    //토큰 있는지 없는지 확인, Bearer로 시작하는 지 확인
    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }
    }

    public String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }
}
