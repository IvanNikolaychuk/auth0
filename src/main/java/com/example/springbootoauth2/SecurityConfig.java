package com.example.springbootoauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Map;

@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {
    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/api/public").permitAll()
                .antMatchers("/api/admin").access("#oauth2.hasScope('admin:all')")
                .antMatchers("/api/user").access("#oauth2.hasScope('user:all')")
                .mvcMatchers("/api/**").authenticated()
                .anyRequest().permitAll();
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId);
//        resources.tokenServices(createTokenServices());
    }

//    @Bean
//    public DefaultTokenServices createTokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore( createTokenStore() );
//        return defaultTokenServices;
//    }
//
//    @Bean
//    public TokenStore createTokenStore() {
//        return new JwtTokenStore( createJwtAccessTokenConverter() );
//    }
//
//    @Bean
//    public JwtAccessTokenConverter createJwtAccessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setAccessTokenConverter( new JwtConverter() );
//        return converter;
//    }
//
//    public static class JwtConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {
//
//        @Override
//        public void configure(JwtAccessTokenConverter converter) {
//            converter.setAccessTokenConverter(this);
//        }
//
//        @Override
//        public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
//            OAuth2Authentication auth = super.extractAuthentication(map);
//            auth.setDetails(map); //this will get spring to copy JWT content into Authentication
//            return auth;
//        }
//    }
}

