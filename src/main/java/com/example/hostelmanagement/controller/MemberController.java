package com.example.hostelmanagement.controller;

import com.example.hostelmanagement.model.Complaint;
import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.service.MemberService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping("/api/member")
    @ResponseBody
    public List<Member> listAll(){
        return memberService.viewAll();
    }

    @GetMapping("/api/member/complaints")
    @ResponseBody
    public List<Complaint> listAllComplaints(@NotNull @RequestBody Member m){
        return memberService.getAllComplaintsOfMember(m);
    }
    @PostMapping("/api/member/add")
    @ResponseBody
    public String insert(@NotNull @RequestBody Member m)
    {
        if (memberService.insertMember(m) == 1) return "SUCCESS";
        return "FAILED TO INSERT";
    }
}
