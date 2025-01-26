package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;

@With
public record CreateUserRequest(
        @Schema(description = "Name", example = "José da Silva")
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 3, max=50, message ="Name must contain between 3 and 50 characters" )
        String name,
        @Schema(description = "E-mail", example = "user@mail.com")
        @Email(message = "Invalid e-mail")
        @NotBlank(message = "E-mail cannot be empty")
        @Size(min = 6, max = 40, message = "Invalid e-mail (size)")
        String email,
        @Schema(description = "Password", example = "1rfsvo3@1")
        @Size(min=6, max = 10, message = "Password must contain between 6 and 10 characters")
        String password,
        @Schema(description = "User profiles", example = "[\"ROLE_ADMIN\", \"ROLE_CUSTOMER(default)\", \"ROLE_TECHNICIAN\"]")
        Set<ProfileEnum> profiles
) {}
