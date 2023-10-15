package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Complaint;
import com.example.hostelmanagement.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.sql.Statement;

@Repository
public class MessageDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public MessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Message addMessage(Message msg) {
        final String sql = "INSERT INTO MESSAGES(grpId, content, sentById, sentByRole) values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, msg.getGrpId());
            ps.setString(2, msg.getContent());
            ps.setInt(3, msg.getSentById());
            ps.setString(4, msg.getSentByRole());
            return ps;
        }, keyHolder);

        Integer generatedKey=keyHolder.getKey().intValue();
        System.out.println(generatedKey);
        final String sql1 = String.format("select * from MESSAGES where msgId=%d", generatedKey);
        return jdbcTemplate.queryForObject(sql1,new BeanPropertyRowMapper<>(Message.class));
    }

    public List<Message> getAllMessagesOfAGroup(Integer grpId) {
        final String sql = String.format("SELECT * from MESSAGES where grpId=%d order by sentAt",grpId);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Message.class));
    }
}
