package se.lexicon.jpabooking.database.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.jpabooking.model.entity.Premises;

public interface PremisesDAO extends JpaRepository<Premises, String> {
}
