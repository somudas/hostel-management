package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Member;
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
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Member.class));
    }
}
