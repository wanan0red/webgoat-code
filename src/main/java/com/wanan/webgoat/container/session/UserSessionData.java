package com.wanan.webgoat.container.session;

import java.util.HashMap;

public class UserSessionData {
    private HashMap<String ,Object> userSessionData = new HashMap<>();

    public UserSessionData() {
    }
    public UserSessionData(String key,String value){
        setValue(key,value);
    }
    public Object getValue(String key){
        if (!userSessionData.containsKey(key)){
            return null;
        }
        return userSessionData.get(key);
    }
    public void setValue(String key,Object value){
        if (userSessionData.containsKey(key)){
//            如果包含这个key就替换
            userSessionData.replace(key,value);
        }else {
//            否则放入这个key
            userSessionData.put(key,value);
        }
    }

}
