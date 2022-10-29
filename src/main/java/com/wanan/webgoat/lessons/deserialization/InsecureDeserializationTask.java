package com.wanan.webgoat.lessons.deserialization;

import com.wanan.dummy.insecure.framework.VulnerableTaskHolder;
import com.wanan.webgoat.container.assignments.AssignmentEndpoint;
import com.wanan.webgoat.container.assignments.AssignmentHints;
import com.wanan.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.util.Base64;

@RestController
@AssignmentHints({"insecure-deserialization.hints.1", "insecure-deserialization.hints.2", "insecure-deserialization.hints.3"})
public class InsecureDeserializationTask extends AssignmentEndpoint {
    @PostMapping("/InsecureDeserialization/task")
    @ResponseBody
    public AttackResult completed(@RequestParam String token) throws IOException{
        String b64token;
        long before;
        long after;
        int delay;
        b64token = token.replace('-','+').replace('_','/');
//        首先替换下token
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(b64token)))){
//            进行base64解密 并读到输入流中
            before = System.currentTimeMillis();
//            获取执行前时间
            Object o = ois.readObject();
//            反序列化开始
            if (!(o instanceof VulnerableTaskHolder)){
//                如果 反序列化对象不属于 VulnerableTaskHolder
                if (o instanceof String){
                    return failed(this).feedback("insecure-deserialization.stringobject").build();
                }
                return failed(this).feedback("insecure-deserialization.wrongobject").build();
            }
            after = System.currentTimeMillis();
//            接着获取当前的时间
        }catch (InvalidClassException e){
            return failed(this).feedback("insecure-deserialization.invalidversion").build();
        }catch (IllegalArgumentException e){
            return failed(this).feedback("insecure-deserialization.expired").build();
        }catch (Exception e){
            return failed(this).feedback("insecure-deserialization.invalidversion").build();
        }
        delay = (int) (after - before);
        if (delay > 7000){
            return failed(this).build();
        }
        if (delay < 3000){
            return failed(this).build();
        }
        return success(this).build();

    }
}
