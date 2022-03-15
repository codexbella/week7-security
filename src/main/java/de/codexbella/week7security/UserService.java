package de.codexbella.week7security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;

   public String createUser (UserDocument user) {
      if (userRepository.findByUserName(user.getUserName()).isEmpty()) {
         userRepository.save(user);
         return "New user created with Username: "+user.getUserName();
      }
      return "User already exists.";
   }
   public Optional<UserDocument> findByEmail(String userName) {
      return userRepository.findByUserName(userName);
   }
}
