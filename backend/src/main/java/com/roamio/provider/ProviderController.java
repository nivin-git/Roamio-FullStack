package com.roamio.provider;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {
    private final ProviderProfileRepository repo;
    public ProviderController(ProviderProfileRepository repo){this.repo=repo;}
    @GetMapping public List<ProviderProfile> all(){return repo.findByActiveTrue();}
}
