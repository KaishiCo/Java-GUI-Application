package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import payload.login.LoginResponse;
import payload.login.LoginCredential;
import payload.tasks.Task;
import payload.tasks.TaskRequest;
import security.AuthToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class AuthService {
    private ObjectMapper mapper;

    public boolean requestLogin(LoginCredential login) {    //this method attempts to login to the service (as of right now it just shows a box if successful)
        mapper = new ObjectMapper();

        try {
            String loginrequest = mapper.writeValueAsString(login); //turns the login credential into Json to be sent to the API

            HttpClient client = HttpClient.newHttpClient();     //creates an instance of the client

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(Endpoint.LOGIN))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(loginrequest))
                    .build();   //builds a post request to the login portion of the API

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //stores the response from the API

            if (response.statusCode() == 401) { //401 status code means login is not authorized
                return false;
            }
            else {  //else returns a 200 status code in which case the response is parsed back into a Java class and the AuthToken is stored in memory
                LoginResponse loginResponse = mapper.readValue(response.body(), LoginResponse.class);
                AuthToken.setAccessToken(loginResponse.getAccessToken(), loginResponse.getExpiresIn());
                AuthToken.setUserId(loginResponse.getUserId());
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Task[] requestTasks() {
        mapper = new ObjectMapper();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Endpoint.TASKS))
                .header("Authorization", "Bearer " + AuthToken.getToken())
                .build();   //Get request to the API; requires Authorization , Bearer {Token} key-value pair

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 401) { //401 indicates unauthorized token (right now just returns null and isn't handled)
                return mapper.readValue(response.body(), Task[].class);
            }
        }
        catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteTask(UUID taskId) {    //call to delete the task
        mapper = new ObjectMapper();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Endpoint.TASKS + "/" + taskId))
                .header("Authorization", "Bearer " + AuthToken.getToken())
                .DELETE()
                .build();   //builds delete request

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) { //200 represents OK to delete
                return true;
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editTask(TaskRequest task, UUID taskId) {    //calls a PUT request to API to edit task
        mapper = new ObjectMapper();

        try {
            String taskrequest = mapper.writeValueAsString(task);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(Endpoint.TASKS + "/" + taskId))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthToken.getToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(taskrequest))
                    .build();   //building put request

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return true;
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addTask(TaskRequest task) {  //calls a POST request to API to add a task
        mapper = new ObjectMapper();

        try {
            String taskrequest = mapper.writeValueAsString(task);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(Endpoint.TASKS))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + AuthToken.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(taskrequest))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                return true;
            }
            else {
                System.out.println(response.statusCode());
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
