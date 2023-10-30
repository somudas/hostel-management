package com.example.hostelmanagement.controller;

import com.example.hostelmanagement.model.Inventory;
import com.example.hostelmanagement.model.User;
import com.example.hostelmanagement.service.InventoryService;
import com.example.hostelmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {
    private final InventoryService inventoryService;
    private final MemberService memberService;
    @Autowired
    public InventoryController(MemberService memberService, InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.memberService = memberService;
    }

    @GetMapping("/inventory")
    public String inventory(Principal principal, Model model) {
        User currentUser= memberService.findUser(principal.getName());
        if(!currentUser.getRole().equals("WARDEN")) {
            return "redirect:/error";
        }
        List<Inventory> inventoryList = inventoryService.getAllInventoryData();
        model.addAttribute("inventoryList", inventoryList);
        model.addAttribute("inventory", new Inventory());
        model.addAttribute("role",currentUser.getRole());
        return "inventory";
    }

    @PostMapping("/inventory/addItem")
    public String addItem(@ModelAttribute Inventory inventory, Model model) {
        model.addAttribute("inventory", inventory);
        System.out.println(inventory.getItemName());
        System.out.println(inventory.getQuantity());
        if(inventoryService.insertItem(inventory) == 1)
            return "redirect:/inventory";
        return "redirect:/error";
    }

    @PostMapping("/inventory/addquantity")
    @ResponseBody
    public int addQuantity(@RequestBody Inventory inventory) {
        int res = inventoryService.addQuantityByItemId(Math.toIntExact(inventory.getItemId()), inventory.getQuantity());
        return res;
    }

    @PostMapping("/inventory/deleteitem")
    public String deleteItem(@RequestParam("itemId") int itemId) {
        int res = inventoryService.deleteByItemId(itemId);
        return "redirect:/inventory";
    }

}
