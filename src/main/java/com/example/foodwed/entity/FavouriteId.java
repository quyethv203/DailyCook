package com.example.foodwed.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavouriteId implements Serializable {

    private String userid;
    private String recipeid;

    public FavouriteId() {}

    public FavouriteId(String userid, String recipeid) {
        this.userid = userid;
        this.recipeid = recipeid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    // Override equals và hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouriteId that = (FavouriteId) o;
        return Objects.equals(userid, that.userid) && Objects.equals(recipeid, that.recipeid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, recipeid);
    }
}
