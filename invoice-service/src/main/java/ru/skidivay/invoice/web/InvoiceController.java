package ru.skidivay.invoice.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skidivay.invoice.model.Invoice;
import ru.skidivay.invoice.model.InvoiceReceiver;
import ru.skidivay.invoice.repo.InvoiceReceiverRepository;
import ru.skidivay.invoice.repo.InvoiceRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

  private final InvoiceRepository invoices;
  private final InvoiceReceiverRepository receivers;

  public InvoiceController(InvoiceRepository invoices, InvoiceReceiverRepository receivers) {
    this.invoices = invoices; this.receivers = receivers;
  }

  @GetMapping
  public List<Invoice> my(Authentication a) { return invoices.findByOwnerId(a.getName()); }

  @PostMapping
  public Invoice create(Authentication a, @RequestBody Map<String,Object> payload) {
    var inv = new Invoice();
    inv.setInvoiceId(UUID.randomUUID().toString());
    inv.setOwnerId(a.getName());
    inv.setType((String) payload.getOrDefault("type","solo"));
    inv.setStatus("pending");
    Object sum = payload.get("sum");
    if (sum instanceof Number n) inv.setSum(new BigDecimal(n.toString()));
    inv.setReceiversQuantity((Integer) payload.getOrDefault("receiversQuantity", 1));
    inv.setDescription((String) payload.get("description"));
    inv.setCreatedAt(Instant.now());
    return invoices.save(inv);
  }

  @GetMapping("/{id}")
  public Invoice one(@PathVariable String id) { return invoices.findById(id).orElseThrow(); }

  @PostMapping("/{id}/receivers")
  public InvoiceReceiver addReceiver(@PathVariable String id, @RequestBody Map<String,String> body) {
    var r = new InvoiceReceiver();
    r.setInvoiceId(id);
    r.setReceiverId(body.get("receiverId"));
    r.setJoinedAt(Instant.now());
    return receivers.save(r);
  }

  @GetMapping("/{id}/receivers")
  public List<InvoiceReceiver> listReceivers(@PathVariable String id){ return receivers.findByInvoiceId(id); }
}
