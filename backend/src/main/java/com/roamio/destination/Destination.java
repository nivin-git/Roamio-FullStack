package com.roamio.destination;

import jakarta.persistence.*;

@Entity
@Table(name="destinations")
public class Destination {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String country;

    private String description;
    private String imageUrl;

    public Destination(){}
    public Destination(String name,String country,String description,String imageUrl){
        this.name=name; this.country=country; this.description=description; this.imageUrl=imageUrl;
    }
    public Long getId(){return id;}
    public String getName(){return name;}
    public String getCountry(){return country;}
    public String getDescription(){return description;}
    public String getImageUrl(){return imageUrl;}
}
