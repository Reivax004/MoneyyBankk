package com.example.models;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    private Integer id;

    private String lastname;
    private String firstname;
    //private LocalDate birthdate;
    private String email;
    private String password;

    public User() {}

    public User(String lastname,String firstname, String email, LocalDate birthdate, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        //this.birthdate = birthdate;
        this.password = password;
    }

    public Integer getId() { return this.id; }
    public String getFirstName() { return this.lastname; }
    public String getLastName() { return this.firstname; }
    public String getEmail() { return this.email; }
    //public LocalDate getBirthdate() { return this.birthdate; }
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
    //public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }
}
