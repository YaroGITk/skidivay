package ru.skidivay.auth.services;

import org.springframework.stereotype.Service;
import ru.skidivay.auth.models.User;
import ru.skidivay.auth.repositories.UserRepository;
import ru.skidivay.auth.security.telegram.WebAppUser;

import java.time.Instant;


@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOrCreateByTelegramUser(WebAppUser tgUser) {
        String id = String.valueOf(tgUser.getId());
        User user = userRepository.findById(id).orElseGet(User::new);

        user.setTgId(id);
        user.setTgUsername(tgUser.getUsername());
        user.setTgFirstName(tgUser.getFirstName());
        user.setTgLastName(tgUser.getLastName());

        if (user.getPlan() == null) {
            user.setPlan("free");
        }
        if (tgUser.getPhotoUrl() != null) {
            user.setTgPfp(tgUser.getPhotoUrl());
        }
        if (user.getRegisteredAt() == null) {
            user.setRegisteredAt(Instant.now());
        }

        return userRepository.save(user);
    }


}
