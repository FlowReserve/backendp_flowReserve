package com.flowreserve.demo1.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.flowreserve.demo1.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNombre(String nombre);
    Optional<User> findByEmail(String email);
}
