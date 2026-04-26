package com.engkhaleel.full_project_spring_react.Model;
// we will using a lombok notaitons to save in the database

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne //-> every item represent for specific product <-\\ \\ //-> one product it can be a many items <-\\ .
    private Product product;
    private int quantity;
    // to calculate the total price  items for the one product
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    //-> one order have a Many item <-\\
    private Order order; //(One from Order) and (Many from Item)

}
