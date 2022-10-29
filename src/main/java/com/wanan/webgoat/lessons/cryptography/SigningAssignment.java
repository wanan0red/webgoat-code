package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;

@RestController
@AssignmentHints({"crypto-signing.hints.1","crypto-signing.hints.2", "crypto-signing.hints.3", "crypto-signing.hints.4"})
@Slf4j
public class SigningAssignment extends AssignmentEndpoint {
    @RequestMapping(path = "/crypto/signing/getprivate",produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getPrivateKey(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException{
        String privateKey = (String) request.getSession().getAttribute("privateKeyString");
        if (privateKey == null){
            KeyPair keyPair = CryptoUtil.generateKeyPair();
//            首先生成密钥对
            privateKey = CryptoUtil.getPrivateKeyInPEM(keyPair);
//            生成可以发送的密钥 也就是前后添加规范字符
            request.getSession().setAttribute("privateKeyString",privateKey);
            request.getSession().setAttribute("keyPair",keyPair);
        }
        return privateKey;
    }

    @PostMapping("/crypto/signing/verify")
    @ResponseBody
    public AttackResult completed(HttpServletRequest request,
                                  @RequestParam String modulus,
                                  @RequestParam String signature){
        String tempModulus = modulus;
        KeyPair keyPair = (KeyPair) request.getSession().getAttribute("keyPair");
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
//        获取公钥
        if (tempModulus.length() == 512){
            tempModulus = "00".concat(tempModulus);
//            如果长度是512 就用00去拼接
        }
        if (!DatatypeConverter.printHexBinary(rsaPublicKey.getModulus().toByteArray()).equals(tempModulus.toUpperCase())){
//            如果获取公钥的16进制 等于tempModulus
            log.warn("modulus {} incorrect",modulus);
            return failed(this).feedback("crypto-signing.modulusnotok").build();
        }
        if (CryptoUtil.verifyAssignment(modulus,signature,keyPair.getPublic())){
//            如果起签名验证正确
            return success(this).feedback("crypto-signing.success").build();
        }else {
            log.warn("signature incorrect");
            return failed(this).feedback("crypto-signing.notok").build();
        }
    }

}
