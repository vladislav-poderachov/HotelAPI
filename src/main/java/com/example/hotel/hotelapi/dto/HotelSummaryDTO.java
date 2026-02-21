package com.example.hotel.hotelapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelSummaryDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}