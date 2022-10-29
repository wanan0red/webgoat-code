package com.wanan.dummy.insecure.framework;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.time.LocalDateTime;

@Slf4j
public class VulnerableTaskHolder implements Serializable {
    private static final  long serialVersionUID =2;
    private String taskName;
    private String taskAction;
    private LocalDateTime requestedExecutionTime;

    public VulnerableTaskHolder(String taskName, String taskAction) {
        super();
        this.taskName = taskName;
        this.taskAction = taskAction;
        this.requestedExecutionTime = LocalDateTime.now();
    }
    @Override
    public String toString(){
        return "VulnerableTaskHolder [taskName=" + taskName + ", taskAction=" + taskAction + ", requestedExecutionTime="
                + requestedExecutionTime + "]";
    }
    private void readObject(ObjectInputStream stream) throws Exception{
        stream.defaultReadObject();
        log.info("restoring task: {}",taskName);
        log.info("restoring time: {}",taskAction);

        if (requestedExecutionTime != null && (requestedExecutionTime.isBefore(LocalDateTime.now().minusMinutes(10)) || requestedExecutionTime.isAfter(LocalDateTime.now()))){
            log.debug(this.toString());
            throw new IllegalStateException("outdated");
        }
        if ((taskAction.startsWith("sleep") || taskAction.startsWith("ping")) && taskAction.length() < 22 ){
            log.info("about to execute: {}",taskAction);
            try {
                Process p = Runtime.getRuntime().exec(taskAction);
//                这里就是去执行这个命令了 不过我们这里需要延时5秒
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String  line = null;
                while ((line = in.readLine()) != null){
                    log.info(line);
                }
            }catch (IOException e){
                log.error("IO Exception",e);
            }
        }
    }
}
