package com.wanan.webgoat.lessons.auth_bypass;

import java.util.HashMap;
import java.util.Map;

public class AccountVerificationHelper {
    private static final Integer verifyUserId = 1223445;
    private static final Map<String ,String > userSecQuestions = new HashMap<>();

    static {
        userSecQuestions.put("secQuestion0","Dr. Watson");
        userSecQuestions.put("secQuestion1","Baker Street");

    }
    private static final Map<Integer,Map> secQuestionStore = new HashMap<>();
    static {
        secQuestionStore.put(verifyUserId,userSecQuestions);
    }
    public boolean didUserLikelyCheat(HashMap<String ,String> submittedAnswers){
        boolean likely = false;
        if (submittedAnswers.size() == secQuestionStore.get(verifyUserId).size()){
//            如果两个数量相同
            likely = true;
        }
        if ((submittedAnswers.containsKey("secQuestion0")&& submittedAnswers.get("secQuestion0").equals(secQuestionStore.get(verifyUserId).get("secQuestion0")))
                && (submittedAnswers.containsKey("secQuestion1") && submittedAnswers.get("secQuestion1").equals(secQuestionStore.get(verifyUserId).get("secQuestion1"))) ){
//            这里代表的是你不能直接看源码去拿到这两个值
            likely = true;
        }else {
            likely = false;
        }
        return likely;
    }
    public boolean  verifyAccount(Integer userId,HashMap<String,String > submittedQuestions){
        if (submittedQuestions.entrySet().size() != secQuestionStore.get(verifyUserId).size()){
//            如果传入的答案和标准答案数量不相同
            return false;
        }
        if (submittedQuestions.containsKey("secQuestion0") && !submittedQuestions.get("secQuestion0").equals(secQuestionStore.get(verifyUserId).get("secQuestion0"))){
//            如果有这个值 并且和原先的值不相同
            return false;
        }
        if (submittedQuestions.containsKey("secQuestion1") && !submittedQuestions.get("secQuestion1").equals(secQuestionStore.get(verifyUserId).get("secQuestion1"))) {
            return false;
        }
        return true;
    }
}
