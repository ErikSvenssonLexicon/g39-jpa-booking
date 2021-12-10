package se.lexicon.jpabooking.service.entity;

import se.lexicon.jpabooking.model.dto.form.AddressForm;
import se.lexicon.jpabooking.model.entity.Address;

import java.util.List;

public interface AddressEntityService{

    Address persistOrChange(AddressForm addressForm);

    Address findById(String id);

    List<Address> findAll();

    void delete(String id);

}
