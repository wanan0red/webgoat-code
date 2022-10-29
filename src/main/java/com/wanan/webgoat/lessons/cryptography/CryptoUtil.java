package com.wanan.webgoat.lessons.cryptography;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Base64;
import java.util.Random;

@Slf4j
public class CryptoUtil {
    private static final BigInteger[] FERMAT_PRIMS = {
            BigInteger.valueOf(3),
            BigInteger.valueOf(5),
            BigInteger.valueOf(17),
            BigInteger.valueOf(257),
            BigInteger.valueOf(65537)
    };

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//      KeyPairGenerator 用于生成一个RSA 密钥对
        RSAKeyGenParameterSpec kpgSpec = new RSAKeyGenParameterSpec(2048, FERMAT_PRIMS[new Random().nextInt(FERMAT_PRIMS.length)]);
//      这里是通过 RSAKeyGenParameterSpec 指定用于生成密钥的参数集
        keyPairGenerator.initialize(kpgSpec);
//        将参数集传入进去
        return keyPairGenerator.generateKeyPair();
//        生成密钥对的简单持有者 目的是用于暂存 密钥对?
    }

    public static String getPrivateKeyInPEM(KeyPair keyPair) {
        String encodedString = "-----BEGIN PRIVATE KEY-----\n";
        encodedString = encodedString + new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()), Charset.forName("UTF-8")) + "\n";
//        这里是对密钥进行拼接 生成私钥 上面的方法生成的 KeyPair 在这里 取到了里面的私钥
        encodedString = encodedString + "-----END PRIVATE KEY-----\n";
        return encodedString;
    }

    public static String signMessage(String message, PrivateKey privateKey) {
        log.debug("start signMessage");
        String signature = null;
        try {
            Signature instance = Signature.getInstance("SHA256withRSA");
//            获取签名方法
            instance.initSign(privateKey);
//            对签名初始化
            instance.update(message.getBytes("UTF-8"));
//            将信息进行加密
            signature = new String(Base64.getEncoder().encode(instance.sign()), Charset.forName("UTF-8"));
//            对信息进行签名
            log.info("signe the signature with result:{}", signature);
        } catch (NoSuchAlgorithmException e) {
            log.info("signe the signature with result: {}", signature);
        } catch (Exception e) {
            log.error("Signature signing failed", e);
        }
        log.debug("end signMessage");
        return signature;
    }

    public static boolean verifyMessage(String message, String base64EncSignature, PublicKey publicKey) {
        log.debug("start verifyMessage");
        boolean result = false;
        base64EncSignature = base64EncSignature.replace("\r", "")
                .replace("\n", "")
                .replace(" ", "");
//        验证信息
        byte[] decodedSignature = Base64.getDecoder().decode(base64EncSignature);
//        先进行base64解码
        try {
            Signature instance = Signature.getInstance("SHA256withRSA");
            instance.initVerify(publicKey);
//          通过获取的公钥进行签名
            instance.update(message.getBytes("UTF-8"));
//            对明文进行处理
            result = instance.verify(decodedSignature);
//            进行签名
            log.info("Signature verification failed", result);
        } catch (Exception e) {
            log.error("Signature verification failed", e);
        }
        log.debug("end verifyMessage");
        return result;
    }

    public static boolean verifyAssignment(String modulus, String signature, PublicKey publicKey) {
        boolean result = false;
        if (modulus != null && signature != null) {
            result = verifyMessage(modulus, signature, publicKey);

            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            if (modulus.length() == 512) {
                modulus = "00".concat(modulus);
            }
            result = result && (DatatypeConverter.printHexBinary(rsaPublicKey.getModulus().toByteArray()).equals(modulus.toUpperCase()));
        }
        return result;
    }

    public static PrivateKey getPrivateKeyFromPEM(String privateKeyPem) throws NoSuchAlgorithmException, InvalidKeySpecException {
        privateKeyPem = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKeyPem = privateKeyPem.replace("-----END PRIVATE KEY-----", "");
        privateKeyPem = privateKeyPem.replace("\n", "").replace("\r", "");
        byte[] decoded = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
//        获取一个 pkcs8加密
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePrivate(spec);
    }
}
