package com.example.hostelmanagement.controller;

import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.User;
import com.example.hostelmanagement.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    @Autowired
    public HomeController(PasswordEncoder passwordEncoder, MemberService memberService) {
        this.passwordEncoder = passwordEncoder;
        this.memberService = memberService;
    }
    @GetMapping("/")
    public String home(Principal principal, Model model) {
        if (principal == null) model.addAttribute("loggedIn", false);
        else model.addAttribute("loggedIn", true);
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String err, Model model) {
        model.addAttribute("error", err);
        return "login";
    }


    @GetMapping("/register")
    public String register(@RequestParam(name="msg", required = false) String msg, @RequestParam(value="error", required=false) String err, Model model) {
        model.addAttribute("error", err);
        model.addAttribute("error_message", msg);
        model.addAttribute("member", new Member());
        return "register";
    }

    @PostMapping("/register")
    public void postRegister(@RequestParam(name="password1", required = false) String password1,
                             @RequestParam(name="password2", required = false) String password2,
                             @ModelAttribute @Valid Member member,
                             Model model,
                             ModelMap modelMap,
                             HttpServletResponse httpServletResponse
    ) throws Exception {

        if(!password1.equals(password2)) {
            httpServletResponse.sendRedirect("/register?error&msg=passwords%20didn't%20match");
            return;
        }

        model.addAttribute("member", member);

        String username = member.getRole() + member.getMid();
        String password = passwordEncoder.encode(password1);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(member.getRole());
        user.setMid(member.getMid());
        if (memberService.findUser(username)!=null) {
            httpServletResponse.sendRedirect("/register?error&msg=user%20already%20exists");
            return;
        }

        memberService.insertMember(member, user);
        httpServletResponse.sendRedirect("/login");
    }


    // REMOVE
    @GetMapping("/student")
    @ResponseBody
    public String stud(Principal principal) {
        System.out.println(principal.getName());return "<h1>sensititve info</h1>";
    }
}
