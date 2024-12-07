package com.example.foodwed.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class CategoryCreateRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryCreateRequest(String name) {
        this.name = name;
    }

    public CategoryCreateRequest() {
    }
}
