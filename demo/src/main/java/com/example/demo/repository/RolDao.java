package com.example.demo.repository;

import com.example.demo.domain.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class RoleDao {
    private final JdbcTemplate jdbcTemplate;

//    생성자주입
    public RoleDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

//INSERT
    public boolean addRole(Role role) {
        String sql = "INSERT INTO role(role_id, name) VALUE(?, ?))";
        int result = jdbcTemplate.update(sql, role.getRoleId(), role.getName()); //update메소드는 inset, update, delete SQL문을 실행할 떄 사용한다.
        return result ==1;
    }

    public boolean deleteRole(int roleId) {
        String sql = "DELETE FROM role WHERE role_id = ?";
        int result = jdbcTemplate.update(sql, roleId);
        return result == 1;
    }

    public Role getRole(int roleId) {
        String sql = "SELECT role_id, name FROM role WHERE role_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Role role = new Role();
            role.setRoleId(rs.getInt("role_id"));
            role.setName(rs.getString("name"));
            return role;
        }, roleId);
    }

}
