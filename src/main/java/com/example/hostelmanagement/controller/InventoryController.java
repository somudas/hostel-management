package com.example.hostelmanagement.controller;

import com.example.hostelmanagement.model.Inventory;
import com.example.hostelmanagement.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory")
    public String inventory(Model model) {
        List<Inventory> inventoryList = inventoryService.getAllInventoryData();
        model.addAttribute("inventoryList", inventoryList);
        return "inventory";
    }

    @PostMapping("/inventory/additem")
    public String addItem(@RequestParam("itemName") String itemName) {
        int res = inventoryService.insertItemByItemName(itemName);
        return "redirect:/inventory";
    }

    @PostMapping("/inventory/addquantity")
    public String addQuantity(@RequestParam("itemId") int itemId, @RequestParam("addedQuantity") int addedQuantity) {
        int res = inventoryService.addQuantityByItemId(itemId, addedQuantity);
        return "redirect:/inventory";
    }

    @PostMapping("/inventory/deleteitem")
    public String deleteItem(@RequestParam("itemId") int itemId) {
        int res = inventoryService.deleteByItemId(itemId);
        return "redirect:/inventory";
    }

}
