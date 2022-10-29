package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@AssignmentHints({"jwt-refresh-hint1", "jwt-refresh-hint2", "jwt-refresh-hint3", "jwt-refresh-hint4"})
public class JWTRefreshEndpoint extends AssignmentEndpoint {
    public static final String PASSWORD = "bm5nhSkxCXZkKRy4";
    private static final String JWT_PASSWORD = "bm5n3SkxCX4kKRy4";
    private static final List<String > validRefreshTokens = new ArrayList<>();

    @PostMapping(value = "/JWT/refresh/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity follow(@RequestBody(required = false) Map<String, Object> json) {
        if (json == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String user = (String) json.get("user");
        String password = (String) json.get("password");

        if ("Jerry".equalsIgnoreCase(user) && PASSWORD.equals(password)) {
//            如果用户名和密码都相同 为Jerry
            return ok(createNewTokens(user));
//            就创建一个新用户
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private Map<String ,Object> createNewTokens(String user) {
        Map<String ,Object> claims = new HashMap<>();
        claims.put("admin","false");
//        非admin
        claims.put("user",user);
        String token = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toDays(10)))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512,JWT_PASSWORD)
                .compact();
//        首先明确这里的 token 其实是 jwt中的
        Map<String ,Object> tokenJson = new HashMap<>();
//        这里的json其实是返回回去给人看的
        String refreshToken = RandomStringUtils.randomAlphabetic(20);
//        这里是生成了一个随机字母
        validRefreshTokens.add(refreshToken);
        tokenJson.put("access_token",token);
        tokenJson.put("refresh_token",refreshToken);
//        这里的
        return tokenJson;

    }

    @PostMapping("/JWT/refresh/checkout")
    @ResponseBody
    public ResponseEntity<AttackResult> checkout(@RequestHeader(value = "Authorization",required = false)String token){
        if (token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Jwt jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(token.replace("Bearer ",""));
//            检查是否是tom
            Claims claims = (Claims) jwt.getBody();
            String user = (String) claims.get("user");
            if ("Tom".equals(user)){
                return ok(success(this).build());
            }
            return ok(failed(this).feedback("jwt-refresh-not-tom").feedbackArgs(user).build());

        }catch (ExpiredJwtException e){
            return ok(failed(this).output(e.getMessage()).build());
        }catch (JwtException e){
            return ok(failed(this).feedback("jwt-invalid-token").build());
        }
    }
    @PostMapping("/JWT/refresh/newToken")
    @ResponseBody
    public ResponseEntity newToken(@RequestHeader(value = "Authorization", required = false) String token,
                                   @RequestBody(required = false) Map<String, Object> json) {
        if (token == null || json == null){
//            这里是如果两个有一个没传的话
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String user;
        String refreshToken;
        try {
//            这里我们假设token没传
            Jwt<Header, Claims> jwt = Jwts.parser().setSigningKey(JWT_PASSWORD).parse(token.replace("Bearer ", ""));
//            这里当然会抛异常
            user = (String) jwt.getBody().get("user");
            refreshToken = (String) json.get("refresh_token");
        }catch (ExpiredJwtException e){
            user = (String) e.getClaims().get("user");
//            这里获取了user
            refreshToken = (String) json.get("refresh_token");
//            获取了refresh_token
        }
        if (user == null || refreshToken == null){
//            如果有一个是空
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else if (validRefreshTokens.contains(refreshToken)){
//            如果已经认证的 refreshToken 存在这个的话
            validRefreshTokens.remove(refreshToken);
//            首先移除了refreshToken
            return ok(createNewTokens(user));
//            接着创建了一个新用户的token
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
