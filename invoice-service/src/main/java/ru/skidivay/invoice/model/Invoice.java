package ru.skidivay.invoice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

@Document("invoices")
@CompoundIndex(name="by_owner_status", def="{ 'ownerId':1, 'status':1 }")
public class Invoice {
  @Id private String invoiceId; // UUID string
  @Indexed private String ownerId;
  private String type; // solo|multi
  private String status; // verified|pending|waiting
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal sum;
  private Integer receiversQuantity = 1;
  private String description;
  private LocalDate deadline;
  private Map<String,Object> banks;
  private Map<String,Object> sbp;
  private Map<String,Object> wallets;
  private Map<String,Object> crypto;
  private String link;
  private Instant createdAt;
  private Instant paidAt;
  private String paidBy;

  public String getInvoiceId() { return invoiceId; }
  public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
  public String getOwnerId() { return ownerId; }
  public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public BigDecimal getSum() { return sum; }
  public void setSum(BigDecimal sum) { this.sum = sum; }
  public Integer getReceiversQuantity() { return receiversQuantity; }
  public void setReceiversQuantity(Integer receiversQuantity) { this.receiversQuantity = receiversQuantity; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public LocalDate getDeadline() { return deadline; }
  public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
  public Map<String, Object> getBanks() { return banks; }
  public void setBanks(Map<String, Object> banks) { this.banks = banks; }
  public Map<String, Object> getSbp() { return sbp; }
  public void setSbp(Map<String, Object> sbp) { this.sbp = sbp; }
  public Map<String, Object> getWallets() { return wallets; }
  public void setWallets(Map<String, Object> wallets) { this.wallets = wallets; }
  public Map<String, Object> getCrypto() { return crypto; }
  public void setCrypto(Map<String, Object> crypto) { this.crypto = crypto; }
  public String getLink() { return link; }
  public void setLink(String link) { this.link = link; }
  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
  public Instant getPaidAt() { return paidAt; }
  public void setPaidAt(Instant paidAt) { this.paidAt = paidAt; }
  public String getPaidBy() { return paidBy; }
  public void setPaidBy(String paidBy) { this.paidBy = paidBy; }
}
