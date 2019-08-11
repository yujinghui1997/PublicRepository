package com.test.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.Entity.Person;
@Repository
public interface PersonRepository extends CrudRepository<Person, String> {

}
