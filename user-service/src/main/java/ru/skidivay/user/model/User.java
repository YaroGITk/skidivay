package ru.skidivay.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("users")
public class User {
  @Id
  private String tgId;
  @Indexed(unique = true, sparse = true)
  private String tgUsername;
  private String tgFirstName;
  private String tgLastName;
  private String plan;
  private String tgPfp;
  private String tgPfpId;
  private Instant registeredAt;

  public String getTgId() { return tgId; }
  public void setTgId(String tgId) { this.tgId = tgId; }
  public String getTgUsername() { return tgUsername; }
  public void setTgUsername(String tgUsername) { this.tgUsername = tgUsername; }
  public String getTgFirstName() { return tgFirstName; }
  public void setTgFirstName(String tgFirstName) { this.tgFirstName = tgFirstName; }
  public String getTgLastName() { return tgLastName; }
  public void setTgLastName(String tgLastName) { this.tgLastName = tgLastName; }
  public String getPlan() { return plan; }
  public void setPlan(String plan) { this.plan = plan; }
  public String getTgPfp() { return tgPfp; }
  public void setTgPfp(String tgPfp) { this.tgPfp = tgPfp; }
  public String getTgPfpId() { return tgPfpId; }
  public void setTgPfpId(String tgPfpId) { this.tgPfpId = tgPfpId; }
  public Instant getRegisteredAt() { return registeredAt; }
  public void setRegisteredAt(Instant registeredAt) { this.registeredAt = registeredAt; }
}
