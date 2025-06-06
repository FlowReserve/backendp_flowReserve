package com.flowreserve.demo1.controller.User;

import com.flowreserve.demo1.model.User.User;
import com.flowreserve.demo1.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> listarUsuarios() {
        return userService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<User> obtenerUsuario(@PathVariable Long id) {
        return userService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<User> crearUsuario(@RequestBody User user) {
        User saved = userService.guardar(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

   // public User crearUsuario(@RequestBody User user) {
     //   return userService.guardar(user);
    //}

    @PutMapping("/{id}")
    public User actualizarUsuario(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userService.guardar(user);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        userService.eliminar(id);


    }
}
