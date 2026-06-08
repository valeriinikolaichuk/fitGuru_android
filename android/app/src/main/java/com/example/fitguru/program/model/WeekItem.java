package com.example.fitguru.program.model;

public class WeekItem {

    private Long id;
    private String title;
    private Integer position;

    public WeekItem(Long id, String title, Integer position) {
        this.id = id;
        this.title = title;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPosition() {
        return position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
