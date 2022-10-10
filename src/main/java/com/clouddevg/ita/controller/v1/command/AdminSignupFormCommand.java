package com.clouddevg.ita.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class AdminSignupFormCommand {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 5, max = 100)
    private String pilotName;

    @NotBlank
    @Size(max = 100)
    private String pilotDetails;

    @NotBlank
    @Size(min = 5, max = 13)
    private String mobileNumber;
}
