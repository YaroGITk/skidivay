package ru.skidivay.session.web;

import org.springframework.web.bind.annotation.*;
import ru.skidivay.session.model.PyroSession;
import ru.skidivay.session.repo.PyroSessionRepository;

import java.time.Instant;

@RestController
@RequestMapping("/sessions")
public class SessionController {
  private final PyroSessionRepository repo;
  public SessionController(PyroSessionRepository repo) { this.repo = repo; }

  @PostMapping
  public PyroSession create(@RequestBody PyroSession s){
    if (s.getLastUsed()==null) s.setLastUsed(Instant.now());
    if (s.getRequestCount()==null) s.setRequestCount(0L);
    if (s.getStatus()==null) s.setStatus("idle");
    if (s.getIsAlive()==null) s.setIsAlive(true);
    return repo.save(s);
  }
}
