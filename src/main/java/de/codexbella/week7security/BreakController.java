package de.codexbella.week7security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/breakornobreak")
public class BreakController {
   @GetMapping
   public String breakOrNoBreak() {
      Random randomObject = new Random();
      String answer = randomObject.nextBoolean() ? "Yes, you can take a break." : "No break for you, sorry.";
      return answer;
   }

   @GetMapping("/vip")
   public String breakForever() {
      return "Yes, you can take a break, since you are such a very important person.";
   }
}
