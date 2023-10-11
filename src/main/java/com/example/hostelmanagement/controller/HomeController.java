package com.example.hostelmanagement.controller;

import org.springframework.ui.Model;
import com.example.hostelmanagement.model.LoginForm;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginContext;

@Controller
public class HomeController {
    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String err, Model model) {
        model.addAttribute("error", err);
        return "login";
    }


}
