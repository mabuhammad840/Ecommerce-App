package com.engkhaleel.full_project_spring_react.Model.DTO;

public record OrderItemRequest(
        // this information will transferring within order request ..
        int productId,
        int quantity
) {}

