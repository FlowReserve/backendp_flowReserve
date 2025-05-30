package com.flowreserve.demo1;

import com.flowreserve.demo1.model.Role;
import com.flowreserve.demo1.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RoleRepository roleRepository;

    @Autowired
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {
        System.out.println("Inicializando roles...");

        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(new Role("ROLE_USER"));
        }
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if (roleRepository.findByName("ROLE_MEDICO").isEmpty()) {
            roleRepository.save(new Role("ROLE_MEDICO"));
        }
        if (roleRepository.findByName("ROLE_HOSPITAL").isEmpty()) {
            roleRepository.save(new Role("ROLE_HOSPITAL"));
        }
        if (roleRepository.findByName("ROLE_ADMIN_INVITACIONES").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN_INVITACIONES"));
        }

        if (roleRepository.findByName("ROLE_ADMIN_HOSPITALES").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN_HOSPITALES"));
        }
    }
}