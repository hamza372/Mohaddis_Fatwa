package com.MohaddisMedia.UrduFatwa.DataModels;

public class TopicDataModel {
    int id;
    int parent_id;
    String title;
    String description;
    int question_count;
    int created_at;

    public TopicDataModel(int id, int parent_id, String title, String description, int question_count, int created_at) {
        this.id = id;
        this.parent_id = parent_id;
        this.title = title;
        this.description = description;
        this.question_count = question_count;
        this.created_at = created_at;
    }

    public TopicDataModel(int id, int parent_id, String title,  int question_count) {
        this.id = id;
        this.parent_id = parent_id;
        this.title = title;
        this.description = description;
        this.question_count = question_count;
        this.created_at = created_at;
    }

    public TopicDataModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(int question_count) {
        this.question_count = question_count;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }
}
