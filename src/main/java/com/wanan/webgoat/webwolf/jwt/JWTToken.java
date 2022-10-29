package com.wanan.webgoat.webwolf.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwx.CompactSerializer;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import java.util.Map;
import java.util.TreeMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.Base64Utils.decodeFromUrlSafeString;
import static org.springframework.util.StringUtils.hasText;
@NoArgsConstructor
//生成无参构造
@AllArgsConstructor
//生成全参构造
@Getter
//生成getter方法
@Setter
//生成setter方法
@Builder(toBuilder = true)
//可连续初始化对象  可以修改这个属性的值
public class JWTToken {
    private String encoded = "";
    private String secretKey;
    private String header;
    private boolean validHeader;
    private boolean validPayload;
    private boolean validToken;
    private String payload;
    private boolean signatureValid = true;

    public static JWTToken decode(String jwt, String secretKey) {
        var token = parseToken(jwt.trim().replace(System.getProperty("line.separator"), ""));
//        先去除字符串两端空格 这里是获取系统换行符接着将jwt中的换行符替换为空
        return token.toBuilder().signatureValid(validateSignature(secretKey, jwt)).build();
    }

    private static Map<String, Object> parse(String header) {
        var reader = new ObjectMapper();
        try {
            return reader.readValue(header, TreeMap.class);
//            这里进行反序列化出了一个treemap对象
        } catch (JsonProcessingException e) {
            return Map.of();
        }


    }

    private static String write(String originalValue, Map<String, Object> data) {
        var writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        try {
            if (data.isEmpty()) {
//                如果data是空的
                return originalValue;
//                就返回原先的字符串
            }
            return writer.writeValueAsString(data);
//            将传入的对象序列化成json格式
        } catch (JsonProcessingException e) {
            return originalValue;
        }
    }
    public static JWTToken  encode(String header, String payloadAsString, String secretKey) {
        var headers = parse(header);
        var payload = parse(payloadAsString);

        var builder = JWTToken.builder()
                .header(write(header, headers))
                .payload(write(payloadAsString, payload))
                .validHeader(!hasText(header) || !headers.isEmpty())
                .validToken(true)
                .validPayload(!hasText(payloadAsString) || !payload.isEmpty());

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(payloadAsString);
        headers.forEach((k, v) -> jws.setHeader(k, v));
        if (!headers.isEmpty()) { //otherwise e30 meaning {} will be shown as header
            builder.encoded(CompactSerializer.serialize(new String[]{jws.getHeaders().getEncodedHeader(), jws.getEncodedPayload()}));
        }

        //Only sign when valid header and payload
        if (!headers.isEmpty() && !payload.isEmpty() && hasText(secretKey)) {
            jws.setDoKeyValidation(false);
            jws.setKey(new HmacKey(secretKey.getBytes(UTF_8)));
            try {
                builder.encoded(jws.getCompactSerialization());
                builder.signatureValid(true);
            } catch (JoseException e) {
                //Do nothing
            }
        }
        return builder.build();
    }

    public static JWTToken parseToken(String jwt) {

        var token = jwt.split("\\.");
        //        按.分割jwt
        var builder = JWTToken.builder().encoded(jwt);
//        给encoded赋值为jwt
        if (token.length >= 2) {
            var header = new String(decodeFromUrlSafeString(token[0]), UTF_8);
//            先进行base64解码返回一个数组 接着转换成utf-8的形式
            var payloadAsString = new String(decodeFromUrlSafeString(token[1]), UTF_8);
            var headers = parse(header);
            var payload = parse(payloadAsString);
            builder.header(write(header, headers));
            builder.payload(write(payloadAsString, payload));
            builder.validHeader(!headers.isEmpty());

            builder.validPayload(!payload.isEmpty());
            builder.validToken(!headers.isEmpty() && !payload.isEmpty());
        } else {
            builder.validToken(false);
        }
        return builder.build();
    }



    private static boolean validateSignature(String secretKey, String jwt) {
        if (hasText(secretKey)) {
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setSkipAllValidators()
                    .setVerificationKey(new HmacKey(secretKey.getBytes(UTF_8)))
                    //                    设置密钥
                    .setRelaxVerificationKeyValidation()
                    .build();
            try {
                jwtConsumer.processToClaims(jwt);
                //                这里是用自定义的密钥去验证jwt
                return true;
            } catch (InvalidJwtException e) {
                return false;
            }
        }
        return false;
    }

}
