package com.test.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.Entity.User;

@Repository("UserRepository")
public interface UserRepository extends CrudRepository<User, String>  {
	Optional<User> findById(Long id);
}
