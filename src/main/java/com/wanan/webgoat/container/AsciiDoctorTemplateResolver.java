package com.wanan.webgoat.container;

import com.wanan.webgoat.container.asciidoc.*;
import lombok.extern.slf4j.Slf4j;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.StringTemplateResource;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.asciidoctor.Asciidoctor.Factory.create;
@Slf4j
public class AsciiDoctorTemplateResolver extends FileTemplateResolver {

    private static final Asciidoctor asciidoctor = create();
    private static final String PREFIX = "doc:";
//    定义前缀
    private final ResourceLoader resourceLoader;

    public AsciiDoctorTemplateResolver(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
        setResolvablePatterns(Set.of(PREFIX + "*"));

    }
    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        var templateName =resourceName.substring(PREFIX.length());
        try(InputStream is = resourceLoader.getResource("classpath:/"+templateName).getInputStream()) {
            JavaExtensionRegistry extensionRegistry = asciidoctor.javaExtensionRegistry();
            extensionRegistry.inlineMacro("webWolfLink", WebWolfMacro.class);
            extensionRegistry.inlineMacro("webWolfRootLink", WebWolfRootMacro.class);
            extensionRegistry.inlineMacro("webGoatVersion", WebGoatVersionMacro.class);
            extensionRegistry.inlineMacro("webGoatTempDir", WebGoatTmpDirMacro.class);
            extensionRegistry.inlineMacro("operatingSystem", OperatingSystemMacro.class);
            extensionRegistry.inlineMacro("username",UsernameMacro.class);

            StringWriter writer = new StringWriter();
            asciidoctor.convert(new InputStreamReader(is),writer,createAttributes());
            return new StringTemplateResource(writer.getBuffer().toString());
        } catch (IOException e) {
            return new StringTemplateResource("<div>Unable to find documentation for:" + templateName + "<div>");
        }
    }
    private Map<String ,Object> createAttributes(){
        Map<String ,Object> attributes = new HashMap<>();
        attributes.put("source-highligher","coderay");
        attributes.put("backend","xhtml");
        attributes.put("icons", Attributes.FONT_ICONS);
        Map<String ,Object> options = new HashMap<>();
        options.put("attributes",attributes);
        return options;
    }

}
