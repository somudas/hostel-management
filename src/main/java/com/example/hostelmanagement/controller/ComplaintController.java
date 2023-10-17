package com.example.hostelmanagement.controller;

import com.example.hostelmanagement.model.Complaint;
import com.example.hostelmanagement.model.Role;
import com.example.hostelmanagement.model.User;
import com.example.hostelmanagement.model.complaintStatus;
import com.example.hostelmanagement.service.ComplaintService;
import com.example.hostelmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
public class ComplaintController {

    private final MemberService memberService;
    private final ComplaintService complaintService;
    @Autowired
    public ComplaintController(MemberService memberService, ComplaintService complaintService) {
        this.memberService = memberService;
        this.complaintService = complaintService;
    }
    private List<Complaint> filterByStatus(Principal principal, complaintStatus complaintStatus) {
        User user = memberService.findUser(principal.getName());
        List<Complaint> listComplaints = complaintService.getComplaintsOfUser(user.getMid(), user.getRole());
        List<Complaint> filtered = new ArrayList<Complaint>();
        listComplaints.stream().filter(complaint -> complaint.getStatus() == complaintStatus).forEach(filtered::add);
        return filtered;
    }
    @GetMapping("/complaints/resolved")
    public String complaint(Principal principal, Model model) {
        model.addAttribute("isResolved", true);
        model.addAttribute("complaints", filterByStatus(principal, complaintStatus.RESOLVED));
        return "complaint";
    }
    @GetMapping("/complaints/unresolved")
    public String complaintUnresolved(Principal principal, Model model) {
        model.addAttribute("isResolved", false);
        model.addAttribute("complaints", filterByStatus(principal, complaintStatus.UNRESOLVED));
        return "complaint";
    }
    @GetMapping("/complaints/register")
    public String registerComplaint(Principal principal, Model model) {
//        model.addAttribute("isResolved", false);
        model.addAttribute("complaint", new Complaint());
        return "registerComplaint";
    }
    @PostMapping("/complaints/register")
    public String registerPost(Principal principal, Model model, @ModelAttribute Complaint complaint) {
        model.addAttribute("complaint", complaint);
        User currentUser= memberService.findUser(principal.getName());
        complaint.setPostedById(currentUser.getMid());
        complaint.setPostedByRole(currentUser.getRole());
        complaint.setStatus(complaintStatus.UNRESOLVED);
        if(complaintService.addComplaint(complaint) == 0) {
            return "redirect:/error";
        }
        return "redirect:/complaints/resolved";
    }

    @PostMapping("/complaints/resolve")
    public String resolve(Principal principal, @RequestParam("id") String cmpId) {
        if(complaintService.resolveComplaint(parseInt(cmpId)) == 1)
            return "redirect:/complaints/resolved";
        return "redirect:/error";
    }


}
