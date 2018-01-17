package com.netcracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class PublishingHouse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publishinghouse_id")
    private Long id;
    private String publishingHouseInfo;
    @OneToMany(mappedBy = "publishingHouse",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Book> books;

    public PublishingHouse() {
    }

    public Long  getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublishingHouseInfo() {
        return publishingHouseInfo;
    }

    public void setPublishingHouseInfo(String publishingHouseInfo) {
        this.publishingHouseInfo = publishingHouseInfo;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
