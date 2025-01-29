package ru.test.task.entities;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "emails", joinColumns =@JoinColumn(name = "user_id"))
    @Column(name = "email")
    private Set<String> email;

    @ElementCollection
    @CollectionTable(name = "phone_numbers", joinColumns =@JoinColumn(name = "user_id"))
    @Column(name = "phone_number")
    private Set<String> phoneNumber;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getEmail() {
        return email;
    }

    public void setEmail(Set<String> email) {
        this.email = email;
    }

    public Set<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Set<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
