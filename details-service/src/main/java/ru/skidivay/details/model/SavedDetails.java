package ru.skidivay.details.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document("saved_details")
@CompoundIndex(name="by_user_type", def="{ 'tgId': 1, 'type': 1 }")
public class SavedDetails {
  @Id private String id;
  private String tgId;
  private String type; // bank|sbp|wallet|crypto
  private Map<String,Object> details;
  private Instant createdAt;
  private Instant updatedAt;

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getTgId() { return tgId; }
  public void setTgId(String tgId) { this.tgId = tgId; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public Map<String, Object> getDetails() { return details; }
  public void setDetails(Map<String, Object> details) { this.details = details; }
  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
  public Instant getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
