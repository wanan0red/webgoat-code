package com.wanan.webgoat.lessons.spoofcookie.encoders;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

public class EncDec {
    private static final String SALT = RandomStringUtils.randomAlphabetic(10);
//    生成一个随机字符串密钥常量作为盐 长度为10
    private EncDec(){}
//    私有构造方法
    public static String encode(final String value){
        if (value == null){
            return null;
        }
        String encoded = value.toLowerCase() + SALT;
//        把需要加密的值与salt相加
        encoded = revert(encoded);
//        该函数将加密值进行逆序输出为字符串
        encoded = hexEncode(encoded);
//        进行16进制编码
        return base64Encode(encoded);

    }

    public static String decode(final String encodedValue) throws IllegalArgumentException{
//        抛出参数非法异常
        if (encodedValue == null){
            return null;
        }
        String decoded = base64Decode(encodedValue);
//        先进行base64解码
        decoded = hexDecode(decoded);
//        接着进行16进制解码
        decoded = revert(decoded);
//        将字符串逆序输出
        return decoded.substring(0,decoded.length() - SALT.length());
//        对解密后的字符串 去除盐值
    }

    public static String  revert(final String value){
        return new StringBuffer(value)
                .reverse()
                .toString();
    }
    private static String hexEncode(final String value){
        char[] encoded = Hex.encode(value.getBytes(StandardCharsets.UTF_8));
//            调用 静态方法将字符输出为数组 在转换为string返回
        return new String(encoded);
    }
    private static String hexDecode(final String value){
        byte[] decoded = Hex.decode(value);
        return new String(decoded);
    }
    private static String base64Encode(final String value){
//        调用 base64编码器 将数组转换为 base64字符串
        return Base64
                .getEncoder()
                .encodeToString(value.getBytes());
    }
    private static String base64Decode(final String value){
        byte[] decoded = Base64
                .getDecoder()
                .decode(value.getBytes());
        return new String(decoded);
    }


}
