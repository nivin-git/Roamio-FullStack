package com.roamio.provider;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProviderProfileRepository extends JpaRepository<ProviderProfile,Long> {
    List<ProviderProfile> findByActiveTrue();
}
