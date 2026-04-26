package com.engkhaleel.full_project_spring_react.Service;


import com.engkhaleel.full_project_spring_react.Model.DTO.OrderItemRequest;
import com.engkhaleel.full_project_spring_react.Model.DTO.OrderItemResponse;
import com.engkhaleel.full_project_spring_react.Model.DTO.OrderRequest;
import com.engkhaleel.full_project_spring_react.Model.DTO.OrderResponse;
import com.engkhaleel.full_project_spring_react.Model.Order;
import com.engkhaleel.full_project_spring_react.Model.OrderItem;
import com.engkhaleel.full_project_spring_react.Model.Product;
import com.engkhaleel.full_project_spring_react.Repo.OrderRepo;
import com.engkhaleel.full_project_spring_react.Repo.RepoProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {
    private OrderRepo orderRepo;
    private RepoProduct repoProduct;

    public OrderService(OrderRepo orderRepo, RepoProduct repoProduct) {
        this.orderRepo = orderRepo;
        this.repoProduct = repoProduct;
    }

    public OrderResponse placeOrder(OrderRequest request) {
        // now I need to formating order responce to send it to clint side
        // to deal with orderRequest I need to create object of order to set values inside Order Object :
        Order order = new Order();

        // I need to generate unique id for each order :
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.setOrderId(orderId);
        order.setCustomerName(request.customerName());
        order.setEmail(request.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        // now should be create a list of orderItems :
        // we wil talk a values from request
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.items()) {
            // I need to set id product for items before this i need to check if the item exist..
            Product product = repoProduct.findById(itemRequest.productId()).orElseThrow(
                    () -> new RuntimeException("Product not found"));

            // I need to update quantity for the product
            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());
            repoProduct.save(product);

            // I will create a OrderItem by using a builder pattern
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())))
                    .order(order)
                    .build();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepo.save(order);

        // I need to create OrderItemResponse to insert inside a OrderResponse constructor
        List<OrderItemResponse> itemResponses = new ArrayList<>();
        for (OrderItem item : order.getOrderItems()) {
            OrderItemResponse orderItemResponse = new OrderItemResponse(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getTotalPrice()
            );
            itemResponses.add(orderItemResponse);
        }

        OrderResponse orderResponse = new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCustomerName(),
                savedOrder.getEmail(),
                savedOrder.getStatus(),
                savedOrder.getOrderDate(),
                itemResponses
        );

        return orderResponse;
    }

    public List<OrderResponse> getAllOrderResponses() {
        // to doing this logic you need to fetch all orders from database
        List<Order> orders = orderRepo.findAll();

        // should be return a OrderResponse object
        List<OrderResponse> orderResponses = new ArrayList<>();

        // we will do some operation inside this ArrayList
        for (Order order : orders) {
            /* for each object as order response will save inside array list but , i will passing values inside a constructor */

            // I need to create a OrderItemResponse to insert inside a OrderResponse constructor
            List<OrderItemResponse> itemResponses = new ArrayList<>();
            for (OrderItem item : order.getOrderItems()) {
                OrderItemResponse orderItemResponse = new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                itemResponses.add(orderItemResponse);
            }

            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            );
            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }
}