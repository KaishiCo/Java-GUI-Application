package payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginCredential {  //class that is turned into Json to be sent to the login API
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public LoginCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }
}