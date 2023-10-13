package com.bbrjava.ProductService.query.api.projection;

import com.bbrjava.ProductService.command.api.data.Product;
import com.bbrjava.ProductService.command.api.data.ProductRepository;
import com.bbrjava.ProductService.command.api.model.ProductRestModel;
import com.bbrjava.ProductService.query.api.queries.GetProductQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {
    private final ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> handle(GetProductQuery getProductQuery){
        List<Product> products = productRepository.findAll();

        List<ProductRestModel> productRestModels =
                products.stream().map(product -> ProductRestModel
                        .builder()
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .name(product.getName())
                        .build()).collect(Collectors.toList());

        return productRestModels;
    }
}
