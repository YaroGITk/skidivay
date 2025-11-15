package ru.skidivay.activity.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skidivay.activity.model.UserActivity;
import java.util.List;

public interface UserActivityRepository extends MongoRepository<UserActivity, String> {
  List<UserActivity> findByTgIdOrderByActivityTimeDesc(String tgId);
}
