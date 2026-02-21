package com.example.hotel.hotelapi.service.strategy;

import com.example.hotel.hotelapi.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CountryHistogramStrategy implements HistogramStrategy {
    private final HotelRepository hotelRepository;

    @Override
    public Map<String, Long> compute() {
        return hotelRepository.countByCountry().stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Long) arr[1]
                ));
    }
}