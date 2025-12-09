package com.example.models;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id @GeneratedValue
    private Integer id;    

    private Double price;

    @PastOrPresent
    private Date date;
    
    
    private String  currency;

    private String type;

    @ManyToOne
    private User user;


    public Transaction() {}

    public Transaction(Double price, Date date, String currency, String type) {
        this.price = price;
        this.date = date;
        this.currency = currency;
        this.type = type;
    }

    public Integer getId() { return this.id; }
    public Double getPrice() { return this.price; }
    public Date getDate() { return this.date; }
    public String getCurrency() { return this.currency; }
    public String getType() { return this.type; }
    public User getUser() { return user; }

    public void setId(Integer id) { this.id = id; }
    public void setPrice(Double price) { this.price = price; }
    public void setDate(Date date) { this.date = date; }
    public void setCurrency(String currency) { this.currency = currency;}
    public void setType(String type) { this.type = type;}
    public void setUser(User user) { this.user = user; }

}
