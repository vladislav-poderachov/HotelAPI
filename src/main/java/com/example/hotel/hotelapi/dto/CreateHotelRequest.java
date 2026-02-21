package com.example.hotel.hotelapi.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateHotelRequest {
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String brand;
    @Valid
    @NotNull
    private AddressDTO address;
    @Valid
    @NotNull
    private ContactsDTO contacts;
    @Valid
    @NotNull
    private ArrivalTimeDTO arrivalTime;
}