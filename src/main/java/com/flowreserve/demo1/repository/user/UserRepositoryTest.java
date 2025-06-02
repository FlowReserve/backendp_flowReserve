package com.flowreserve.demo1.repository.user;

import com.flowreserve.demo1.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryTest extends CrudRepository<UserModel, Long> {

    Optional<UserModel> findUserModelByEmail(String email);
    Optional<UserModel> findUserModelByUsername(String username);
}
