package com.example.hotel.hotelapi.service.strategy;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HistogramStrategyFactory {
    private final Map<String, HistogramStrategy> strategies = new ConcurrentHashMap<>();

    public HistogramStrategyFactory(BrandHistogramStrategy brand,
                                    CityHistogramStrategy city,
                                    CountryHistogramStrategy country,
                                    AmenitiesHistogramStrategy amenities) {
        strategies.put("brand", brand);
        strategies.put("city", city);
        strategies.put("country", country);
        strategies.put("amenities", amenities);
    }

    public HistogramStrategy getStrategy(String param) {
        HistogramStrategy strategy = strategies.get(param.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid histogram parameter: " + param);
        }
        return strategy;
    }
}