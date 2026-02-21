package com.example.hotel.hotelapi.repository;

import com.example.hotel.hotelapi.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    Optional<Amenity> findByName(String name);
    Set<Amenity> findByNameIn(Set<String> names);
}