package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(
        @Schema(description = "Requester ID", example = "0cd29a79-514f-482a-a5ea-9f7797d4b1ef")
        @NotBlank(message="requesterId cannot be empty or null")
        @Size(min=24, max=36, message="requesterId must be between 24 and 36 characters")
        String requesterId,
        @Schema(description = "Customer ID", example = "0cd29a79-514f-482a-a5ea-9f7797d4b1ef")
        @NotBlank(message="customerId cannot be empty or null")
        @Size(min=24, max=36, message="customerId must be between 24 and 36 characters")
        String customerId,
        @Schema(description = "Title of order service", example = "Free text")
        @NotBlank(message="title cannot be empty or null")
        @Size(min=3, max=45, message="Title must be between 3 and 45 characters")
        String title,

        @Schema(description = "Description of order", example = "Free text")
        @NotBlank(message="description cannot be empty or null")
        @Size(min=10, max=3000, message="Title must be between 10 and 3000 characters")
        String description,

        @Schema(description = "Status of order", example = "OPEN")
        @Size(min=4, max=15, message="Status must be between 4 and 15 characters")
        String status
) {
}
