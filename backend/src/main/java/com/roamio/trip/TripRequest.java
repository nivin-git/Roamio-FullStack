package com.roamio.trip;

import com.roamio.user.User;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name="trip_requests")
public class TripRequest {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private User traveler;

    @Column(nullable=false, length=5000)
    private String requestText;

    private String destination;
    private Integer durationDays;
    private Integer travelers;
    private Integer budget;
    private String currency;
    private String interests;
    private String status = "DRAFT";
    private Instant createdAt = Instant.now();

    public Long getId(){return id;}
    public User getTraveler(){return traveler;}
    public void setTraveler(User traveler){this.traveler=traveler;}
    public String getRequestText(){return requestText;}
    public void setRequestText(String v){requestText=v;}
    public String getDestination(){return destination;}
    public void setDestination(String v){destination=v;}
    public Integer getDurationDays(){return durationDays;}
    public void setDurationDays(Integer v){durationDays=v;}
    public Integer getTravelers(){return travelers;}
    public void setTravelers(Integer v){travelers=v;}
    public Integer getBudget(){return budget;}
    public void setBudget(Integer v){budget=v;}
    public String getCurrency(){return currency;}
    public void setCurrency(String v){currency=v;}
    public String getInterests(){return interests;}
    public void setInterests(String v){interests=v;}
    public String getStatus(){return status;}
    public void setStatus(String v){status=v;}
    public Instant getCreatedAt(){return createdAt;}
}
