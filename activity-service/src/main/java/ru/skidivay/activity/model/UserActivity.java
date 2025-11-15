package ru.skidivay.activity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document("user_activity")
@CompoundIndex(name="by_user_time", def="{ 'tgId':1, 'activityTime':-1 }")
public class UserActivity {
  @Id private String id;
  @Indexed private String tgId;
  private String activityType; // "session" by default (в контроллере)
  private Instant activityTime; // now() default
  private Map<String,Object> metadata;

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getTgId() { return tgId; }
  public void setTgId(String tgId) { this.tgId = tgId; }
  public String getActivityType() { return activityType; }
  public void setActivityType(String activityType) { this.activityType = activityType; }
  public Instant getActivityTime() { return activityTime; }
  public void setActivityTime(Instant activityTime) { this.activityTime = activityTime; }
  public Map<String, Object> getMetadata() { return metadata; }
  public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
