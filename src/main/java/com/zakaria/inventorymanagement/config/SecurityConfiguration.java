package com.zakaria.inventorymanagement.config;

import com.zakaria.inventorymanagement.service.auth.ApplicationUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.SessionManagementFilter;


import java.util.Arrays;
import java.util.Collections;


// https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

	@Autowired
	private ApplicationUserDetailService applicationUserDetailService;

	@Autowired
	private ApplicationRequestFilter applicationRequestFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.addFilterBefore(corsFilter(), SessionManagementFilter.class)
//				.authorizeHttpRequests((authz) -> authz
//						.requestMatchers("/**/authenticate",
//								"/**/company/create",
//								"/v2/api-docs",
//								"/swagger-resources",
//								"/swagger-resources/**",
//								"/configuration/ui",
//								"/configuration/security",
//								"/swagger-ui.html",
//								"/webjars/**",
//								"/v3/api-docs/**",
//								"/swagger-ui/**").permitAll()
//						.anyRequest().authenticated()
//				);
		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest()
				.permitAll());
		
		http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.addFilterBefore(corsFilter(), SessionManagementFilter.class)
//				.csrf().disable()
//				.authorizeRequests().antMatchers("/**/authenticate",
//						"/**/entreprises/create",
//						"/v2/api-docs",
//						"/swagger-resources",
//						"/swagger-resources/**",
//						"/configuration/ui",
//						"/configuration/security",
//						"/swagger-ui.html",
//						"/webjars/**",
//						"/v3/api-docs/**",
//						"/swagger-ui/**").permitAll()
//				.anyRequest().authenticated()
//				.and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		;
//
//		http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);
//	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // you can customize this as per your requirements
		config.setAllowedOriginPatterns(Collections.singletonList("*")); // you should restrict this in production
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
		source.registerCorsConfiguration("/**", config); // apply this configuration to all paths
		return new CorsFilter(source);
	}
	
}
