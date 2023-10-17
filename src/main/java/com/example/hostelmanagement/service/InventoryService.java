package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.ComplaintDao;
import com.example.hostelmanagement.dao.InventoryDao;
import com.example.hostelmanagement.dao.MemberDao;
import com.example.hostelmanagement.dao.UserDao;
import com.example.hostelmanagement.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryDao inventoryDao;
    @Autowired
    public InventoryService(InventoryDao inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    public int insertItem(Inventory inventory) {
        return inventoryDao.insertItem(inventory);
    }

    public int insertItemByItemName(String itemName) {
        return inventoryDao.insertItemByItemName(itemName);
    }

    public int deleteItem(Inventory inventory) {
        return inventoryDao.deleteItem(inventory);
    }

    public int deleteByItemId(int itemId) {
        return inventoryDao.deleteByItemId(itemId);
    }

    public int addQuantityByItemId(int itemId, int addedQuantity) {
        return inventoryDao.addQuantityByItemId(itemId, addedQuantity);
    }

    public int addQuantity(Inventory inventory, int addedQuantity) {
        return inventoryDao.addQuantity(inventory, addedQuantity);
    }

    public List<Inventory> getAllInventoryData() {
        return inventoryDao.getAllInventoryData();
    }
}
