DROP TABLE IF EXISTS roles_permissions_test;
DROP TABLE IF EXISTS user_roles_test;
DROP TABLE IF EXISTS permissions_test;
DROP TABLE IF EXISTS roles_test;



SELECT
    u.email AS username,
    GROUP_CONCAT(DISTINCT r.role_name ORDER BY r.role_name SEPARATOR ', ') AS roles,
    GROUP_CONCAT(DISTINCT p.name ORDER BY p.name SEPARATOR ', ') AS permissions
FROM user u
JOIN user_roles_test ur ON u.id = ur.user_id
JOIN roles_test r ON ur.role_id = r.id
JOIN roles_permissions_test rp ON r.id = rp.role_id
JOIN permissions_test p ON rp.permission_id = p.id
GROUP BY u.id, u.email
ORDER BY u.email;
