package com.bbrjava.ProductService.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
@Data
@Builder
public class DeleteProductCommand {
    @TargetAggregateIdentifier
    private String productId;

    public DeleteProductCommand(String productId){
        this.productId=productId;
    }

}
