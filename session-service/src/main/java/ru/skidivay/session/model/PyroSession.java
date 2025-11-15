package ru.skidivay.session.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("pyro_sessions")
@CompoundIndex(name="by_status", def="{ 'status':1 }")
public class PyroSession {
  @Id private String id;
  @Indexed(unique=true) private String name;
  private String status; // idle|busy|error
  private Instant lastUsed;
  private Long requestCount;
  private String lastError;
  private Boolean isAlive = true;

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
  public Instant getLastUsed() { return lastUsed; }
  public void setLastUsed(Instant lastUsed) { this.lastUsed = lastUsed; }
  public Long getRequestCount() { return requestCount; }
  public void setRequestCount(Long requestCount) { this.requestCount = requestCount; }
  public String getLastError() { return lastError; }
  public void setLastError(String lastError) { this.lastError = lastError; }
  public Boolean getIsAlive() { return isAlive; }
  public void setIsAlive(Boolean isAlive) { this.isAlive = isAlive; }
}
