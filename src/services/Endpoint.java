package services;

public interface Endpoint { //interface for location of different endpoints
    String LOGIN = "http://localhost:7000/api/auth/login";
    String TASKS = "http://localhost:7000/api/taskItems";
}
