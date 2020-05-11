package com.MohaddisMedia.UrduFatwa.DataModels;

public class JildDataModel extends KutubDataModel {
    int jild_Id;
    int question_count;
    String jild_title;
    int jildparent_id;
    int hasParts;
    int no_of_parts;
    String bookImage;
    boolean hasJilds;

    public JildDataModel(int jild_Id, int question_count, String jild_title, int jildparent_id, int hasParts, int no_of_parts, String bookImage, boolean hasJilds) {
        this.jild_Id = jild_Id;
        this.question_count = question_count;
        this.jild_title = jild_title;
        this.jildparent_id = jildparent_id;
        this.hasParts = hasParts;
        this.no_of_parts = no_of_parts;
        this.bookImage = bookImage;
        this.hasJilds = hasJilds;
    }

    public JildDataModel() {
    }

    public int getJild_Id() {
        return jild_Id;
    }

    public void setJild_Id(int jild_Id) {
        this.jild_Id = jild_Id;
    }

    @Override
    public int getQuestion_count() {
        return question_count;
    }

    @Override
    public void setQuestion_count(int question_count) {
        this.question_count = question_count;
    }

    public String getJild_title() {
        return jild_title;
    }

    public void setJild_title(String jild_title) {
        this.jild_title = jild_title;
    }

    public int getJildparent_id() {
        return jildparent_id;
    }

    public void setJildparent_id(int jildparent_id) {
        this.jildparent_id = jildparent_id;
    }

    @Override
    public int getHasParts() {
        return hasParts;
    }

    @Override
    public void setHasParts(int hasParts) {
        this.hasParts = hasParts;
    }

    @Override
    public int getNo_of_parts() {
        return no_of_parts;
    }

    @Override
    public void setNo_of_parts(int no_of_parts) {
        this.no_of_parts = no_of_parts;
    }

    @Override
    public String getBookImage() {
        return bookImage;
    }

    @Override
    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    @Override
    public boolean isHasJilds() {
        return hasJilds;
    }

    @Override
    public void setHasJilds(boolean hasJilds) {
        this.hasJilds = hasJilds;
    }
}
