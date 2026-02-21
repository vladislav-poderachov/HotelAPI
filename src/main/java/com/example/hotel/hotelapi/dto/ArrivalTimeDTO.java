package com.example.hotel.hotelapi.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ArrivalTimeDTO {
    @NotBlank
    private String checkIn;
    private String checkOut;
}