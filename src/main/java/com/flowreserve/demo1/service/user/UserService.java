package com.flowreserve.demo1.service.user;
import com.flowreserve.demo1.model.User.User;
import org.springframework.stereotype.Service;
import com.flowreserve.demo1.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service


public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> obtenerTodos() {
        return userRepository.findAll();
    }

    public Optional<User> obtenerPorId(Long id) {
        return userRepository.findById(id);
    }

    public User guardar(User user) {
        return userRepository.save(user);
    }

    public void eliminar(Long id) {
        userRepository.deleteById(id);
    }


}
