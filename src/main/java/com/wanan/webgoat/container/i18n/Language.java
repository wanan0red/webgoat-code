package com.wanan.webgoat.container.i18n;

import lombok.AllArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@AllArgsConstructor
//作用于类，生成参数为所有实例变量的构造函数
public class Language {
    private final LocaleResolver localeResolver;
    public Locale getLocale(){
        return localeResolver.resolveLocale(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

    }

}
