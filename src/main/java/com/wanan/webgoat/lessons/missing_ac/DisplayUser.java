package com.wanan.webgoat.lessons.missing_ac;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Getter
public class DisplayUser {
    private String username;
    private boolean admin;
    private String userHash;

    public DisplayUser(User user,String passwordSalt){
        this.username = user.getUsername();
        this.admin = user.isAdmin();
        try {
            this.userHash =  genUserHash(user.getUsername(), user.getPassword(), passwordSalt);

        }catch (Exception ex){
            this.userHash = "Error generating user hash";
        }
    }

    protected String genUserHash(String username,String password, String passwordSalt)throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        这里是获取一个SHA-256的算法对象 其中直接调用的 静态方法 getInstance获取的 说明之前就new过了
        String salted = password + passwordSalt + username;
        byte[] hash = md.digest(salted.getBytes(StandardCharsets.UTF_8));
//        这里首先 通过getByte 以 ust-8的格式转换成bytes数组 接着使用sha-256去进行了加密
        return Base64.getEncoder().encodeToString(hash);
//        这里使用了base64进行了加密 加密的是数组

    }

}
