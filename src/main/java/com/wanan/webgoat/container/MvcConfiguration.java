package com.wanan.webgoat.container;

import com.wanan.webgoat.container.i18n.Language;
import com.wanan.webgoat.container.i18n.Messages;
import com.wanan.webgoat.container.i18n.PluginMessages;
import com.wanan.webgoat.container.lessons.LessonScanner;
import com.wanan.webgoat.container.session.LabelDebugger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;
import org.thymeleaf.templateresource.StringTemplateResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;


@Configuration
//注解类
@RequiredArgsConstructor
//写在类上可以代替@Autowired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
//@Autowired 注释添加到需要该类型数组的字段或方法，则 Spring 会从ApplicationContext 中搜寻符合指定类型的所有 bean
public class MvcConfiguration implements WebMvcConfigurer {
    private static final String UTF8 = "UTF-8";
    //    定义字符集
    private final LessonScanner lessonScanner;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/lesson_content").setViewName("lesson_content");
        registry.addViewController("/start.mvc").setViewName("main_new");
        registry.addViewController("/scoreboard").setViewName("scoreboard");
    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine thymeleafTemplateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(thymeleafTemplateEngine);
//        设置模板引擎是thymeleafTemplateEngine
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
//        设置字符集utf-8
        return resolver;
    }

    @Bean
    public ITemplateResolver lessonThymeleafTemplateResolver(ResourceLoader resourceLoader) {
//        大概就是获取一个模板解析器
        var resolver = new FileTemplateResolver() {
            @Override
            protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
                try (var is = resourceLoader.getResource("classpath:" + resourceName).getInputStream()) {
                    return new StringTemplateResource(new String(is.readAllBytes(), StandardCharsets.UTF_8));
                } catch (IOException e) {
                    return null;
                }
            }
        };
        resolver.setOrder(1);
        //        设置解析器的执行顺序为1
        return resolver;
    }

    @Bean
    public ITemplateResolver springThymeleafTemplateResolver(ApplicationContext applicationContext) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/webgoat/templates/");
//        设置模板文件的前缀
        resolver.setSuffix(".html");
//        设置模本文件的后缀
        resolver.setTemplateMode(TemplateMode.HTML);
//        设置模本模型为html
        resolver.setOrder(2);
//        设置解析器的执行顺序为2
        resolver.setCharacterEncoding(UTF8);
//        设置字符集
        resolver.setApplicationContext(applicationContext);
        return resolver;
    }

    @Bean
    public LessonTemplateResolver LessonTemplateResolver(ResourceLoader resourceLoader) {
        LessonTemplateResolver lessonTemplateResolver = new LessonTemplateResolver(resourceLoader);
        lessonTemplateResolver.setOrder(0);
        lessonTemplateResolver.setCacheable(false);
//        不启用缓存模式
        lessonTemplateResolver.setCharacterEncoding(UTF8);
        return lessonTemplateResolver;

    }

    @Bean
    public AsciiDoctorTemplateResolver asciiDoctorTemplateResolver(ResourceLoader resourceLoader) {
        AsciiDoctorTemplateResolver resolver = new AsciiDoctorTemplateResolver(resourceLoader);
        resolver.setCacheable(false);
        resolver.setOrder(1);
        resolver.setCharacterEncoding(UTF8);
        return resolver;

    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver springThymeleafTemplateResolver,
                                                        LessonTemplateResolver lessonTemplateResolver,
                                                        AsciiDoctorTemplateResolver asciiDoctorTemplateResolver,
                                                        ITemplateResolver lessonThymeleafTemplateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.addDialect(new SpringSecurityDialect());
        engine.setTemplateResolvers(Set.of(lessonTemplateResolver, asciiDoctorTemplateResolver, lessonThymeleafTemplateResolver, springThymeleafTemplateResolver));
        return engine;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/webgoat/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/webgoat/static/js/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:/webgoat/static/plugins/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/webgoat/static/fonts/");
//        注册静态资源

        registry.addResourceHandler("/images/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/images/").toArray(String[]::new));
        registry.addResourceHandler("/lesson_js/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/js/").toArray(String[]::new));
        registry.addResourceHandler("/lesson_css/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/css/").toArray(String[]::new));
        registry.addResourceHandler("/lesson_templates/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/templates").toArray(String[]::new));
        registry.addResourceHandler("/video/**").addResourceLocations(lessonScanner.applyPattern("classpath:/lessons/%s/video").toArray(String[]::new));
//        注册lessons的静态资源

    }

    @Bean
    public PluginMessages pluginMessages(Messages messages, Language language,
                                         ResourcePatternResolver resourcePatternResolver) {
        PluginMessages pluginMessages = new PluginMessages(messages, language, resourcePatternResolver);
        pluginMessages.setDefaultEncoding("UTF-8");
        pluginMessages.setBasename("i18n/WebGoatLables");
        pluginMessages.setFallbackToSystemLocale(false);
        return pluginMessages;
    }

    @Bean
    public Language language(LocaleResolver localeResolver) {
        return new Language(localeResolver);
    }

    //    配置语言
    @Bean
    public Messages messageSource(Language language) {
        Messages messages = new Messages(language);
        messages.setDefaultEncoding("UTF-8");
        messages.setBasename("classpath:i18n/messages");
        messages.setFallbackToSystemLocale(false);
        return messages;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }

    @Bean
    public LabelDebugger labelDebugger() {
        return new LabelDebugger();
    }
}
