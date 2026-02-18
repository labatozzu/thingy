package com.myproject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "garment")
public class Garment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String title;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "waist_min", nullable = false)
    private Integer waistMin;

    @Column(name = "waist_max", nullable = false)
    private Integer waistMax;

    @Column(name = "length_in", nullable = false)
    private Integer lengthIn;

    @Column(name = "fit_estimate", nullable = false)
    @Enumerated(EnumType.STRING)
    private FitEstimate fitEstimate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemState state;

    public Garment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getWaistMin() {
        return waistMin;
    }

    public void setWaistMin(Integer waistMin) {
        this.waistMin = waistMin;
    }

    public Integer getWaistMax() {
        return waistMax;
    }

    public void setWaistMax(Integer waistMax) {
        this.waistMax = waistMax;
    }

    public Integer getLengthIn() {
        return lengthIn;
    }

    public void setLengthIn(Integer lengthIn) {
        this.lengthIn = lengthIn;
    }

    public FitEstimate getFitEstimate() {
        return fitEstimate;
    }

    public void setFitEstimate(FitEstimate fitEstimate) {
        this.fitEstimate = fitEstimate;
    }

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }
}
