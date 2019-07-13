package com.social.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.social.user.entities.User;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Data
public class UserDTO { //todo why is this class not within the DTOs ?

    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c" +
            "\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0" +
            "-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
            "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x" +
            "5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "enter valid email")
    private String email;

    private String username;

    @Pattern(regexp = "^(?=.*\\d).{4,8}$", message = " Password must be between 4 and 8 digits long and include at least one numeric digit.")
    private String password;


    @Pattern(regexp = "^[a-zA-Z]+$", message = "No numbers, special characters or spaces")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "No numbers, special characters or spaces")
    private String middleName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "No numbers, special characters or spaces")
    private String lastName;

    private LocalDateTime birthday;

    private List<Integer> followingIds;

    private int activate = -1;

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setFollowingIds(List<Integer> followingIds) {
        this.followingIds = followingIds;
    }

    @JsonIgnore
    public List<Integer> getFollowingIds() {
        return followingIds;
    }

    @JsonProperty
    public void setActivate(int activate) {
        this.activate = activate;
    }

    @JsonIgnore
    public int getActivate() {
        return activate;
    }


}
