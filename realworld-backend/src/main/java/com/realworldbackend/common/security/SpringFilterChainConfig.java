package com.realworldbackend.common.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.IpAddressMatcher;


@Configuration
@EnableWebSecurity
public class SpringFilterChainConfig {

    private final String[] WHITE_LIST = {"/api/**", "/api"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults());


        http
                .authorizeHttpRequests(request ->
                        request.dispatcherTypeMatchers(DispatcherType.FORWARD)
                                .permitAll()
                                .requestMatchers("/api/use")
                                .permitAll());

        http
                .authorizeHttpRequests(authorize -> {
                            try {
                                authorize
                                        .requestMatchers(WHITE_LIST).permitAll()
                                        .requestMatchers(new IpAddressMatcher("127.0.0.1")).permitAll();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
        return http.build();
    }
}

