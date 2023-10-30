package com.example.hostelmanagement.controller;

import com.example.hostelmanagement.model.*;
import com.example.hostelmanagement.service.ComplaintService;
import com.example.hostelmanagement.service.MemberService;
import com.example.hostelmanagement.service.ServiceService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ServiceController {

    private final ServiceService serviceService;
    private final MemberService memberService;

    @Autowired
    public ServiceController(ServiceService serviceService, MemberService memberService){
        this.serviceService=serviceService;
        this.memberService=memberService;
    }

    @GetMapping("/services")
    private String getServices(Principal principal, Model model){
        User user = memberService.findUser(principal.getName());

        if(user.getRole().equals("WARDEN")){
            model.addAttribute("services", serviceService.getAllServices());
        }
        else {
            model.addAttribute("services", serviceService.getAllServicesAssignedToMember(user.getMid(), user.getRole()));
        }
        model.addAttribute("isAdmin", user.getRole().equals("WARDEN"));
        model.addAttribute("role",user.getRole());
        return "services";
    }

    @PostMapping("/services/add")
    private String addService(Principal principal, Model model,@ModelAttribute Service service){
        model.addAttribute("service", service);
        service.setAssignedToRole("STAFF");
        System.out.println("asd "+ service.getAssignedToId());
        System.out.println("asd "+ service.getAssignedToRole());
        System.out.println(serviceService.addService(service));
        return "redirect:/services";
    }

    @GetMapping("/update/service/{id}")
    @ResponseBody
    private Integer updateService(@PathVariable("id") Integer serviceId){
        Service service= new Service();
        service.setServiceId(serviceId);
        return serviceService.updateServiceStatus(service);
    }

    @GetMapping("/services/add")
    public String addService(Principal principal, Model model) {
        User user = memberService.findUser(principal.getName());
        model.addAttribute("service", new Service());
        model.addAttribute("allMembers",memberService.viewAll());
        model.addAttribute("role",user.getRole());
        return "registerService";
    }
}
