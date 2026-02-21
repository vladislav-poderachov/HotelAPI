package com.example.hotel.hotelapi.service;

import com.example.hotel.hotelapi.dto.*;
import com.example.hotel.hotelapi.entity.Address;
import com.example.hotel.hotelapi.entity.Hotel;
import com.example.hotel.hotelapi.exception.ResourceNotFoundException;
import com.example.hotel.hotelapi.repository.AddressRepository;
import com.example.hotel.hotelapi.repository.AmenityRepository;
import com.example.hotel.hotelapi.repository.HotelRepository;
import com.example.hotel.hotelapi.service.strategy.HistogramStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AmenityRepository amenityRepository;
    @Mock
    private HistogramStrategyFactory strategyFactory;

    @InjectMocks
    private HotelService hotelService;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        Address address = new Address(1L, "9", "Pobediteley Avenue", "Minsk", "Belarus", "220004");
        hotel = new Hotel(1L, "DoubleTree by Hilton Minsk", "desc", "Hilton",
                address, "+375 17 309-80-00", "test@test.com", "14:00", "12:00", null);
    }

    @Test
    void shouldReturnHotelById() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        HotelDetailDTO dto = hotelService.getHotelById(1L);
        assertThat(dto.getName()).isEqualTo("DoubleTree by Hilton Minsk");
    }

    @Test
    void shouldThrowWhenHotelNotFound() {
        when(hotelRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> hotelService.getHotelById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }
}