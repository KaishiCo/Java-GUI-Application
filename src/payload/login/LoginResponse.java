package payload.login;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class LoginResponse {    //Class to store the response from the login API
    @JsonProperty("tokenType")
    private String tokenType;
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("expiresIn")
    private int expiresIn;
    @JsonProperty("userId")
    private UUID userId;

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public UUID getUserId() {
        return userId;
    }
}
