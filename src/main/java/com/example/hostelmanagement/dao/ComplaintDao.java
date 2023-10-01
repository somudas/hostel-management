package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComplaintDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ComplaintDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertComplaint(Complaint complaint) {
        final String sql = "INSERT INTO COMPLAINTS(title, description, status, feedback, postedById, postedByRole) values(?,?,?,?,?,?)";
//        System.out.println(member.getEmail() + " "+ member.getFirstName() + " " + member.getLastName());
        int res = jdbcTemplate.update(sql,
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getStatus(),
                complaint.getFeedback(),
                complaint.getPostedById(),
                complaint.getPostedByRole()
        );
        return res;
    }
    public List<Complaint> getComplaintsOfMember(Integer MID, String memberRole) {
        final String sql = String.format("SELECT * from COMPLAINTS where postedById=%d and postedByRole='%s'",MID,memberRole );
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Complaint.class));
    }
}
