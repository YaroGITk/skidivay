package ru.skidivay.invoice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("invoice_receivers")
@CompoundIndex(name="pk_invoice_receiver", def="{ 'invoiceId':1, 'receiverId':1 }", unique = true)
public class InvoiceReceiver {
  @Id private String id;
  private String invoiceId;
  private String receiverId;
  private Boolean accepted = false;
  private Instant joinedAt;

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getInvoiceId() { return invoiceId; }
  public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
  public String getReceiverId() { return receiverId; }
  public void setReceiverId(String receiverId) { this.receiverId = receiverId; }
  public Boolean getAccepted() { return accepted; }
  public void setAccepted(Boolean accepted) { this.accepted = accepted; }
  public Instant getJoinedAt() { return joinedAt; }
  public void setJoinedAt(Instant joinedAt) { this.joinedAt = joinedAt; }
}
