package com.bbrjava.ProductService.command.api.events;

import com.bbrjava.ProductService.command.api.data.Product;
import com.bbrjava.ProductService.command.api.data.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Component
@ProcessingGroup("product")
public class ProductEventHandler {
    private final ProductRepository productRepository;

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception {
    Product product = new Product();
        BeanUtils.copyProperties(event, product);
        productRepository.save(product);
        //throw new Exception("Exception occured");
    }

    @ExceptionHandler
    public void handle(Exception e) throws Exception {
        throw e;
    }

    @EventHandler
    public void on(ProductUpdatedEvent event){

            Optional<Product> product = productRepository.findByName(event.getName());
            product.get().setPrice(event.getPrice());
            product.get().setQuantity(event.getQuantity());

            productRepository.save(product.get());
    }

    @EventHandler
    public void on(ProductDeletedEvent event){
        Optional<Product> product = productRepository.findById(event.getProductId());
        productRepository.delete(product.get());
    }


}
