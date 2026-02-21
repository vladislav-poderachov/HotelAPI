package com.example.hotel.hotelapi.specification;

import com.example.hotel.hotelapi.entity.Hotel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class HotelSpecification {

    public static Specification<Hotel> hasNameLike(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) -> brand == null ? null :
                cb.equal(cb.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) -> city == null ? null :
                cb.equal(cb.lower(root.get("address").get("city")), city.toLowerCase());
    }

    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) -> country == null ? null :
                cb.equal(cb.lower(root.get("address").get("country")), country.toLowerCase());
    }

    public static Specification<Hotel> hasAmenities(List<String> amenityNames) {
        return (root, query, cb) -> {
            if (amenityNames == null || amenityNames.isEmpty()) return null;
            Join<Object, Object> amenitiesJoin = root.join("amenities");
            return amenitiesJoin.get("name").in(amenityNames);
        };
    }
}