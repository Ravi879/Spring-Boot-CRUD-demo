package com.thinkitive.cruddemo.ui.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {

    @NotBlank(message = "email.not.empty")
    @Size(max = 100, message = "email.max.size")
    @Pattern(regexp = "^([\\w-.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", message = "email.invalid")
    private String email;

    @NotBlank(message = "password.not.empty")
    @Size(min = 5, max = 14, message = "password.size.between")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!#?&])[A-Za-z\\d@$!%*#?&]{5,14}$", message = "password.invalid")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
