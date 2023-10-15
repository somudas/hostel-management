package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Message;
import com.example.hostelmanagement.model.MessageGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addGroup(MessageGroup grp) {
        final String sql = "INSERT INTO MESSAGE_GROUP(name, description, adminId, adminRole) values(?,?,?,?)";
//        System.out.println(member.getEmail() + " "+ member.getFirstName() + " " + member.getLastName());
        int res = jdbcTemplate.update(sql,
                grp.getName(),
                grp.getDescription(),
                grp.getAdminId(),
                grp.getAdminRole()
        );
        return res;
    }
    public List<MessageGroup> getAllGroups() {
        final String sql = "SELECT * from MESSAGE_GROUP";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MessageGroup.class));
    }
}
