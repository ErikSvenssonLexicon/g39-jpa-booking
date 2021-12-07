package se.lexicon.jpabooking.service.entity;

import java.util.List;

public interface GenericEntityService <T, FORM>{
    T create(FORM form);
    T findById(String id);
    List<T> findAll();
    T update(String id, FORM form);
    void delete(String id);
}
