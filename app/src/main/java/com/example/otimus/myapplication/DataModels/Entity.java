package com.example.otimus.myapplication.DataModels;

import java.io.Serializable;

/**
 * Created by Otimus on 8/4/2016.
 */
public class Entity implements Serializable {
    private int id;

    private String newstitle;
    private String newsbody;
    private String images;
    private String date;
    private int category_id;

    public Entity(int id, String newstitle, String newsbody, String images, String date,int category_id) {
        this.id = id;
        this.newstitle = newstitle;
        this.newsbody = newsbody;
        this.images = images;
        this.date = date;
        this.category_id=category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getNewsbody() {
        return newsbody;
    }

    public void setNewsbody(String newsbody) {
        this.newsbody = newsbody;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}

