package com.example.foodwed.dto.response;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> content; // Danh sách bản ghi trong trang
    private int pageNumber;  // Số trang hiện tại
    private int pageSize;    // Số bản ghi trên mỗi trang
    private long totalElements; // Tổng số bản ghi
    private int totalPages;  // Tổng số trang
    private boolean isLast;  // Có phải trang cuối cùng hay không
}
