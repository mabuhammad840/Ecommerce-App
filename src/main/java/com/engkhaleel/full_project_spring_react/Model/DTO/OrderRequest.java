package com.engkhaleel.full_project_spring_react.Model.DTO;

import java.util.List;

public record OrderRequest(
        // the data will submit from user in the clint-side
        String customerName,
        String email,
        List<OrderItemRequest> items
) {
}
