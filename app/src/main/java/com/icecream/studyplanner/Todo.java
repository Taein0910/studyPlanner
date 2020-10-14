package com.icecream.studyplanner;

public class Todo {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return content;
    }

    public void setDescription(String content) {
        this.content = content;
    }

    public Todo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
