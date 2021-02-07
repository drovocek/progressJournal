package edu.volkov.progressjournal.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class BaseConfig {

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        final ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
        res.setDefaultEncoding("UTF-8");
        res.setFallbackToSystemLocale(false); // will force to use specified locale even if locale for current env is different
        res.addBasenames("classpath:messages/validation", "classpath:messages/messages", "classpath:messages/titles");
        return res;
    }

    @Bean
    public MessageSourceAccessor getMessageSourceAccessor(final MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource, Locale.US);
    }
}
