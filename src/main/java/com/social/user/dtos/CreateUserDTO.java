package com.social.user.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class CreateUserDTO {

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d).{4,8}$", message = " Password must be between 4 and 8 digits long and include at least one numeric digit.")
    private String password;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    private LocalDate birthday;
}
