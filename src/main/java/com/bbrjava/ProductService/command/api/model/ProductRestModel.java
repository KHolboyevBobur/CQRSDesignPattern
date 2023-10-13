package com.bbrjava.ProductService.command.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRestModel {
    @NotBlank
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
