package com.example.models;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    private Integer id;

    private String lastname;
    private String firstname;
    private Date birthdate;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ConnectionHistory> connectionHistories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public User() {}

    public User(String lastname,String firstname, String email, Date birthdate, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.birthdate = birthdate;
        this.password = password;
    }

    public Integer getId() { return id; }
    public String getFirstName() { return lastname; }
    public String getLastName() { return firstname; }
    public String getEmail() { return email; }
    public Date getBirthdate() { return birthdate; }
    public Double getMoyenne(){
        //TODO
        return null; }
    public Double getEcartType(){
        //TODO
        return null; }
    public void setId(Integer id) { this.id = id; }
    public void setLastName(String lastname) { this.lastname = lastname; }
    public void setFirstName(String firstname) { this.firstname = firstname; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password;}
}
