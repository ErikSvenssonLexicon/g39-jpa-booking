package se.lexicon.jpabooking.database;

import se.lexicon.jpabooking.model.entity.Address;

import java.util.Optional;

public interface AddressDAO extends DAOGenericCRUD<Address, String> {
    Optional<Address> findByStreetZipCodeAndCity(String street, String zip, String city);
}
