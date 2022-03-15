package de.codexbella.week7security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

   private final JWTUtils jwtUtils;
   private final MongoUserDetailsService service;

   @Autowired
   public JwtAuthFilter(JWTUtils jwtUtils, MongoUserDetailsService service) {
      this.jwtUtils = jwtUtils;
      this.service = service;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
      String token = getToken(httpServletRequest);
      String userName = token != null ? jwtUtils.extractUserName(token) : null;
      if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

         UserDetails userDetails = service.loadUserByUsername(userName);

         if (jwtUtils.validateToken(token, userDetails.getUsername())) {

            UsernamePasswordAuthenticationToken token =
                  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities();

            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(token);
         }
      }
      filterChain.doFilter(httpServletRequest, httpServletResponse);
   }

   private String getToken(HttpServletRequest httpServletRequest) {
      String authHeader = httpServletRequest.getHeader("Authorization");
      if (authHeader != null) {
         return authHeader.replace("Bearer", "").trim();
      }
      return null;
   }

   private void setSecurityContext(Claims claims) {
      List<GrantedAuthority> grantedAuthorities = null;
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(claims.getSubject(),
   }
}
