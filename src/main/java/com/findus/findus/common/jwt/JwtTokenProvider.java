package com.findus.findus.common.jwt;


import com.findus.findus.common.exception.UnauthorizedException;
import com.findus.findus.common.exception.UserNotFoundException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import org.json.simple.JSONObject;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "test";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private  long tokenValidTime = 1000 * 60L *60L *24 *365;
    //1시간  1일  1년
    private  long RefreshTime = 1000 * 60L *60L *24 *183;
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(HashMap userVo) {
        JSONObject json = new JSONObject(userVo);
        Claims claims = Jwts.claims().setSubject(json.toJSONString()); // JWT payload 에 저장되는 정보단위

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }


    // 토큰에서 회원 정보 추출
    public String getAuthToken(String token) {
        try{
            token = token.replace("Bearer " , "");
            String subject = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse( subject );
            JSONObject jsonObj = (JSONObject) obj;
            return jsonObj.get("auth_token").toString();
        }catch (Exception e){
            throw new UserNotFoundException("토큰을 찾을 수 없습니다.");
        }
    }

    public Date getRefreshDate( Date validDate ){
        return new Date(validDate.getTime() - RefreshTime);
    }

    public Date getTokenExpiration(String token) throws ParseException {
        token = token.replace("Bearer " , "");
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public List<String> resolveToken(ServerHttpRequest request) {
        return request.getHeaders().get("Authorization");
    }

    public JSONObject tokenValue(String token) throws ParseException {
        try{
            token = token.replace("Bearer ", "");
        }catch (Exception e){}
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse((String) claims.getBody().get("sub"));
        return  (JSONObject) obj;
    }
    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try{
            jwtToken = jwtToken.replace("Bearer ", "");
        }catch (Exception ignored){}
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
            throw new UnauthorizedException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
            throw new UnauthorizedException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
            throw new UnauthorizedException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
            throw new UnauthorizedException("JWT 토큰이 잘못되었습니다.");
        } catch (Exception e) {
            throw new UnauthorizedException("ERROR");
        }
    }
}
