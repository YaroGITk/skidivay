package ru.skidivay.activity.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skidivay.activity.model.UserActivity;
import ru.skidivay.activity.repo.UserActivityRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity")
public class ActivityController {
  private final UserActivityRepository repo;
  public ActivityController(UserActivityRepository repo) { this.repo = repo; }

  @GetMapping
  public List<UserActivity> list(Authentication a) {
    return repo.findByTgIdOrderByActivityTimeDesc(a.getName());
  }

  @PostMapping
  public UserActivity create(Authentication a, @RequestBody Map<String,Object> body) {
    var ua = new UserActivity();
    ua.setTgId(a.getName());
    ua.setActivityType((String) body.getOrDefault("activityType","session"));
    ua.setActivityTime(Instant.now());
    ua.setMetadata((Map<String,Object>) body.get("metadata"));
    return repo.save(ua);
  }
}
