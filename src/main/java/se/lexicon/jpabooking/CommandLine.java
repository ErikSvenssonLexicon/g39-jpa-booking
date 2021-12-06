package se.lexicon.jpabooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.jpabooking.database.data.AddressDAO;
import se.lexicon.jpabooking.model.entity.Address;

import java.util.Optional;

@Component
public class CommandLine implements CommandLineRunner {

    private final AddressDAO addressDAO;

    @Autowired
    public CommandLine(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    @Override
    public void run(String... args) throws Exception {
        Address address = addressDAO.save(new Address(null, "Storgatan 1", "35256", "Växjö"));

        Optional<Address> result = addressDAO.findByStreetZipCodeAndCity(address.getStreetAddress(), address.getZipCode(), address.getCity());
        System.out.println(result);
    }
}
