package models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
        @Size(min=16, max=50, message="Token size must be between 16 and 32 characters")
        @NotBlank(message = "Refresh token cannot be empty")
        String refreshToken
) {

}
