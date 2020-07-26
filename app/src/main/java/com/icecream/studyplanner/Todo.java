package com.icecream.studyplanner;

public class Todo {
    private String title;
    private String description;

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

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
