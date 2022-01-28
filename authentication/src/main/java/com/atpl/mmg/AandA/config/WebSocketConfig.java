package com.atpl.mmg.AandA.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
	
@Autowired ObjectMapper objectMapper;
	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }
    
    @Override 
    public void configureClientInboundChannel(ChannelRegistration registration) { 
    } 
 
    @Override 
    public void configureClientOutboundChannel(ChannelRegistration registration) { 
        registration.taskExecutor().corePoolSize(4).maxPoolSize(10); 
    }
    
    @Override 
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) { 
        // TODO: ?? 
    } 
 
    @Override 
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) { 
        // TODO: ?? 
    } 
 
    @Override 
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) { 
        // Workaround for issue 2445: https://github.com/spring-projects/spring-boot/issues/2445 
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver(); 
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON); 
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter(); 
        converter.setObjectMapper(objectMapper); 
        converter.setContentTypeResolver(resolver); 
        messageConverters.add(converter); 
        return false; 
    }

	
//    @Override
//	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
//		// TODO Auto-generated method stub
//		
//	} 

}
