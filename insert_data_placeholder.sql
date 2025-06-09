-- AÑADE LOS PERMISOS EXISTENTES EN LA BASE DE DATOS
INSERT INTO permissions_test (id, name) VALUES
(1,'CREATE'),
(2,'DELETE'),
(3,'UPDATE'),
(4,'READ');

-- AÑADE LOS ROLES EXISTENTES EN LA BASE DE DATOS
INSERT INTO roles_test (id, role_name) VALUES
(1, 'GUESS'),
(2, 'USER'),
(3, 'DEVELOPER'),
(4, 'ADMIN');

-- CREA RELACIONES ENTRE ROLES Y PERMISOS
INSERT INTO roles_permissions_test (role_id, permission_id) VALUES
(1,4), -- guess
(2,4), -- user
(3,1), -- moderator
(3,2),
(3,3),
(4,1), -- admin
(4,2),
(4,3),
(4,4);

-- CREA USUARIOS EN LA BASE DE DATOS
INSERT INTO users_test (id, credential_no_expired, account_no_expired, account_no_locked, username, is_enabled, password) VALUES
(1, TRUE, TRUE, TRUE, 'test@test.com', TRUE, '$2a$10$EaOIPn9whrM5QV/BiIL9Z.d./GcGalcpOE/WGOLSL.tUjuQ/ek9JG'), -- admin 12345
(2, TRUE, TRUE, TRUE, 'martin.gago@flowreserve.es', TRUE, '$2a$10$EaOIPn9whrM5QV/BiIL9Z.d./GcGalcpOE/WGOLSL.tUjuQ/ek9JG'), -- moderator 12345
(3, TRUE, TRUE, TRUE, 'elias@flowreserve.es', TRUE, '$2a$10$EaOIPn9whrM5QV/BiIL9Z.d./GcGalcpOE/WGOLSL.tUjuQ/ek9JG'), -- moderator 12345
(4, TRUE, TRUE, TRUE, 'user@test.com', TRUE, '$2a$10$EaOIPn9whrM5QV/BiIL9Z.d./GcGalcpOE/WGOLSL.tUjuQ/ek9JG'); -- user 12345


-- ASIGNA ROLES A LOS USUARIOS
INSERT INTO user_roles_test (user_id, role_id) VALUES
(1,4),
(2,3),
(3,3),
(4,2);

INSERT INTO hospital (id, codigo, nombre) VALUES 
(1, 123456, "HOSPITAL CHUS SANTIAGO"),
(2, 654321, "HOSPITAL PONTEVEDRA");

INSERT INTO user (id, account_no_expired, account_no_locked, apellido, credential_no_expired, email, is_enabled, nombre, password) VALUES
(1, TRUE, TRUE, "Lopez Sanchez",TRUE, 'test@test.com', TRUE, "Ramirez",'$2a$10$EaOIPn9whrM5QV/BiIL9Z.d./GcGalcpOE/WGOLSL.tUjuQ/ek9JG'), -- 12345
(2, TRUE, TRUE, "Gago", TRUE, 'martin.gago@flowreserve.es', TRUE, "Martin", '$2a$10$EaOIPn9whrM5QV/BiIL9Z.d./GcGalcpOE/WGOLSL.tUjuQ/ek9JG'); -- 12345

INSERT INTO medico (id, especialidad, hospital_id) VALUES 
(1, "CARDIOLOGO", 1),
(2, "CARDIOLOGO", 2);

INSERT INTO invitaciones (id, codigo, usada, hospital_id, medico_id) VALUES
(3, "INV_000002", TRUE, 1, 1),
(4, "INV_000003", TRUE, 2, 2);

INSERT INTO paciente(id, apellido, nhc, nombre, medico_id) VALUES
(1, "Casal", "12345678H", "Paula", 1),
(2, "Naranjo", "65432134H", "Carlos", 1);

SELECT 
    u.username,
    GROUP_CONCAT(DISTINCT r.role_name ORDER BY r.role_name SEPARATOR ', ') AS roles,
    GROUP_CONCAT(DISTINCT p.name ORDER BY p.name SEPARATOR ', ') AS permissions
FROM users_test u
JOIN user_roles_test ur ON u.id = ur.user_id
JOIN roles_test r ON ur.role_id = r.id
JOIN roles_permissions_test rp ON r.id = rp.role_id
JOIN permissions_test p ON rp.permission_id = p.id
GROUP BY u.id, u.username
ORDER BY u.username;


