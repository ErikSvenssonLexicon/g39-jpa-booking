package se.lexicon.jpabooking.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.jpabooking.model.entity.Premises;

public interface PremisesDAO extends JpaRepository<Premises, String> {
    @Query("SELECT COUNT(p.id) as total FROM Premises p WHERE p.address.id = :addressId")
    long countUsagesByAddressId(@Param("addressId") String addressId);
}
