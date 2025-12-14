package ru.netology.hibernate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @EmbeddedId
    private PersonId id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city_of_living")
    private String cityOfLiving;

    public Person(String name,
                  String surname,
                  Integer age,
                  String phoneNumber,
                  String cityOfLiving) {
        this.id = new PersonId(name, surname, age);
        this.phoneNumber = phoneNumber;
        this.cityOfLiving = cityOfLiving;
    }
}
