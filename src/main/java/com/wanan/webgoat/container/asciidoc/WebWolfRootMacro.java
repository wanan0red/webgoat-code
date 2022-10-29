package com.wanan.webgoat.container.asciidoc;

import java.util.Map;

public class WebWolfRootMacro extends WebWolfMacro{
    public WebWolfRootMacro(String macroName){
        super(macroName);
    }
    public WebWolfRootMacro(String macroName, Map<String,Object> config){
        super(macroName,config);
    }
    @Override
    protected boolean includeWebWolfContext(){
        return false;
    }
}
