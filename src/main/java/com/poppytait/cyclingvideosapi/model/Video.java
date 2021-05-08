package com.poppytait.cyclingvideosapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    private int id;
    private String title;
    private Instant date;

    public Video() {
    }

    public Video(int id, String title, Instant date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Instant getDate() {
        return date;
    }
}
