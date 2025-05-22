package com.marciorodrigues.order_server_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.exceptions.ValidationException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "OrderController", description = "Controller responsible for managing orders")
@RequestMapping("/api/orders")
public interface  OrderController {

    @Operation(summary = "Create Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationException.class)
                    )),
    @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationException.class)
                    )),
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)
            ))
    })
    @PostMapping
    ResponseEntity<Void> save(@Valid @RequestBody final CreateOrderRequest createOrderRequest);

    @Operation(summary = "Update order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order Updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderResponse.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Order Not Found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )),
            @ApiResponse(responseCode = "500", description = "Bad Request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    ))
    })
    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> update(
            @Parameter(description = "OrderId", required = true, example = "10")
            @PathVariable(name = "id") Long orderId,
            @Parameter(description = "Update order request", required = true)
            @Valid @RequestBody UpdateOrderRequest updateOrderRequest
            );



}


