package com.MohaddisMedia.UrduFatwa.DataModels;

import java.io.Serializable;

public class FatwaDataModel extends BabDataModel implements Serializable {
    int id;
    int fatwa_no;
    String question;
    String answer;
    String type;
    int is_important;
    int is_miscellaneous;
    String creation_date;
    int view_count;


    public FatwaDataModel(int id, int fatwa_no, String question, String answer, String type, int is_important, int is_miscellaneous) {
        this.id = id;
        this.fatwa_no = fatwa_no;
        this.question = question;
        this.answer = answer;
        this.type = type;
        this.is_important = is_important;
        this.is_miscellaneous = is_miscellaneous;
    }

    public FatwaDataModel() {
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }


    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFatwa_no() {
        return fatwa_no;
    }

    public void setFatwa_no(int fatwa_no) {
        this.fatwa_no = fatwa_no;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIs_important() {
        return is_important;
    }

    public void setIs_important(int is_important) {
        this.is_important = is_important;
    }

    public int getIs_miscellaneous() {
        return is_miscellaneous;
    }

    public void setIs_miscellaneous(int is_miscellaneous) {
        this.is_miscellaneous = is_miscellaneous;
    }
}
