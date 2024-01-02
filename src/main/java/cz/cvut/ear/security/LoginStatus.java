package cz.cvut.ear.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginStatus {
    private boolean loggedIn;
    private String username;
    private String errorMessage;
    private boolean success;


    public LoginStatus(boolean loggedIn, boolean success, String username, String errorMessage) {
        this.loggedIn = loggedIn;
        this.username = username;
        this.errorMessage = errorMessage;
        this.success = success;
    }
}
