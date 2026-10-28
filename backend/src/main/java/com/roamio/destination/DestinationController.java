package com.roamio.destination;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {
    private final DestinationRepository repo;
    public DestinationController(DestinationRepository repo){this.repo=repo;}
    @GetMapping public List<Destination> all(){return repo.findAll();}
}
