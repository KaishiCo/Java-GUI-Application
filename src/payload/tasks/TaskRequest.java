package payload.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TaskRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("date")
    private String date;
    @JsonProperty("description")
    private String description;

    public TaskRequest(String name, String date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }
}
