package com.bbrjava.ProductService.command.api.controller;

import com.bbrjava.ProductService.command.api.commands.CreateProductCommand;
import com.bbrjava.ProductService.command.api.commands.DeleteProductCommand;
import com.bbrjava.ProductService.command.api.commands.UpdateProductCommand;
import com.bbrjava.ProductService.command.api.model.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductCommandController {

    private CommandGateway commandGateway;

    public ProductCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addProduct(@RequestBody ProductRestModel productRestModel) {

       CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .quantity(productRestModel.getQuantity())
                .build();

        String result = commandGateway.sendAndWait(createProductCommand);
        return result;
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> updateProduct(@PathVariable String name, @RequestBody UpdateProductCommand updateProductCommand) {
        updateProductCommand.setName(name);
        commandGateway.send(updateProductCommand);

        return ResponseEntity.accepted().body("Product updated!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String productId){
        commandGateway.send(new DeleteProductCommand(productId));

        return ResponseEntity.accepted().body("Product deleted!");
    }

}
