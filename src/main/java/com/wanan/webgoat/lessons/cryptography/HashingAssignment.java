package com.wanan.webgoat.lessons.cryptography;

import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DataTruncation;
import java.util.Locale;
import java.util.Random;
@RestController
@AssignmentHints({"crypto-hashing.hints.1","crypto-hashing.hints.2"})
public class HashingAssignment extends AssignmentEndpoint {
    public static final String [] SECRETS = {"secret","admin","password","123456","passw0rd"};

    @RequestMapping(path = "/crypto/hashing/md5",produces = MediaType.TEXT_HTML_VALUE)
//    这里是返回文本
    @ResponseBody
    public String getMd5(HttpServletRequest request) throws NoSuchAlgorithmException{
//当请求特定加密算法但在环境中不可用时，抛出此异常
        String md5Hash = (String) request.getSession().getAttribute("md5Hash");
//        获取session中的md5Hash
        if (md5Hash == null){
//            如果获取到是空
            String secret = SECRETS[new Random().nextInt(SECRETS.length)];
//            就通过密钥随机生成一个
            MessageDigest md = MessageDigest.getInstance("MD5");
//            获取一个md5 加密对象 因为是单例的所以直接获取 应该是单例吧
            md.update(secret.getBytes());
//            将得到的字符串进去处理一下
            byte[] digest = md.digest();
//            获取一个byte数组
            md5Hash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
//          通过java工具类将字节数组转换成 str 接着 转换成大写
            request.getSession().setAttribute("md5Hash",md5Hash);
//            密文和明文都放入session
            request.getSession().setAttribute("md5Secret",secret);
//
        }
        return md5Hash;
    }
    @RequestMapping(path = "/crypto/hashing/sha256",produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getSha256(HttpServletRequest request) throws NoSuchAlgorithmException{
//        同样先判断session中有无
        String sha256 = (String) request.getSession().getAttribute("sha256");
        if (sha256 == null){
            String secret = SECRETS[new Random().nextInt(SECRETS.length)];
//            获取明文
            sha256 = getHash(secret,"SHA-256");
//            通过 下面的getHash 获取 密文
            request.getSession().setAttribute("sha256Hash",sha256);
            request.getSession().setAttribute("sha256Secret",secret);
        }
        return sha256;
    }

    @PostMapping("/crypto/hashing")
    @ResponseBody
    public AttackResult completed(HttpServletRequest request,
                                  @RequestParam String answer_pwd1,
                                  @RequestParam String answer_pwd2){
        String  md5Secret = (String) request.getSession().getAttribute("md5Secret");
        String sha256Secret = (String) request.getSession().getAttribute("sha256Secret");
//      简单判断
        if (answer_pwd1 != null && answer_pwd2 != null){
            if (answer_pwd1.equals(md5Secret)
                && answer_pwd2.equals(sha256Secret)){
                return success(this)
                        .feedback("crypto-hashing.success")
                        .build();
            }else if (answer_pwd1.equals(md5Secret) || answer_pwd2.equals(sha256Secret)){
                return failed(this).feedback("crypto-hashing.oneok").build();
            }
        }
        return failed(this).feedback("crypto-hashing.empty").build();
    }



    public static String getHash(String  secret,String algorithm) throws NoSuchAlgorithmException{
//        一个密文一个加密方法 剩下和上面一样
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(secret.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();
    }

}
