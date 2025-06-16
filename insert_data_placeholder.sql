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
(4, 'DOCTOR'),
(5, 'VISUALIZER'),
(6, 'ADMIN');

INSERT INTO roles_permissions_test (role_id, permission_id) VALUES
(1,4),
(2,4),
(3,1),
(3,2),
(3,3),
(3,4),
(4,1),
(4,3),
(4,4),
(6,1),
(6,3),
(6,4);

-- CREA USUARIOS EN LA BASE DE DATOS
INSERT INTO user (id, account_no_expired, account_no_locked, apellido, credential_no_expired, email, is_enabled, nombre, password) VALUES
(1, TRUE, TRUE, "Rodriguez Otero",TRUE, 'admin@test.com', TRUE, "Alberto",'$2a$10$EaOIPn9whrM5QV/BiIL9Z.d./GcGalcpOE/WGOLSL.tUjuQ/ek9JG'), -- 12345
(2, TRUE, TRUE, "Domingo Ramos", TRUE, 'user@test.com', TRUE, "Francisco", '$2a$10$EaOIPn9whrM5QV/BiIL9Z.d./GcGalcpOE/WGOLSL.tUjuQ/ek9JG'); -- 12345

-- ASIGNA ROLES A LOS USUARIOS
INSERT INTO user_roles_test (user_id, role_id) VALUES
(1,6),
(2,4);


INSERT INTO hospital (id, codigo, nombre) VALUES 
(1, 123456, "HOSPITAL CHUS SANTIAGO"),
(2, 654321, "HOSPITAL PONTEVEDRA"),
(3,  44234, 'Hospital Central'),
(4, 4321, 'Hospital Norte'),
(5, 5678, 'Hospital Sur'),
(6, 8765, 'Hospital del Este');


INSERT INTO medico (id, especialidad, hospital_id) VALUES 
(1, "CARDIOLOGO", 1),
(2, "CARDIOLOGO", 2);







