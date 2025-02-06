package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public record AuthenticateRequest(
        @Schema(description = "E-mail", example = "user@mail.com")
        @Email(message = "Invalid e-mail")
        @NotBlank(message = "E-mail cannot be empty")
        @Size(min = 6, max = 40, message = "Invalid e-mail (size)")
        String email,

        @Schema(description = "Password", example = "1rfsvo3@1")
        @Size(min=6, max = 10, message = "Password must contain between 6 and 10 characters")
        @NotBlank(message = "Password cannot be empty")
        String password

)implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
}
