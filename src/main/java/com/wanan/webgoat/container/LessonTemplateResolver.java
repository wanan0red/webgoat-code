package com.wanan.webgoat.container;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.StringTemplateResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class LessonTemplateResolver extends FileTemplateResolver {
    private static final String PREFIX = "lesson:";
//    定义前缀
    private ResourceLoader resourceLoader;
//    定义资源加载器
    private Map<String ,byte[]> resources = new HashMap<>();
//    定义容器存放加载到的文件

    public LessonTemplateResolver(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
        setResolvablePatterns(Set.of(PREFIX + "*"));
//        设置新的 模式 应用建立的 模板可以通过这个模板解析器来解决。  Set.of用于简单的创建不可变的少量元素的集合
    }

    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        var templateName = resourceName.substring(PREFIX.length());
//        截取模板的名字
        byte[] resource = resources.get(templateName);
//        获取存在map中的这个值
        if (resource == null) {
            try {
                resource = resourceLoader.getResource("classpath:/" + templateName).getInputStream().readAllBytes();
            } catch (IOException e) {
                log.error("Unable to find lesson HTML: {}", template);
            }
            resources.put(templateName, resource);
        }
        return new StringTemplateResource(new String(resource, StandardCharsets.UTF_8));
    }
}
