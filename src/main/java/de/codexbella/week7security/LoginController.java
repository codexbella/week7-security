package de.codexbella.week7security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth/login")
@RequiredArgsConstructor
public class LoginController {
   private final AuthenticationManager authenticationManager;
   private final JwtService jwtService;

   @PostMapping
   public String login(@RequestBody LoginData loginData) {
      try {
         authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginData.getUserName(), loginData.getPassword())
         );
         return jwtService.createToken(new HashMap<>(), loginData.getUserName());
      } catch (Exception e) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials");
      }
   }
}
