package com.example.grocerystore.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Admin {
    private Long id;

    @NotEmpty(message = "adminname cannot be empty.")
    @Size(min = 5, max = 50)
    private String adminname;

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 5, max = 50)
    private String password;

    @NotEmpty(message = "email cannot be empty.")
    @Size(min = 5, max = 50)
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotEmpty(message = "phone cannot be empty.")
    @Size(min = 5, max = 10)
    private String phone;
}