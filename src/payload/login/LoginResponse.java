package payload.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {    //Class to store the response from the login API
    @JsonProperty("tokenType")
    private String tokenType;
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("expiresIn")
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
