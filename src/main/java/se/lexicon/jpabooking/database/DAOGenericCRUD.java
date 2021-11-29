package se.lexicon.jpabooking.database;

import java.util.List;
import java.util.Optional;

public interface DAOGenericCRUD <T, ID>{
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void delete(ID id);
}
