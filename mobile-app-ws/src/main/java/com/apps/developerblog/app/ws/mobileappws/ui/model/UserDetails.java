package com.apps.developerblog.app.ws.mobileappws.ui.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDetails {

    private String userId;

    @NotNull(message = "First name cannot be null")
    @Size(min=2, message = "First name should not be less than 2 characters.")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min=2, message = "Last name should not be less than 2 characters.")
    private String lastName;

    @Email
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 16, message = "Password must be greater than 8 and less than 16 characters")
    private String password;

}
