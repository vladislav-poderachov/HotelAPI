package com.example.hotel.hotelapi.service;

import com.example.hotel.hotelapi.dto.*;
import com.example.hotel.hotelapi.entity.Address;
import com.example.hotel.hotelapi.entity.Amenity;
import com.example.hotel.hotelapi.entity.Hotel;
import com.example.hotel.hotelapi.exception.ResourceNotFoundException;
import com.example.hotel.hotelapi.mapper.HotelMapper;
import com.example.hotel.hotelapi.repository.AddressRepository;
import com.example.hotel.hotelapi.repository.AmenityRepository;
import com.example.hotel.hotelapi.repository.HotelRepository;
import com.example.hotel.hotelapi.service.strategy.HistogramStrategyFactory;
import com.example.hotel.hotelapi.specification.HotelSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AddressRepository addressRepository;
    private final AmenityRepository amenityRepository;
    private final HistogramStrategyFactory histogramStrategyFactory;

    public List<HotelSummaryDTO> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(HotelMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    public HotelDetailDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        return HotelMapper.toDetailDTO(hotel);
    }

    @Transactional
    public HotelSummaryDTO createHotel(CreateHotelRequest request) {
        Hotel hotel = HotelMapper.toEntity(request);

        Address address = findOrCreateAddress(request.getAddress());
        hotel.setAddress(address);

        Hotel savedHotel = hotelRepository.save(hotel);
        return HotelMapper.toSummaryDto(savedHotel);
    }

    @Transactional
    public void addAmenities(Long hotelId, List<String> amenityNames) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        Set<String> uniqueNames = new HashSet<>(amenityNames);

        Set<Amenity> existingAmenities = amenityRepository.findByNameIn(uniqueNames);
        Set<String> existingNames = existingAmenities.stream()
                .map(Amenity::getName)
                .collect(Collectors.toSet());

        Set<Amenity> newAmenities = uniqueNames.stream()
                .filter(name -> !existingNames.contains(name))
                .map(name -> new Amenity(null, name))
                .collect(Collectors.toSet());

        if (!newAmenities.isEmpty()) {
            amenityRepository.saveAll(newAmenities);
        }

        Set<Amenity> allAmenities = new HashSet<>();
        allAmenities.addAll(existingAmenities);
        allAmenities.addAll(newAmenities);
        hotel.getAmenities().addAll(allAmenities);

        hotelRepository.save(hotel);
    }

    public List<HotelSummaryDTO> searchHotels(String name, String brand, String city, String country, List<String> amenities) {
        Specification<Hotel> spec = Specification.where(null);

        if (name != null && !name.isBlank()) {
            spec = spec.and(HotelSpecification.hasNameLike(name));
        }
        if (brand != null && !brand.isBlank()) {
            spec = spec.and(HotelSpecification.hasBrand(brand));
        }
        if (city != null && !city.isBlank()) {
            spec = spec.and(HotelSpecification.hasCity(city));
        }
        if (country != null && !country.isBlank()) {
            spec = spec.and(HotelSpecification.hasCountry(country));
        }
        if (amenities != null && !amenities.isEmpty()) {
            spec = spec.and(HotelSpecification.hasAmenities(amenities));
        }

        return hotelRepository.findAll(spec).stream()
                .map(HotelMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    public Map<String, Long> getHistogram(String param) {
        return histogramStrategyFactory.getStrategy(param).compute();
    }

    private Address findOrCreateAddress(AddressDTO dto) {
        return addressRepository
                .findByHouseNumberAndStreetAndCityAndCountryAndPostCode(
                        dto.getHouseNumber(),
                        dto.getStreet(),
                        dto.getCity(),
                        dto.getCountry(),
                        dto.getPostCode())
                .orElseGet(() -> {
                    Address newAddress = HotelMapper.toEntity(dto);
                    return addressRepository.save(newAddress);
                });
    }
}