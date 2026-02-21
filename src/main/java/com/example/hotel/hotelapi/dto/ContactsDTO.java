package com.example.hotel.hotelapi.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class ContactsDTO {
    @NotBlank
    private String phone;
    @Email
    @NotBlank
    private String email;
}