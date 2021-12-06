package se.lexicon.jpabooking.database.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.jpabooking.model.entity.Address;

import java.util.Optional;

public interface AddressDAO extends JpaRepository<Address, String> {

    @Query("SELECT a FROM Address a WHERE UPPER(a.streetAddress) = UPPER(:street) AND a.zipCode = :zip AND UPPER(a.city) = UPPER(:city)")
    Optional<Address> findByStreetZipCodeAndCity(
            @Param("street") String streetAddress,
            @Param("zip") String zipCode,
            @Param("city") String city
    );

}
