package ru.skidivay.invoice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.skidivay.invoice.model.InvoiceReceiver;
import java.util.List;

public interface InvoiceReceiverRepository extends MongoRepository<InvoiceReceiver, String> {
  List<InvoiceReceiver> findByInvoiceId(String invoiceId);
}
