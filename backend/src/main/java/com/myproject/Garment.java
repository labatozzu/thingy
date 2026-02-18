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

    @Column(nullable = false)
    private Integer waist;

    @Column(name = "length_in", nullable = false)
    private Integer lengthIn;

    @Column(name = "fit_estimate", nullable = false)
    @Enumerated(EnumType.STRING)
    private FitEstimate fitEstimate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemVisibility visibility;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

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

    public Integer getWaist() {
        return waist;
    }

    public void setWaist(Integer waist) {
        this.waist = waist;
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

    public ItemVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(ItemVisibility visibility) {
        this.visibility = visibility;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
}
