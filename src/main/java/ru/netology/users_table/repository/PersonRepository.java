package ru.netology.users_table.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.users_table.entity.Person;
import ru.netology.users_table.entity.PersonId;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, PersonId> {

    // по городу
    List<Person> findByCityOfLiving(String city);

    // младше age и по возрастанию возраста
    List<Person> findByIdAgeLessThanOrderByIdAgeAsc(Integer age);

    // по name + surname => Optional
    Optional<Person> findFirstByIdNameAndIdSurname(String name, String surname);
}
