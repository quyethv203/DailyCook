package com.example.foodwed.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecipeCategoryId implements Serializable {

    private String recipeid;
    private String categoryid;

    public RecipeCategoryId() {}

    public RecipeCategoryId(String recipeid, String categoryid) {
        this.recipeid = recipeid;
        this.categoryid = categoryid;
    }

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeCategoryId that = (RecipeCategoryId) o;
        return Objects.equals(recipeid, that.recipeid) && Objects.equals(categoryid, that.categoryid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeid, categoryid);
    }
}
