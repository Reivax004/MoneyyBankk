package com.example.models;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id @GeneratedValue
    private Long id;    

    private Double price;
    private Date date;
    private String  currency;
    private String type;

    public Transaction() {}

    public Transaction(Double price, Date date, String currency, String type) {
        this.price = price;
        this.date = date;
        this.currency = currency;
        this.type = type;
    }

    public Long getId() { return this.id; }
    public Double getPrice() { return this.price; }
    public Date getDate() { return this.date; }
    public String getCurrency() { return this.currency; }
    public String getType() { return this.type; }
    
    public void setId(Long id) { this.id = id; }
    public void setPrice(Double price) { this.price = price; }
    public void setDate(Date date) { this.date = date; }
    public void setCurrency(String currency) { this.currency = currency;}
    public void setType(String type) { this.type = type;}
}
