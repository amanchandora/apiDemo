package com.apidemo.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto {

    private Long id;
    @NotNull(message = "can not be null")
    @Size(min=2,max=10,message="should be between 2 to 10 characters")
    private String name;
    @Email(message="invalid email address")
    private String emailId;
    @NotNull(message="can not be null")
    @Size(min=10,max=10,message="should be exactly 10 digit")
    private String mobile;


}
