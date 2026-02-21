package com.example.hotel.hotelapi.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class AddressDTO {
    @NotBlank
    private String houseNumber;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String postCode;
}