package de.codexbella.week7security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;

   public UserDocument createUser (UserDocument user) {
      return userRepository.save(user);
   }
   public Optional<UserDocument> findByEmail(String email) {
      return userRepository.findByEmail(email);
   }
}
