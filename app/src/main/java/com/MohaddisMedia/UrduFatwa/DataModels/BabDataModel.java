package com.MohaddisMedia.UrduFatwa.DataModels;

import java.io.Serializable;

public class BabDataModel extends KutubDataModel implements Serializable {
    int jild_Id;
    int question_count;
    String jild_Title;
    int jild_parent_id;
    int book_id;

    public BabDataModel(int jild_Id, int question_count, String jild_Title, int jild_parent_id, int book_id) {
        this.jild_Id = jild_Id;
        this.question_count = question_count;
        this.jild_Title = jild_Title;
        this.jild_parent_id = jild_parent_id;
        this.book_id = book_id;
    }

    public BabDataModel() {
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

    public String getJild_Title() {
        return jild_Title;
    }

    public void setJild_Title(String jild_Title) {
        this.jild_Title = jild_Title;
    }

    public int getJild_parent_id() {
        return jild_parent_id;
    }

    public void setJild_parent_id(int jild_parent_id) {
        this.jild_parent_id = jild_parent_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
}
