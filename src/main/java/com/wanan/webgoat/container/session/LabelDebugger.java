package com.wanan.webgoat.container.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

public class LabelDebugger implements Serializable {
    private boolean enabled=false;

    public boolean isEnabled(){return enabled;}

    public void enable(){
        this.enabled = true;
    }
    public void disable(){this.enabled = false;}
    public void setEnabled(boolean enabled){this.enabled = enabled;}


}
