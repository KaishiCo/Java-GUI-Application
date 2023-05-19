package payload.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskArrayResponse {    //this class handles the array that is returned from the API's task endpoint
    Task[] tasks;   //array that will hold tasks
}
