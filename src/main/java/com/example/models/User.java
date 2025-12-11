package com.example.models;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    private Integer id;

    private String lastname;

    private String firstname;

    @Past
    private LocalDate birthdate;

    @Column(nullable = false) @Email @NotBlank

    private String email;

    @Column(nullable = false) @NotBlank @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConnectionHistory> connectionHistories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public User() {}

    public User(String lastname,String firstname, String email, LocalDate birthdate, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        //this.birthdate = birthdate;
        this.password = password;
    }

    public Integer getId() { return this.id; }
    public String getFirstName() { return this.firstname; }
    public String getLastName() { return this.lastname; }
    public String getEmail() { return this.email; }
    public LocalDate getBirthdate() { return this.birthdate; }
  
    public void setId(Integer id) { this.id = id; }
    public void setLastName(String lastname) { this.lastname = lastname; }
    public void setFirstName(String firstname) { this.firstname = firstname; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password;}
    //public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }
}
