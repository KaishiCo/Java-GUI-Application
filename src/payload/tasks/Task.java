package payload.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public class Task {     //Class to store the response of a task retrieved from the API
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("description")
    private String description;
    @JsonProperty("userId")
    private UUID userId;
    @JsonProperty("isCompleted")
    private boolean isCompleted;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription(){
        return description;
    }

    public UUID getUserId(){
        return userId;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }
}
