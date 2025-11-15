package ru.skidivay.hunted.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("hunted_base")
public class Hunted {
  @Id private String tgId;
  @Indexed(unique = true, sparse = true) private String tgUsername;
  private String tgFirstName;
  private String tgLastName;
  private String tgPfp;
  private String tgPfpId;
  private Instant addedAt;

  public String getTgId() { return tgId; }
  public void setTgId(String tgId) { this.tgId = tgId; }
  public String getTgUsername() { return tgUsername; }
  public void setTgUsername(String tgUsername) { this.tgUsername = tgUsername; }
  public String getTgFirstName() { return tgFirstName; }
  public void setTgFirstName(String tgFirstName) { this.tgFirstName = tgFirstName; }
  public String getTgLastName() { return tgLastName; }
  public void setTgLastName(String tgLastName) { this.tgLastName = tgLastName; }
  public String getTgPfp() { return tgPfp; }
  public void setTgPfp(String tgPfp) { this.tgPfp = tgPfp; }
  public String getTgPfpId() { return tgPfpId; }
  public void setTgPfpId(String tgPfpId) { this.tgPfpId = tgPfpId; }
  public Instant getAddedAt() { return addedAt; }
  public void setAddedAt(Instant addedAt) { this.addedAt = addedAt; }
}
