package com.example.foodwed.dto.Request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RecipeCreateRequest {

    private String ingredien;
    private String description;
    private String name;
    private String step;
    private String image;
    private int time;
    private int serves;
    private int price;
    private List<String> categoryids;

}
