package payload.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Task {     //Class to store the response of a task retrieved from the API
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("date")
    private String date;
    @JsonProperty("description")
    private String description;
    @JsonProperty("userId")
    private String userId;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getDescription(){
        return description;
    }

    public String getUserId(){
        return userId;
    }
}
