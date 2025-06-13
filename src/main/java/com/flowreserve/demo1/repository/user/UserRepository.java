package com.flowreserve.demo1.repository.user;
import org.springframework.data.jpa.repository.JpaRepository;
import com.flowreserve.demo1.model.User.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
