package ru.skidivay.hunted.web;

import org.springframework.web.bind.annotation.*;
import ru.skidivay.hunted.model.Hunted;
import ru.skidivay.hunted.repo.HuntedRepository;

import java.time.Instant;

@RestController
@RequestMapping("/hunted")
public class HuntedController {
  private final HuntedRepository repo;
  public HuntedController(HuntedRepository repo) { this.repo = repo; }

  @PostMapping
  public Hunted add(@RequestBody Hunted h) {
    if (h.getAddedAt()==null) h.setAddedAt(Instant.now());
    return repo.save(h);
  }
}
