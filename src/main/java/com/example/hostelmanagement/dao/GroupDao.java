package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Member;
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
    public MessageGroup getGroupById(Integer grpId) {
        final String sql = String.format("SELECT * from MESSAGE_GROUP where grpId=%d", grpId);
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MessageGroup.class));
    }

    public List<Member> getAllMembers(Integer grpId) {
        final String sql = String.format("select * from members" +
                "    where (mid, role) in (select memberId, memberRole from GROUP_MEMBERSHIP" +
                "    where grpId = %d);", grpId);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Member.class));
    }
}
