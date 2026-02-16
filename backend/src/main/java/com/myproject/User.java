package com.myproject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "jeans_waist_min")
    private Integer jeansWaistMin;

    @Column(name = "jeans_waist_max")
    private Integer jeansWaistMax;

    @Column(name = "jeans_length_in")
    private Integer jeansLengthIn;

    @Column(name = "height_cm")
    private Integer heightCm;

    public User() {
    }

    public User(Long id, String email, String passwordHash) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Integer getJeansWaistMin() {
        return jeansWaistMin;
    }

    public void setJeansWaistMin(Integer jeansWaistMin) {
        this.jeansWaistMin = jeansWaistMin;
    }

    public Integer getJeansWaistMax() {
        return jeansWaistMax;
    }

    public void setJeansWaistMax(Integer jeansWaistMax) {
        this.jeansWaistMax = jeansWaistMax;
    }

    public Integer getJeansLengthIn() {
        return jeansLengthIn;
    }

    public void setJeansLengthIn(Integer jeansLengthIn) {
        this.jeansLengthIn = jeansLengthIn;
    }

    public Integer getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Integer heightCm) {
        this.heightCm = heightCm;
    }
}
