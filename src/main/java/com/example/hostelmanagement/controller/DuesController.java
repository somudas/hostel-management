package com.example.hostelmanagement.controller;

import com.example.hostelmanagement.model.Dues;
import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.User;
import com.example.hostelmanagement.service.DuesService;
import com.example.hostelmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class DuesController {
    private final MemberService memberService;
    private final DuesService duesService;
    @Autowired
    public DuesController(MemberService memberService, DuesService duesService) {
        this.memberService = memberService;
        this.duesService = duesService;
    }
    @GetMapping("/dues")
    public String dues(Principal principal, Model model) {
        User user = memberService.findUser(principal.getName());
        model.addAttribute("isAdmin", user.getRole().equals("WARDEN"));
        if(!user.getRole().equals("WARDEN")) {
            model.addAttribute("filteredDues", duesService.getAllDuesOfUser(user.getMid(), user.getRole()));
            model.addAttribute("totalAmount", duesService.getTotalOfUser(user.getMid(), user.getRole()));
        }else{
            model.addAttribute("allDues", duesService.getAllDues());
        }
        model.addAttribute("role",user.getRole());
        return "dues";
    }
    @GetMapping("/dues/add")
    public String addDues(Principal principal, Model model) {
        User user = memberService.findUser(principal.getName());
        if(!user.getRole().equals("WARDEN")) return "redirect:/dues";
        model.addAttribute("dues", new Dues());
        model.addAttribute("allMembers", memberService.viewAll());
        List<String> allRoles = memberService.getAllRoles();
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("role",user.getRole());
        return "registerDues";
    }
    @PostMapping("/dues/add")
    public String addPostDues(@ModelAttribute Dues dues, Model model) {
        model.addAttribute("dues", dues);
        duesService.addDue(dues);
        return "redirect:/dues";
    }

    @GetMapping("/dues/remove/{idx}")
    public int removeDue(@PathVariable("idx") Integer dueId) {
        return duesService.removeDue(dueId);
    }
}
