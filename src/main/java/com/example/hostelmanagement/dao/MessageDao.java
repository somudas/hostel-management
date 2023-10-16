package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Complaint;
import com.example.hostelmanagement.model.Member;
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
    private final MemberDao memberDao;
    @Autowired
    public MessageDao(JdbcTemplate jdbcTemplate, MemberDao memberDao) {
        this.jdbcTemplate = jdbcTemplate;this.memberDao=memberDao;
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
        System.out.println("DEBUG: "+generatedKey);
        final String sql1 = String.format("select * from MESSAGES where msgId=%d", generatedKey);
        Message addedMessage = jdbcTemplate.queryForObject(sql1,new BeanPropertyRowMapper<>(Message.class));
        System.out.println("DEBUG: "+ addedMessage.getSentById() + addedMessage.getSentByRole());
        Member sentByUser = memberDao.getMember(addedMessage.getSentById(), addedMessage.getSentByRole());
        addedMessage.setFirstname(sentByUser.getFirstname());
        addedMessage.setLastname(sentByUser.getLastname());
        return addedMessage;
    }

    public List<Message> getAllMessagesOfAGroup(Integer grpId) {
        final String sql = String.format("SELECT * from MESSAGES where grpId=%d order by sentAt",grpId);
        List<Message> messages=jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Message.class));
        for(Message message: messages)
        {
            Member sentByUser=memberDao.getMember(message.getSentById(), message.getSentByRole());
            message.setFirstname(sentByUser.getFirstname());
            message.setLastname(sentByUser.getLastname());
        }
        return messages;
    }

    public Integer updateUnreadCnt(Integer grpId, Integer mid, String role)
    {
        final String sql = String.format("UPDATE GROUP_MEMBERSHIP SET unreadCnt=0 where grpId=%d and memberId=%d and memberRole='%s'",grpId,mid,role);
        return jdbcTemplate.update(sql);
    }
}
