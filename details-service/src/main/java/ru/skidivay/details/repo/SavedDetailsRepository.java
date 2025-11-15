package ru.skidivay.details.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skidivay.details.model.SavedDetails;
import java.util.List;

public interface SavedDetailsRepository extends MongoRepository<SavedDetails, String> {
  List<SavedDetails> findByTgId(String tgId);
}
