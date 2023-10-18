package com.example.hostelmanagement.dao;

import com.example.hostelmanagement.model.Inventory;
import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.Message;
import com.example.hostelmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class InventoryDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InventoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertItem(Inventory inventory) {
        final String sql = "INSERT INTO inventory(itemName, quantity, thresholdQuantity) values(?, ?, ?)";
        int res = jdbcTemplate.update(sql,
                inventory.getItemName(),
                inventory.getQuantity(),
                inventory.getThresholdQuantity()
        );

        return res;
    }

    public int insertItemByItemName(String itemName) {
        final String sql = String.format("INSERT INTO inventory(itemName) values('%s')", itemName);
        return jdbcTemplate.update(sql);
    }

    public int addQuantity(Inventory inventory, int addedQuantity) {
        final String sql = "update inventory set @quantity = ? where itemId = ?";
        return jdbcTemplate.update(sql, addedQuantity, inventory.getItemId());
    }

    public int addQuantityByItemId(int itemId, int addedQuantity) {
        final String sql = "update inventory set quantity = ? where itemId = ?";
        return jdbcTemplate.update(sql, addedQuantity, itemId);
    }

    public int deleteItem(Inventory inventory) {
        final String sql = "delete from inventory where itemId = ?";
        return jdbcTemplate.update(sql, inventory.getItemId());
    }

    public int deleteByItemId(int itemId) {
        final String sql = "delete from inventory where itemId = ?";
        return jdbcTemplate.update(sql, itemId);
    }

    public List<Inventory> getAllInventoryData() {
        final String sql = "SELECT * from inventory";
        List<Inventory> inventoryList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Inventory.class));
        return inventoryList;
    }

}
