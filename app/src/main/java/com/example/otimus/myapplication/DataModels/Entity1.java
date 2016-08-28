package com.example.otimus.myapplication.DataModels;

import java.io.Serializable;

/**
 * Created by Otimus on 8/8/2016.
 */
public class Entity1 implements Serializable{
    private int id;
    private String username;
    private String comment;
    private int news_id;

    public Entity1(int id, String username, String comment,int news_id) {
        this.id = id;
        this.username = username;
        this.comment = comment;
        this.news_id=news_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }
}
