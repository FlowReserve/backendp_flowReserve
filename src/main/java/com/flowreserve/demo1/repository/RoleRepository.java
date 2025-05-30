package com.flowreserve.demo1.repository;
import java.util.Optional;
import com.flowreserve.demo1.model.Request;
import com.flowreserve.demo1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
