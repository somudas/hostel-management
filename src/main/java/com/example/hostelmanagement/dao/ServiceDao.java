package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Message;
import com.example.hostelmanagement.model.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class ServiceDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ServiceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer addService (Service service){

        final String sql = "INSERT INTO services(serviceName, assignedToId, assignedToRole) values(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, service.getServiceName());
            ps.setInt(2, service.getAssignedToId());
            ps.setString(3, service.getAssignedToRole());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public List<Service> getAllServices(){
        final String sql="Select * from services";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Service.class));
    }

    public List<Service> getAllServicesAssignedToMember(Integer memberId, String memberRole){
        final String sql=String.format("Select * from services where assignedToId=%d and assignedToRole='%s'",memberId,memberRole);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Service.class));
    }

    public Integer updateServiceStatus (Integer serviceId){
        final String sql=String.format("UPDATE services SET lastUpdatedOn=NOW() where serviceId=%d",serviceId);
        return jdbcTemplate.update(sql);
    }
}
