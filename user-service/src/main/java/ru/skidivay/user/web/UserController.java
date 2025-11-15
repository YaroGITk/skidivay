package ru.skidivay.user.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skidivay.user.model.User;
import ru.skidivay.user.repo.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserRepository repo;
  public UserController(UserRepository repo) { this.repo = repo; }

  @GetMapping("/me")
  public User me(Authentication auth) {
    return repo.findById(auth.getName()).orElseThrow();
  }

  @PatchMapping("/me")
  public User update(Authentication auth, @RequestBody Map<String,Object> patch) {
    var u = repo.findById(auth.getName()).orElseThrow();
    if (patch.containsKey("plan")) u.setPlan((String) patch.get("plan"));
    return repo.save(u);
  }
}
