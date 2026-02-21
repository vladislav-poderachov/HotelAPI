package com.example.hotel.hotelapi.service.strategy;

import com.example.hotel.hotelapi.entity.Amenity;
import com.example.hotel.hotelapi.entity.Hotel;
import com.example.hotel.hotelapi.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AmenitiesHistogramStrategy implements HistogramStrategy {
    private final HotelRepository hotelRepository;

    @Override
    public Map<String, Long> compute() {
        List<Hotel> hotels = hotelRepository.findAll(); // для простоты
        Map<String, Long> histogram = new HashMap<>();
        for (Hotel hotel : hotels) {
            for (Amenity amenity : hotel.getAmenities()) {
                histogram.merge(amenity.getName(), 1L, Long::sum);
            }
        }
        return histogram;
    }
}