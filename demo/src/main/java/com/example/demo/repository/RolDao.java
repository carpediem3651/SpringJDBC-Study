package com.example.demo.repository;

import com.example.demo.domain.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoleDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsertOperations insertAction;

//    생성자주입
    public RoleDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource); // DataSource를 넣는다.
        insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("role");
    }

//INSERT
    public boolean addRole(Role role) {
//        String sql = "INSERT INTO role(role_id, name) VALUE(?, ?))";
//        int result = jdbcTemplate.update(sql, role.getRoleId(), role.getName()); //update메소드는 inset, update, delete SQL문을 실행할 떄 사용한다.
//        return result ==1;
//         role은 프로퍼티, roleid, name
//        INSERT INTO role(role_id, name) VALUES(:roleId, :name)
//        위와 같은 SQL을 SimpleJdbcInsert가 내부적으로 만든다
        SqlParameterSource params = new BeanPropertySqlParameterSource(role); //role 객체의 필드명과 테이블의 컬럼명이 같아야 한다.
        int result = insertAction.execute(params);
        return result == 1;
    }
//DELETE
    public boolean deleteRole(int roleId) {
        String sql = "DELETE FROM role WHERE role_id = :roleId";
        SqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
        int result = jdbcTemplate.update(sql, params);
        return result == 1;
    }

//SELECT
    // 한 건의 데이터르 읽어옴
    public Role getRole(int roleId) {
        String sql = "SELECT role_id, name FROM role WHERE role_id = :roleId";
        // queryForObject는 1건 또는 0건을 읽어오는 메소드
        // queryForObject(String sql, RowMapper<T> rowMapper(한 건의 데이터를 객체에 담아서 리턴, 매핑/바인딩), @Nullable Object... args)
        try {
            SqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
            RowMapper<Role> roleRowMapper = BeanPropertyRowMapper.newInstance(Role.class);
            return jdbcTemplate.queryForObject(sql, params , roleRowMapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // 여러건의 데이터를 불러옴
    public List<Role> getRoles() {
        String sql = "SELECT role_id, name FROM role ODER BY role_id DESC";
//      query메소드는 여러건의 결과를 구할 떄 사용하는 메소드
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Role role = new Role();
            role.setRoleId(rs.getInt("role_id"));
            role.setName(rs.getString("name"));
            return role;
            }
        );
    }
}

//데이터를 한 건 읽어오는 것을 성공한 것을 가정하고, 한 건의 데이터를 Roll객체에 담아서 리턴.
//class RoleRowMapper implements RowMapper<Role> {
//    @Override
//    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
//        Role role = new Role(); //DTO
//        role.setRoleId(rs.getInt("role_id"));
//        role.setName(rs.getString("name"));
//        return role;
//    }
//}
