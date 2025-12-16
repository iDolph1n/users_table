package ru.netology.users_table.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @EmbeddedId
    private PersonId id;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "city_of_living", length = 50)
    private String cityOfLiving;
}
