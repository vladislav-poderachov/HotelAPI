package com.example.hotel.hotelapi.repository;

import com.example.hotel.hotelapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByHouseNumberAndStreetAndCityAndCountryAndPostCode(
            String houseNumber, String street, String city, String country, String postCode);
}