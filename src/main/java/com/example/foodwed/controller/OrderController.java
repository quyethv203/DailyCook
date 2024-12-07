package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.OrderCreateRequest;
import com.example.foodwed.dto.Request.OrderUpdateRequest;
import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.response.OrderResponse;
import com.example.foodwed.dto.response.PaginatedResponse;
import com.example.foodwed.entity.Orders;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Tạo đơn hàng
     */
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest order) {

        Orders newOrder = orderService.create(order);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<Orders>("success", "200", "Order created successfully", newOrder));
    }

    /**
     * Xóa đơn hàng
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new Appexception(ErrorCode.PARAM_ERROR);
        }
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<String>("success", "200", "Order deleted successfully", "delete success"));
    }

    /**
     * Cập nhật đơn hàng
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable String id, @RequestBody OrderUpdateRequest order) {

        Orders updatedOrder = orderService.update(id, order);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<Orders>("success", "200", "Order updated successfully", updatedOrder));
    }

    /**
     * Lấy danh sách đơn hàng phân trang theo trạng thái
     */
    @GetMapping
    public ResponseEntity<?> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "false") boolean isActive
    ) {
        PaginatedResponse<Orders> response = orderService.getAllPaginated(page, size, isActive);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<PaginatedResponse<Orders>>(
                        "success", "200", "Orders retrieved successfully", response
                ));
    }

    @GetMapping("/uorder/{uid}")
    public ResponseEntity<?> getAllByUid(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @PathVariable String uid
    ) {
        System.out.printf("h" + uid);
        PaginatedResponse<OrderResponse> response = orderService.getOrderByUser(page, size, uid);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiRespone<PaginatedResponse<OrderResponse>>(
                        "success", "200", "Orders retrieved successfully", response
                ));
    }
}
