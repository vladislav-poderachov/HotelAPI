package com.example.hotel.hotelapi.mapper;

import com.example.hotel.hotelapi.dto.*;
import com.example.hotel.hotelapi.entity.Address;
import com.example.hotel.hotelapi.entity.Amenity;
import com.example.hotel.hotelapi.entity.Hotel;
import java.util.List;
import java.util.stream.Collectors;

public class HotelMapper {

    private HotelMapper() {}

    public static HotelSummaryDTO toSummaryDTO(Hotel hotel) {
        if (hotel == null) return null;
        return new HotelSummaryDTO(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                formatAddress(hotel.getAddress()),
                hotel.getPhone()
        );
    }

    public static HotelDetailDTO toDetailDTO(Hotel hotel) {
        if (hotel == null) return null;
        HotelDetailDTO dto = new HotelDetailDTO();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setBrand(hotel.getBrand());
        dto.setAddress(toAddressDTO(hotel.getAddress()));

        ContactsDTO contacts = new ContactsDTO();
        contacts.setPhone(hotel.getPhone());
        contacts.setEmail(hotel.getEmail());
        dto.setContacts(contacts);

        ArrivalTimeDTO arrival = new ArrivalTimeDTO();
        arrival.setCheckIn(hotel.getCheckIn());
        arrival.setCheckOut(hotel.getCheckOut());
        dto.setArrivalTime(arrival);

        if (hotel.getAmenities() != null) {
            List<String> amenityNames = hotel.getAmenities().stream()
                    .map(Amenity::getName)
                    .collect(Collectors.toList());
            dto.setAmenities(amenityNames);
        }
        return dto;
    }

    public static AddressDTO toAddressDTO(Address address) {
        if (address == null) return null;
        AddressDTO dto = new AddressDTO();
        dto.setHouseNumber(address.getHouseNumber());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setCountry(address.getCountry());
        dto.setPostCode(address.getPostCode());
        return dto;
    }

    public static Hotel toEntity(CreateHotelRequest request) {
        if (request == null) return null;
        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setBrand(request.getBrand());

        if (request.getContacts() != null) {
            hotel.setPhone(request.getContacts().getPhone());
            hotel.setEmail(request.getContacts().getEmail());
        }

        if (request.getArrivalTime() != null) {
            hotel.setCheckIn(request.getArrivalTime().getCheckIn());
            hotel.setCheckOut(request.getArrivalTime().getCheckOut());
        }
        return hotel;
    }

    public static Address toEntity(AddressDTO dto) {
        if (dto == null) return null;
        Address address = new Address();
        address.setHouseNumber(dto.getHouseNumber());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setPostCode(dto.getPostCode());
        return address;
    }

    private static String formatAddress(Address address) {
        if (address == null) return null;
        return address.getHouseNumber() + " " + address.getStreet() + ", " +
                address.getCity() + ", " + address.getPostCode() + ", " + address.getCountry();
    }
}