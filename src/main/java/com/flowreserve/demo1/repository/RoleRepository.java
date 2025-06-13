package com.flowreserve.demo1.repository;
import java.util.Optional;

import com.flowreserve.demo1.model.Role;
import com.flowreserve.demo1.model.role.RoleEnum;
import com.flowreserve.demo1.model.role.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    Optional<RoleModel> findByRoleEnum(RoleEnum roleEnum);

}
