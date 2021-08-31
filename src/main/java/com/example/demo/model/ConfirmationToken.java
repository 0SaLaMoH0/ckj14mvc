package com.example.demo.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String value;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    public ConfirmationToken(){}

    public ConfirmationToken(User user) {
        this.value = UUID.randomUUID().toString();
        this.user = user;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getValue() {return value;}
    public void setValue(String value) {this.value = value;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
