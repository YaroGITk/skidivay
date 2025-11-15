package ru.skidivay.auth.repositories;

import ru.skidivay.auth.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> { }
