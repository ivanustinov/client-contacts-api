package ru.ustinov.clients.entities;

import io.swagger.model.ContactType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Client client;

    @Enumerated(EnumType.STRING)
    private ContactType type;

    @NotNull
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Contact contact = (Contact) o;
        return id != null && Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
