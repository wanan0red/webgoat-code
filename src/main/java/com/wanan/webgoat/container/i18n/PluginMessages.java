package com.wanan.webgoat.container.i18n;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

public class PluginMessages extends ReloadableResourceBundleMessageSource {
    private static final String PROPERTIES_SUFFIX = ".properties";
    //    定义后缀
    private final Language language;
    private final ResourcePatternResolver resourcePatternResolver;
//    创建一个ant路径匹配对象 作用类似于正则表达式

    public PluginMessages(Messages messages, Language language, ResourcePatternResolver resourcePatternResolver) {
        this.language = language;
        this.setParentMessageSource(messages);
        this.setBasename("WebGoatLabels");
        this.resourcePatternResolver = resourcePatternResolver;
//        进行初始化
    }

    @Override
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propertiesHolder) {
        Properties properties = new Properties();
        long lastModified = System.currentTimeMillis();

        try {
            var resources = resourcePatternResolver.getResources("classpath:/lessons/**/i18n" +
                    "/WebGoatLabels" + PROPERTIES_SUFFIX);
//            这里的**便是ant表达式 意思是匹配0个或者多个目录
            for (var resource : resources) {
                String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
//                获取资源路径
                PropertiesHolder holder = super.refreshProperties(sourcePath, propertiesHolder);
                properties.putAll(holder.getProperties());
//                获取配置文件
            }
        } catch (IOException e) {
            logger.error("Unable to read plugin message", e);
//            打印异常
        }
        return new PropertiesHolder(properties, lastModified);
    }

    public Properties getMessages() {
        return getMergedProperties(language.getLocale()).getProperties();
    }

    public String getMessage(String code, Object... args) {
        return getMessage(code, args, language.getLocale());
    }

    public String getMessage(String code, String defaultValue, Object... args) {
        return super.getMessage(code, args, defaultValue, language.getLocale());
    }
}
