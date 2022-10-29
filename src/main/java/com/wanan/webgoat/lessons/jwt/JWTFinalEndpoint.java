package com.wanan.webgoat.lessons.jwt;

import com.wanan.webgoat.container.LessonDataSource;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@AssignmentHints({"jwt-final-hint1", "jwt-final-hint2", "jwt-final-hint3", "jwt-final-hint4", "jwt-final-hint5", "jwt-final-hint6"})
public class JWTFinalEndpoint extends AssignmentEndpoint {
    private final LessonDataSource dataSource;


    public JWTFinalEndpoint(LessonDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/JWT/final/follow/{user}")
    @ResponseBody
    public String follow(@PathVariable("user") String user) {
        if ("Jerry".equals(user)) {
            return "Following yourself seems redundant";
        } else {
            return "You are now following Tom";
        }
    }

    @PostMapping("/JWT/final/delete")
    @ResponseBody
    public AttackResult resetVotes(@RequestParam("token") String token) {
        if (StringUtils.isEmpty(token)) {
            return failed(this).feedback("jwt-invalid-token").build();
        } else {
            try {
                final String[] errorMessage = {null};
                Jwt jwt = Jwts.parser().setSigningKeyResolver(new SigningKeyResolverAdapter() {
                    //                    这里相当于一个自定义的用户签名验证系统 SigningKeyResolverAdapter
                    @Override
                    public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
                        final String kid = (String) header.get("kid");
//                        获取jwt 中的kid
                        try (var connection = dataSource.getConnection()) {
                            ResultSet rs = connection.createStatement().executeQuery("SELECT key FROM jwt_keys WHERE id = '" + kid + "'");
//                            执行sql语句这里很明显是存在sql注入的 也就是说这里的密钥其实是我们可以自己控制的
//                            比如这样SELECT key FROM jwt_keys WHERE id = 'webgoat_key' union select 'MQ==' from salaries --
//                            那么我们查询出来的密钥就是 这个MQ== 为啥base64 是因为后面有解密
                            while (rs.next()) {
                                return TextCodec.BASE64.decode(rs.getString(1));
                            }
                        } catch (SQLException e) {
                            errorMessage[0] = e.getMessage();
                        }
                        return null;
                    }
                }).parseClaimsJws(token);
//                这里是安全的
                if (errorMessage[0] != null) {
                    return failed(this).output(errorMessage[0]).build();
                }
                Claims claims = (Claims) jwt.getBody();
                String username = (String) claims.get("username");
                if ("Jerry".equals(username)) {
                    return failed(this).feedback("jwt-final-jerry-account").build();
                }
                if ("Tom".equals(username)) {
                    return success(this).build();
                } else {
                    return failed(this).feedback("jwt-final-not-tom").build();
                }
            } catch (JwtException e) {
                return failed(this).feedback("jwt-invalid-token").output(e.toString()).build();
            }
        }
    }
}

