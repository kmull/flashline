package pl.flashline.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Email jest wymagany")
        @Email(message = "Nieprawidłowy format email")
        String email,

        @NotBlank(message = "Hasło jest wymagane")
        @Size(message = "Hasło musi mieć conajmniej 8 znaków")
        String password
) {


}
