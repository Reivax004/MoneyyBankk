package com.example.models;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "connection_histories")
public class ConnectionHistory {

    @Id @GeneratedValue
    private Integer id;    

    private Long id_User;
    private Date connection_date;
    private String  status;

    public ConnectionHistory() {}

    public ConnectionHistory(Long id_User, Date connection_date, String status) {
        this.id_User = id_User;
        this.connection_date = connection_date;
        this.status = status;
    }

    public Integer getId() { return this.id; }
    public Long getIdUser() { return this.id_User; }
    public Date getConnectionDate() { return this.connection_date; }
    public String getStatus() { return this.status; }
    
    public void setId(Integer id) { this.id = id; }
    public void setIdUser(Long id_User) { this.id_User = id_User; }
    public void setConnectionDate(Date connection_date) { this.connection_date = connection_date; }
    public void setStatus(String status) { this.status = status;}
}
