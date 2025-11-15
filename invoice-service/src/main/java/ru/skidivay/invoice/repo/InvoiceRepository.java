package ru.skidivay.invoice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skidivay.invoice.model.Invoice;
import java.util.List;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
  List<Invoice> findByOwnerId(String ownerId);
}
