package com.MohaddisMedia.UrduFatwa.DataModels;

import java.io.Serializable;

public class KutubDataModel implements Serializable {
    int kitab_Id;
    int question_count;
    String kitab_title;
    int kitabparent_id;
    int hasParts;
    int no_of_parts;
    String bookImage;
    boolean hasJilds;

    public boolean isHasJilds() {
        return hasJilds;
    }

    public void setHasJilds(boolean hasJilds) {
        this.hasJilds = hasJilds;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public KutubDataModel() {
    }

    public int getKitab_Id() {
        return kitab_Id;
    }

    public void setKitab_Id(int kitab_Id) {
        this.kitab_Id = kitab_Id;
    }

    public int getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(int question_count) {
        this.question_count = question_count;
    }

    public  String getKitab_title() {
        return kitab_title;
    }

    public void setKitab_title(String kitab_title) {
        this.kitab_title = kitab_title;
    }

    public int getKitabparent_id() {
        return kitabparent_id;
    }

    public void setKitabparent_id(int kitabparent_id) {
        this.kitabparent_id = kitabparent_id;
    }

    public int getHasParts() {
        return hasParts;
    }

    public void setHasParts(int hasParts) {
        this.hasParts = hasParts;
    }

    public int getNo_of_parts() {
        return no_of_parts;
    }

    public void setNo_of_parts(int no_of_parts) {
        this.no_of_parts = no_of_parts;
    }
}
