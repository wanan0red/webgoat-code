package com.wanan.webgoat.container.asciidoc;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class WebWolfMacro extends InlineMacroProcessor {
    public WebWolfMacro(String macroName){
        super(macroName);
    }
    public WebWolfMacro(String macroName, Map<String, Object> config){
        super(macroName,config);
    }
    @Override
    public Object process(ContentNode contentNode,String linkText,Map<String,Object> attributes){
        var env = EnvironmentExposure.getEnv();
        var hostname = determineHost(env.getProperty("webwolf.port"));
//        这里的webwolf.port其实就是配置文件中的webwolf端口
        var target = (String ) attributes.getOrDefault("target","home");
//        这里设定默认值为home?
        var href = hostname + "/" +target;
        if (displayCompleteLinkNoFormatting(attributes)){
//            判断是一个nolink的话
            linkText = href;
        }
        var options = new HashMap<String ,Object>();
        options.put("type",":link");
        options.put("target",href);
        attributes.put("window","_blank");
        return createPhraseNode(contentNode,"anchor",linkText,attributes,options).convert();


    }

    private boolean displayCompleteLinkNoFormatting(Map<String ,Object> attributes){
        return attributes.values().stream().anyMatch(a->a.equals("noLink"));
    }
    private String determineHost(String port){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String host = request.getHeader("Host");
//        获取请求头中的host
        int semicolonIndex = host.indexOf(":");
//        将host以:进行分割获取:的位置
        if (semicolonIndex == -1 || host.endsWith(":80")){
//            如果没有找到: 或者host是以:80结尾,那么就将:80进行替换 将www.web ...进行替换
            host = host.replace(":80","").replace("www.webgoat.local","www.webwolf.local");
        }else {
            host = host.substring(0,semicolonIndex);
            //            将host以semicolonIndex 进行分割
            host = host.concat(":").concat(port);
//            将9090进行拼接进去
        }
        return "http://"+host;
    }
    protected boolean includeWebWolfContext(){
        return true;
    }


}
