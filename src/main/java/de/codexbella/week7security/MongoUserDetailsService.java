package de.codexbella.week7security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MongoUserDetailsService implements UserDetailsService {
   private final UserService userService;

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      return userService.findByEmail(email)
            .map(userDocument -> new User(userDocument.getEmail(), userDocument.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+userDocument.getRole()))))
            .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
   }
}
