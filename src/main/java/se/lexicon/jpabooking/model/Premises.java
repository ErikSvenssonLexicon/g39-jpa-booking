package se.lexicon.jpabooking.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

import static se.lexicon.jpabooking.model.constants.EntityConstants.GENERATOR;
import static se.lexicon.jpabooking.model.constants.EntityConstants.UUID_GENERATOR;

@Entity
public class Premises {

    @Id
    @GeneratedValue(generator = GENERATOR)
    @GenericGenerator(name = GENERATOR, strategy = UUID_GENERATOR)
    @Column(updatable = false)
    private String id;
    private String name;
    @ManyToOne(
            cascade = {CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "fk_address_id")
    private Address address;

    public Premises(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Premises() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Premises premises = (Premises) o;
        return Objects.equals(id, premises.id) && Objects.equals(name, premises.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Premises{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
