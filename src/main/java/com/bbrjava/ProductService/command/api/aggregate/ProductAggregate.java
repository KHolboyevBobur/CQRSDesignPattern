package com.bbrjava.ProductService.command.api.aggregate;

import com.bbrjava.ProductService.command.api.commands.CreateProductCommand;
import com.bbrjava.ProductService.command.api.commands.DeleteProductCommand;
import com.bbrjava.ProductService.command.api.commands.UpdateProductCommand;
import com.bbrjava.ProductService.command.api.events.ProductCreatedEvent;
import com.bbrjava.ProductService.command.api.events.ProductDeletedEvent;
import com.bbrjava.ProductService.command.api.events.ProductUpdatedEvent;
import com.bbrjava.ProductService.command.api.model.ProductRestModel;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;


    public ProductAggregate() {}

    @CommandHandler
    public ProductAggregate(CreateProductCommand command){
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        // 1 - obekt qiymatini 2 chi obekt qiymatiga ko`chiradi
        BeanUtils.copyProperties(command, productCreatedEvent);
        // Eventni publikovat qilish
        AggregateLifecycle.apply(productCreatedEvent);
    }
    @EventSourcingHandler
    public void on(ProductCreatedEvent event){
        this.quantity=event.getQuantity();
        this.productId=event.getProductId();
        this.price=event.getPrice();
        this.name=event.getName();
    }
    @CommandHandler
    public void on(UpdateProductCommand command){
        ProductUpdatedEvent event = new ProductUpdatedEvent();
        BeanUtils.copyProperties(command, event);
         AggregateLifecycle.apply(event);
    }
    @EventSourcingHandler
    public void on(ProductUpdatedEvent event){
        this.quantity=event.getQuantity();
        this.productId=event.getProductId();
        this.price=event.getPrice();
        this.name=event.getName();
    }

    @CommandHandler
    public void on(DeleteProductCommand command){
        AggregateLifecycle.apply(new ProductDeletedEvent(command.getProductId()));
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent event) {
        this.productId = event.getProductId();
    }

}
