package com.paintingscollectors.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDto {

    @NotNull
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;

    @NotNull
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String confirmPassword;

    public @NotNull @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!") String username) {
        this.username = username;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotNull @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!") String password) {
        this.password = password;
    }

    public @NotNull @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotNull @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
