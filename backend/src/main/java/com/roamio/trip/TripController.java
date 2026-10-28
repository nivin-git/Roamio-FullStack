package com.roamio.trip;

import com.roamio.auth.CurrentUser;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripRequestRepository trips;
    private final CurrentUser current;

    public TripController(TripRequestRepository trips,CurrentUser current){this.trips=trips;this.current=current;}

    public record CreateTripRequest(String requestText,String destination,Integer durationDays,
                                     Integer travelers,Integer budget,String currency,String interests){}

    @PostMapping
    public TripRequest create(@RequestBody CreateTripRequest r, Principal p){
        TripRequest t=new TripRequest();
        t.setTraveler(current.get(p));
        t.setRequestText(r.requestText()); t.setDestination(r.destination());
        t.setDurationDays(r.durationDays()); t.setTravelers(r.travelers());
        t.setBudget(r.budget()); t.setCurrency(r.currency());
        t.setInterests(r.interests()); t.setStatus("SUBMITTED");
        return trips.save(t);
    }

    @GetMapping
    public List<TripRequest> mine(Principal p){return trips.findByTravelerIdOrderByCreatedAtDesc(current.get(p).getId());}
}
