package com.example.models;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "connection_histories")
public class ConnectionHistory {

    @Id @GeneratedValue
    private Integer id;    

    @PastOrPresent
    private LocalDate connection_date;

    private String  status;

    @ManyToOne
    private User user;

    public ConnectionHistory() {}

    public ConnectionHistory(Long id_User, LocalDate connection_date, String status) {
        this.connection_date = connection_date;
        this.status = status;
    }

    public Integer getId() { return this.id; }
    public LocalDate getConnectionDate() { return this.connection_date; }
    public String getStatus() { return this.status; }
    public User getUser() { return this.user; }
    
    public void setId(Integer id) { this.id = id; }
    public void setConnectionDate(LocalDate connection_date) { this.connection_date = connection_date; }
    public void setStatus(String status) { this.status = status;}
    public void setUser(User user) { this.user = user; }
}
