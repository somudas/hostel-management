package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.MessageGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, grp.getName());
            ps.setString(2, grp.getDescription());
            ps.setInt(3, grp.getAdminId());
            ps.setString(4, grp.getAdminRole());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }


    public int addGroupMember(Integer grpId, Member member) {
        final String sql = "INSERT INTO GROUP_MEMBERSHIP(grpId, memberId, memberRole) values(?,?,?)";
        int res = jdbcTemplate.update(sql,
                grpId,
                member.getMid(),
                member.getRole()
        );
        return res;
    }

    public MessageGroup getGroupById(Integer grpId) {
        final String sql = String.format("SELECT * from MESSAGE_GROUP where grpId=%d", grpId);
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MessageGroup.class));
    }

    public List<Member> getAllMembers(Integer grpId) {
        final String sql = String.format("select * from MEMBERS where (mid, role) in (select memberId, memberRole from GROUP_MEMBERSHIP where grpId = %d)", grpId);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Member.class));
    }

    public void updateUnreadCnt(Integer grpId)
    {
        final String sql = String.format("UPDATE GROUP_MEMBERSHIP SET unreadCnt=unreadCnt+1 where grpId=%d",grpId);
        jdbcTemplate.update(sql);
    }
}
