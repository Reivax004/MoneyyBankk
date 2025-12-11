package com.example.models;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "connection_histories")
public class ConnectionHistory {

    @Id @GeneratedValue
    private Integer id;    

    @PastOrPresent
    private Date connection_date;

    private String  status;

    @ManyToOne
    private User user;

    public ConnectionHistory() {}

    public ConnectionHistory(Long id_User, Date connection_date, String status) {
        this.connection_date = connection_date;
        this.status = status;
    }

    public Integer getId() { return this.id; }
    public Date getConnectionDate() { return this.connection_date; }
    public String getStatus() { return this.status; }
    public User getUser() { return this.user; }
    
    public void setId(Integer id) { this.id = id; }
    public void setConnectionDate(Date connection_date) { this.connection_date = connection_date; }
    public void setStatus(String status) { this.status = status;}
    public void setUser(User user) { this.user = user; }
}
