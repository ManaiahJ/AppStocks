package com.manusoft.config;

import com.manusoft.enums.Roles;
import com.manusoft.filter.TockenAuthenticationFilter;
import com.manusoft.util.ApiConstants;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig{
    @Value("${swagger.secure.enable:true}")
    private boolean enableSwagger;

    /**
     * @param webSecurity
     * @return
     * @throws Exception This method implemented to ignore some requests
     */
    public Filter webSecurityFilter(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring()
                .requestMatchers("/stocks/**", "/index")
                .and()
                .ignoring()
                .requestMatchers("/actuator/**");

        return webSecurity.build();
    }

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TockenAuthenticationFilter tockenAuthenticationFilter) throws Exception {

        // Disable form login, CSRF, and set session creation policy to STATELESS
        http.formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        final String wildCard = "/**";
        // Restrict access to URLs starting with ApiConstants.ADMIN_LOGIN_URL to users with admin role
        http.authorizeHttpRequests(request -> request.requestMatchers(ApiConstants.ADMIN_LOGIN_URL ).hasAuthority(Roles.ADMIN.getName()));
        // Allow access to URLs starting with ApiConstants.User_Login_URL to all users
        http.authorizeHttpRequests(request->request.requestMatchers(ApiConstants.User_Login_URL+"/auth/login" ).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers(ApiConstants.User_Login_URL ).authenticated());
        // Permit access to any other request
        http.authorizeHttpRequests(request -> request.anyRequest().permitAll());


//        if (enableSwagger) {
//            http.authorizeHttpRequests(request -> request
//                    .requestMatchers(new AntPathRequestMatcher("swagger-ui/**")).permitAll()
//                    .requestMatchers(new AntPathRequestMatcher("swagger-ui/**")).permitAll()
//                    .requestMatchers(new AntPathRequestMatcher("v3/api-docs/**")).permitAll()
//                    .requestMatchers(new AntPathRequestMatcher("v3/api-docs/**")).permitAll()
//                    .anyRequest().authenticated());
//
//        }
        http.addFilterBefore(tockenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // Return the built HttpSecurity object
        return http.build();
    }
    /**
     * Returns a BCrypt password encoder with strength 11.
     *
     * @return BCryptPasswordEncoder with strength 11
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);  // Use bcrypt for password encoding
    }



    /**
     * Configures and returns a RestTemplate with a read timeout and connect timeout both set to 1 millisecond.
     *
     * @param builder the RestTemplateBuilder used to build the RestTemplate
     * @return a RestTemplate instance with the specified timeouts
     */
    @Bean
    public RestTemplate resttemplateReadTimeOut(RestTemplateBuilder builder) {
        return builder.setReadTimeout(Duration.ofMillis(1)).setConnectTimeout(Duration.ofMillis(1)).build();
    }

}
