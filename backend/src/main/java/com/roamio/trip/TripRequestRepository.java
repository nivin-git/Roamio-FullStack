package com.roamio.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripRequestRepository extends JpaRepository<TripRequest,Long> {
    List<TripRequest> findByTravelerIdOrderByCreatedAtDesc(Long travelerId);
}
