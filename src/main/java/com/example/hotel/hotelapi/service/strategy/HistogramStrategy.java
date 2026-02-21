package com.example.hotel.hotelapi.service.strategy;

import java.util.Map;

public interface HistogramStrategy {
    Map<String, Long> compute();
}