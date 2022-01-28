package com.atpl.mmg.AandA.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	 @Bean
	    public WebMvcConfigurer corsConfigurer()
	    {
	        return new WebMvcConfigurerAdapter() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	            	 registry.addMapping("/**").allowedMethods("GET","PUT","POST","DELETE","OPTIONS");
	            }
	        };
	    }
	    
	 @Override
	    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
	        configurer.setDefaultTimeout(1000000);
	    }
	    

}
