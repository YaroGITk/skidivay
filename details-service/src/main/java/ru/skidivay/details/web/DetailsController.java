package ru.skidivay.details.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skidivay.details.model.SavedDetails;
import ru.skidivay.details.repo.SavedDetailsRepository;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/details")
public class DetailsController {
  private final SavedDetailsRepository repo;
  public DetailsController(SavedDetailsRepository repo) { this.repo = repo; }

  @GetMapping
  public List<SavedDetails> list(Authentication a){ return repo.findByTgId(a.getName()); }

  @PostMapping
  public SavedDetails create(Authentication a, @RequestBody SavedDetails dto){
    dto.setId(null);
    dto.setTgId(a.getName());
    dto.setCreatedAt(Instant.now());
    dto.setUpdatedAt(Instant.now());
    return repo.save(dto);
  }
}
