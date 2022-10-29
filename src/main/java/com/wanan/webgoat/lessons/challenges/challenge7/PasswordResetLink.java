package com.wanan.webgoat.lessons.challenges.challenge7;


import java.util.Random;

public class PasswordResetLink {
    public String createPasswordReset(String username,String key){
        Random random = new Random();
        if (username.equalsIgnoreCase("admin")){
            random.setSeed(key.length());
        }
        return scramble(random,scramble(random,scramble(random,MD5.getHashString(username))));
    }
    public static String scramble(Random random,String inputString){
        char[] a = inputString.toCharArray();
        for (int i = 0; i < a.length;i++){
            int j = random.nextInt(a.length);
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        return new String(a);
    }
}
