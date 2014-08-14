package com.demo.model;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String author;

    private String publisher;

    private Date publication;

    private int price;

    private int discount;

    public Book() {
    }

    public Book(String title, String author, String publisher, Date publication, int price, int discount) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publication = publication;
        this.price = price;
        this.discount = discount;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublication() {
        return this.publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return this.discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}