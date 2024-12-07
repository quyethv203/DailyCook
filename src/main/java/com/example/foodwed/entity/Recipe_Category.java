package com.example.foodwed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Recipe_Category {

    @EmbeddedId
    private RecipeCategoryId id;

    @ManyToOne
    @MapsId("recipeid") // Ánh xạ khóa chính "recipeid" từ RecipeCategoryId
    @JoinColumn(name = "recipeid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recipe recipe;

    @ManyToOne
    @MapsId("categoryid") // Ánh xạ khóa chính "categoryid" từ RecipeCategoryId
    @JoinColumn(name = "categoryid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    // Các thuộc tính khác nếu cần

    public RecipeCategoryId getId() {
        return id;
    }

    public void setId(RecipeCategoryId id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
