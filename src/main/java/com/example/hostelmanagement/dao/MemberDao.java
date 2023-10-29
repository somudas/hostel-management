package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.MessageGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertMember(Member member) {
        final String sql = "INSERT INTO MEMBERS(mid, role, firstname, lastname, batch, branch, dateofbirth, email, phonenumber) values(?,?,?,?,?,?,?,?,?)";
//        System.out.println(member.getEmail() + " "+ member.getFirstName() + " " + member.getLastName());
        int res = jdbcTemplate.update(sql,
                member.getMid(),
                member.getRole(),
                member.getFirstname(),
                member.getLastname(),
                member.getBatch(),
                member.getBranch(),
                member.getDateofbirth(),
                member.getEmail(),
                member.getPhonenumber()
                );
        return res;
    }
    public List<Member> viewAll() {
        final String sql = "SELECT * from MEMBERS";
        int x = 4;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Member.class));
    }
    public List<String> getAllRoles() {
        return jdbcTemplate.queryForList("select distinct ROLE from MEMBERS", String.class);
    }
    public List<MessageGroup> getAllGroups(Integer mid, String role) {
        final String sql = String.format("SELECT grpId,unreadCnt from GROUP_MEMBERSHIP where memberId=%d and memberRole='%s'", mid, role);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MessageGroup.class));
    }

    public Member getMember(Integer mid, String role) {
        final String sql = String.format("SELECT * from MEMBERS where mid=%d and role='%s'", mid, role);
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Member.class));
    }
}
