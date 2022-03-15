package de.codexbella.week7security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Random;

@RestController
@RequestMapping("/api/breakornobreak")
public class BreakController {
   @GetMapping
   public String breakOrNoBreak(Principal principal) {
      Random randomObject = new Random();
      String answer = randomObject.nextBoolean() ? "Yes, you can take a break, "+principal.getName() : "No break for you, sorry, "+principal.getName();
      return answer;
   }

   @GetMapping("/vip")
   public String breakForever() {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String name = auth.getName();
      return "Yes, you can take a break, since you are such a very important person, "+name;
   }
}
