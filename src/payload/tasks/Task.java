package payload.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {     //Class to store the response of a task retrieved from the API
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("date")
    private String date;
    @JsonProperty("desc")
    private String description;

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
}
