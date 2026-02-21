package com.example.hotel.hotelapi.repository;

import com.example.hotel.hotelapi.entity.Address;
import com.example.hotel.hotelapi.entity.Hotel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void shouldSaveAndFindHotel() {
        Address address = new Address(null, "9", "Pobediteley Avenue", "Minsk", "Belarus", "220004");
        address = addressRepository.save(address);

        Hotel hotel = new Hotel(null, "DoubleTree by Hilton Minsk", "description", "Hilton",
                address, "+375 17 309-80-00", "email@test.com", "14:00", "12:00", null);
        hotel = hotelRepository.save(hotel);

        List<Hotel> found = hotelRepository.findAll();
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getName()).isEqualTo("DoubleTree by Hilton Minsk");
    }
}