package com.wanan.webgoat.container.asciidoc;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;

import java.util.Map;

public class WebGoatVersionMacro extends InlineMacroProcessor {
    public WebGoatVersionMacro(String macroName){super(macroName);}
    public WebGoatVersionMacro(String macroName, Map<String,Object> config){super(macroName,config);}

    @Override
    public Object process(ContentNode contentNode,String target,Map<String,Object> attributes){
        var webgoatVersion = EnvironmentExposure.getEnv().getProperty("webgoat.build.version");
        return createPhraseNode(contentNode,"quoted",webgoatVersion);
    }
}
