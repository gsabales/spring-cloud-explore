package com.apps.developerblog.app.ws.mobileappws.ui.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdatedUserDetails {

    @NotNull(message = "First name cannot be null")
    @Size(min=2, message = "First name should not be less than 2 characters.")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min=2, message = "Last name should not be less than 2 characters.")
    private String lastName;

}
