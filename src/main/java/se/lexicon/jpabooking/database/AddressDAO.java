package se.lexicon.jpabooking.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.jpabooking.model.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressDAO extends JpaRepository<Address, String> {
    @Query("SELECT a FROM Address a WHERE UPPER(a.streetAddress) = UPPER(:street) AND a.zipCode = :zip AND UPPER(a.city) = UPPER(:city)")
    List<Address> findByStreetZipCodeAndCity(@Param("street") String street, @Param("zip") String zip, @Param("city") String city);
}
