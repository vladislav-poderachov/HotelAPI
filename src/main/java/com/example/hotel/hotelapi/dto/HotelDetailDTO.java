package com.example.hotel.hotelapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class HotelDetailDTO {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressDTO address;
    private ContactsDTO contacts;
    private ArrivalTimeDTO arrivalTime;
    private List<String> amenities;
}