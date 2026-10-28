package com.roamio.provider;

import com.roamio.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="provider_profiles")
public class ProviderProfile {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional=false)
    private User user;

    private String companyName;
    private String country;
    private String destinations;
    private String specialties;
    private Double rating = 4.5;
    private boolean active = true;

    public Long getId(){return id;}
    public User getUser(){return user;}
    public void setUser(User v){user=v;}
    public String getCompanyName(){return companyName;}
    public void setCompanyName(String v){companyName=v;}
    public String getCountry(){return country;}
    public void setCountry(String v){country=v;}
    public String getDestinations(){return destinations;}
    public void setDestinations(String v){destinations=v;}
    public String getSpecialties(){return specialties;}
    public void setSpecialties(String v){specialties=v;}
    public Double getRating(){return rating;}
    public void setRating(Double v){rating=v;}
    public boolean isActive(){return active;}
    public void setActive(boolean v){active=v;}
}
