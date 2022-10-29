package com.wanan.webgoat.container.i18n;

import lombok.AllArgsConstructor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Properties;
@AllArgsConstructor
//作用于类，生成参数为所有实例变量的构造函数
public class Messages extends ReloadableResourceBundleMessageSource {
    private final Language language;
    public Properties getMessages(){return getMergedProperties(language.getLocale()).getProperties();}
    public String getMessage(String code,Object... args){return getMessage(code,args,language.getLocale());}
    public String getMessage(String code,String defaultValue,Object... args){
        return super.getMessage(code,args,defaultValue,language.getLocale());
    }

}
