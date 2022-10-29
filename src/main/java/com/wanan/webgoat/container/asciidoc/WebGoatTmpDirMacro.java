package com.wanan.webgoat.container.asciidoc;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;

import java.util.Map;

public class WebGoatTmpDirMacro extends InlineMacroProcessor {
    public WebGoatTmpDirMacro(String macroName){super(macroName);}

    public WebGoatTmpDirMacro(String macroName, Map<String ,Object> config){
        super(macroName, config);
    }
    @Override
    public Object process(ContentNode contentNode,String  target,Map<String ,Object> attributes){
        var env = EnvironmentExposure.getEnv().getProperty("webgoat.server.directory");
        return createPhraseNode(contentNode,"quoted",env);
    }


}
