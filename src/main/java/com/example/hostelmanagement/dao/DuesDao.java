package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Dues;
import com.example.hostelmanagement.model.MessageGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DuesDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DuesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addNewDue(Dues dues) {
        final String sql = "INSERT INTO dues(imposedOnId, imposedOnRole, dueDate, dueAmount, dueType) VALUES(?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                dues.getImposedOnId(),
                dues.getImposedOnRole(),
                dues.getDueDate(),
                dues.getDueAmount(),
                dues.getDueType()
        );
    }
    public List<Dues> getAllDues() {
        return jdbcTemplate.query("SELECT * FROM dues", new BeanPropertyRowMapper<>(Dues.class));
    }
    public int removeDue(int idx) {
        final String sql = "delete from dues where dueId=?";
        return jdbcTemplate.update(sql, idx);
    }
    public int getTotalOfUser(Integer mid, String role) {
        try{
        final String sql = String.format("SELECT SUM(dueAmount) FROM dues WHERE imposedOnId=%d and imposedOnRole='%s'", mid, role);
        return jdbcTemplate.queryForObject(sql, Integer.class);
        }
        catch(Exception e) {
            return 0;
        }
    }
    public List<Dues> getAllDuesOfUser(Integer mid, String role) {
        final String sql = String.format("SELECT * FROM dues WHERE imposedOnId=%d and imposedOnRole='%s'", mid, role);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Dues.class));
    }
}
