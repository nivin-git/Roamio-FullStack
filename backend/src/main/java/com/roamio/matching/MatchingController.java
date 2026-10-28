package com.roamio.matching;

import com.roamio.provider.*;
import com.roamio.trip.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {
    private final TripRequestRepository trips;
    private final ProviderProfileRepository providers;

    public MatchingController(TripRequestRepository trips,ProviderProfileRepository providers){
        this.trips=trips;this.providers=providers;
    }

    @GetMapping("/trip/{tripId}")
    public List<Map<String,Object>> match(@PathVariable Long tripId){
        TripRequest t=trips.findById(tripId).orElseThrow();
        String dest=Optional.ofNullable(t.getDestination()).orElse("").toLowerCase();
        String interests=Optional.ofNullable(t.getInterests()).orElse("").toLowerCase();

        return providers.findByActiveTrue().stream().map(p->{
            int score=0;
            String pd=Optional.ofNullable(p.getDestinations()).orElse("").toLowerCase();
            String ps=Optional.ofNullable(p.getSpecialties()).orElse("").toLowerCase();
            if(!dest.isBlank() && pd.contains(dest)) score+=40;
            if(!interests.isBlank() && Arrays.stream(interests.split(","))
                    .map(String::trim).anyMatch(ps::contains)) score+=30;
            score+=Math.round((float)(p.getRating()/5.0*30));
                return Map.<String,Object>of("providerId",p.getId(),"companyName",
                    Optional.ofNullable(p.getCompanyName()).orElse(p.getUser().getFullName()),
                    "score",Math.min(score,100),"rating",p.getRating(),
                    "specialties",Optional.ofNullable(p.getSpecialties()).orElse(""));
        }).sorted((a,b)->Integer.compare((Integer)b.get("score"),(Integer)a.get("score"))).toList();
    }
}
