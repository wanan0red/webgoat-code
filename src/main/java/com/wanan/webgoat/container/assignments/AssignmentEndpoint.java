package com.wanan.webgoat.container.assignments;

import com.wanan.webgoat.container.i18n.PluginMessages;
import com.wanan.webgoat.container.lessons.Initializeable;
import com.wanan.webgoat.container.session.UserSessionData;
import com.wanan.webgoat.container.session.WebSession;
import com.wanan.webgoat.container.users.WebGoatUser;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public class AssignmentEndpoint implements Initializeable {
    @Autowired
    private WebSession webSession;
    @Autowired
    private UserSessionData userSessionData;
    @Getter
    @Autowired
    private PluginMessages messages;
    protected WebSession getWebSession(){
        return webSession;
    }

    protected UserSessionData getUserSessionData(){
        return userSessionData;
    }
    protected AttackResult.AttackResultBuilder success(AssignmentEndpoint assignment){
        return AttackResult.builder(messages).lessonCompleted(true).attemptWasMade().feedback("assignment.solved").assignment(assignment);
    }
    protected AttackResult.AttackResultBuilder failed(AssignmentEndpoint assignment){
        return AttackResult.builder(messages).lessonCompleted(false).attemptWasMade().feedback("assignment.not.solved").assignment(assignment);
    }
    protected AttackResult.AttackResultBuilder informationMessage(AssignmentEndpoint assignmentEndpoint){
//        这里很明显示返回一个AttackResultBuilder
        return AttackResult.builder(messages).lessonCompleted(false).assignment(assignmentEndpoint);
//        这里对这个对象进行了链式赋值创建 首先这个messages其实并不是返回的信息 而是获取返回信息的配置文件 这里我们去看messages
    }
    @Override
    public void initialize(WebGoatUser user){

    }


}
