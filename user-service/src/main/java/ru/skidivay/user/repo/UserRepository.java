package ru.skidivay.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skidivay.user.model.User;

public interface UserRepository extends MongoRepository<User, String> { }
