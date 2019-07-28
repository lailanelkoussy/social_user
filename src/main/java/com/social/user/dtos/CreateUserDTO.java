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
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^+=!*()@%&]).{8,10}$", message = " Password must be between 8 digits or longer and include at least one numeric digit, one uppercase, one lower case and one special character.")
    private String password;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    private LocalDate birthday;
}
