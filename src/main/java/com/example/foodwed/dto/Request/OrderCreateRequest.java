package com.example.foodwed.dto.Request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Getter
@Setter
public class OrderCreateRequest {

    private String uid;
    private String recipeid;
    private String recipename;
    private String name;
    private String address;
    private int phone;
    private String ingredien;
    private int price;
    private int quantity;
    private int totalPrice;
    private LocalDateTime orderTime;
    private boolean isactive;
}
