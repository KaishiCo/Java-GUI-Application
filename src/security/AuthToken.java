package security;

import java.util.Date;

public class AuthToken {
    private static String token;
    private static Date expiresIn;

    public static String getToken() {   //returns the token
        return token;
    }

    public static void setAccessToken(String token, long milli) {   //Sets the token and the expiration date
        AuthToken.token = token;
        milli *= 1000;  //API returns number of seconds so this turns it into milliseconds
        expiresIn = new Date(System.currentTimeMillis() + milli);
    }

    public static boolean isExpired() { //returns if the current time is after expiration time
        Date now = new Date(System.currentTimeMillis());
        return now.after(expiresIn);
    }
}
