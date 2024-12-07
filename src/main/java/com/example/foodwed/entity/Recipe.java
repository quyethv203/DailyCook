package com.example.foodwed.entity;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Getter
@Setter
public class Recipe {
    @Id

    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String ingredien;
    private String description;
    private String name;
    private String step;
    private String image;
    private int time;
    private int serves;
    private int price;

}
