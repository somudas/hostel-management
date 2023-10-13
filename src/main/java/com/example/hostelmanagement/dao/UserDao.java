package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Complaint;
import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertUser(User user) {
        final String sql = "INSERT INTO users(username, password, enabled) values(?, ?, 1)";
        int res = jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword()
        );
        res &= jdbcTemplate.update("insert into authorities(username, authority) values(?, ?)", user.getUsername(), user.getRole());
        return res;
    }
    public int changeRole(User user, String newRole) {
        final String sql = "update authorities set authority=? where username=?";
        return jdbcTemplate.update(sql, newRole, user.getUsername());
    }
    public boolean findByUsername(String username) {
        final String sql = String.format("select count(*) from users where username=%s", username);
        try{
            int num = jdbcTemplate.queryForObject(sql, Integer.class);
            return num>0;
        }catch (Exception e) {
            return false;
        }


    }
}
