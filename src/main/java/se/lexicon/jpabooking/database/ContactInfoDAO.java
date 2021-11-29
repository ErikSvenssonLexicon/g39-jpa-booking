package se.lexicon.jpabooking.database;

import se.lexicon.jpabooking.model.ContactInfo;

import java.util.Optional;

public interface ContactInfoDAO extends DAOGenericCRUD<ContactInfo, String>{
    Optional<ContactInfo> findByEmail(String email);
}
