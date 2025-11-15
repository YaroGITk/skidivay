package ru.skidivay.session.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skidivay.session.model.PyroSession;

public interface PyroSessionRepository extends MongoRepository<PyroSession, String> { }
