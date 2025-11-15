package ru.skidivay.hunted.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skidivay.hunted.model.Hunted;

public interface HuntedRepository extends MongoRepository<Hunted, String> { }
