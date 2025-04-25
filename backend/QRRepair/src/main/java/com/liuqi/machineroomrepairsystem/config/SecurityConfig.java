package com.liuqi.machineroomrepairsystem.config;

import com.liuqi.machineroomrepairsystem.dto.security.CustomUserDetails;
import com.liuqi.machineroomrepairsystem.exception.security.SimpleAccessDeniedHandler;
import com.liuqi.machineroomrepairsystem.exception.security.SimpleAuthenticationEntryPoint;
import com.liuqi.machineroomrepairsystem.filter.JwtAuthenticationFilter;
import com.liuqi.machineroomrepairsystem.mapper.UserMapper;
import com.liuqi.machineroomrepairsystem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;

@SpringBootConfiguration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SimpleAuthenticationEntryPoint simpleAuthenticationEntryPoint;

    @Autowired
    private SimpleAccessDeniedHandler simpleAccessDeniedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setUserDetailsService(userDetailsService());
        return jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     *
     * 抛出: UsernameNotFoundException – if the user could not be found or the user has no GrantedAuthority
     * @return 完全填充的用户记录(never null)
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = userMapper.getUserByUsername(username);
            if(Objects.isNull(user)) throw new UsernameNotFoundException("username " + username + " is not found");
            return new CustomUserDetails(user);
        };
    }

    /**
     *  用户名/密码 的身份验证提供者
     * @return
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserCache(); // 可配置缓存
        return daoAuthenticationProvider;
    }

    /**
     *  spring security cors configuration
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(false); // 返回时是否生成凭证
        // 当allowCredentials为true时，allowedOrigins不能包含特殊值“*”，因为它不能在“Access-Control-Allow-Origin”响应头中设置。要允许凭据到一组来源，请显式列出它们，或者考虑使用“allowedOriginPatterns”。
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setMaxAge(Duration.ofHours(1));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // or .csrf(csrfCustomizer -> csrfCustomizer.disable()) ; close csrf 保护机制，本质上就是从 Spring Security 过滤器链中移除了 CsrfFilter
                .sessionManagement(sessionManagementCustomizer ->
                        sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/user/login","/user/register",
                                "/TBuilding/all",
                                "/lab/byLabName","/lab/byBuildingName",
                                "/repair/upload","/repair/appletRepair").permitAll()
                                .requestMatchers(HttpMethod.POST,"/repair/add").permitAll()
                                .requestMatchers("/user/**","/lab/**","/qrcode/**","/repair/**","/TBuilding/**").hasAuthority("ADMIN")
//                        .requestMatchers("/user/xxx").hasAnyAuthority("ADMIN","COMMON")
                        .anyRequest().authenticated()
                )
                .cors(cors-> cors.configurationSource(corsConfigurationSource()))
                .authenticationProvider(daoAuthenticationProvider()) // 认证提供者
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(securityException -> securityException
                        .authenticationEntryPoint(simpleAuthenticationEntryPoint)
                        .accessDeniedHandler(simpleAccessDeniedHandler)
                );
        return httpSecurity.build();
    }
}

