package de.codexbella.week7security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   private final UserDetailsService userDetailsService;
   private final JwtAuthFilter jwtAuthFilter;

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService);
   }
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
            .authorizeRequests()
            .mvcMatchers("/api/breakornobreak").hasAnyRole("USER", "ADMIN")
            .mvcMatchers("/api/breakornobreak/vip").hasRole("ADMIN")
            .mvcMatchers(HttpMethod.POST, "/api/users").permitAll()
            .mvcMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
            .antMatchers("/**").authenticated()
            //.and().formLogin()
            .and().httpBasic()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
   }

   @Override
   @Bean
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }
}
