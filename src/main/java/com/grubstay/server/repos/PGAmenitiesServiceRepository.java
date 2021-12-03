package com.grubstay.server.repos;

import com.grubstay.server.entities.PGAmenitiesServices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PGAmenitiesServiceRepository extends JpaRepository<PGAmenitiesServices, Long> {
}
