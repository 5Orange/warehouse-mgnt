package com.mgnt.warehouse.controller;

import com.mgnt.warehouse.modal.common.MetricSearch;
import com.mgnt.warehouse.modal.request.CreateOrderRequest;
import com.mgnt.warehouse.modal.response.SuccessResponse;
import com.mgnt.warehouse.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("create")
    public ResponseEntity<SuccessResponse> createAnOrder(@RequestBody @Valid CreateOrderRequest orderRequest) {
        var orderId = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(SuccessResponse.builder().data("order id: " + orderId).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getOrder(@PathVariable("id") String orderId) {
        return ResponseEntity.ok(SuccessResponse.builder()
                .data(orderService.getOrderById(orderId))
                .build());
    }

    @PostMapping("list")
    @Operation(description = "metricFilters.filterField => valid values: [productCode, customerName, createDate]",
            parameters = @Parameter(example = """
                    {
                      "sortField": "customerName",
                      "desc": true,
                      "pageSize": 0,
                      "pageNumber": 10,
                      "metricFilters": [
                        {
                          "filterField": "string",
                          "from": "2025-02-04T15:39:35.978Z",
                          "to": "2025-02-04T15:39:35.978Z"
                        },
                        {
                          "filterField": "customerName",
                          "value": "name"
                        },
                        {
                          "filterField": "productCode",
                          "value": "code"
                        }
                      ]
                    }
                    """)
    )
    public ResponseEntity<SuccessResponse> getListOrder(@RequestBody MetricSearch metricSearch) {
        return ResponseEntity.ok(SuccessResponse.builder().data(orderService.getListOrder(metricSearch)).build());
    }
}
