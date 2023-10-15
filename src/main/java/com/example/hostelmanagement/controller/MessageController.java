package com.example.hostelmanagement.controller;

import com.example.hostelmanagement.dao.UserDao;
import com.example.hostelmanagement.model.*;
import com.example.hostelmanagement.service.MemberService;
import com.example.hostelmanagement.service.MessageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;


@Controller
public class MessageController {

    private final MessageService messageService;
    private final MemberService memberService;

    @Autowired
    public MessageController(MessageService messageService, MemberService memberService) {
        this.messageService = messageService;
        this.memberService= memberService;
    }

    @MessageMapping("/send/{grpId}")
    @SendTo("/socket/message/receive/{grpId}")
    public Message saveMessage(@DestinationVariable int grpId, Principal principal, @NotNull @RequestBody Message msg){
        User currentUser= memberService.findUser(principal.getName());
        msg.setSentById(currentUser.getMid());
        msg.setSentByRole(currentUser.getRole());
        msg.setGrpId(grpId);
        return messageService.addMessage(msg);
    }
    @PostMapping("/api/create/group")
    @ResponseBody
    public int createGroup(Principal principal,@NotNull @RequestBody MessageGroup grp){
        User currentUser= memberService.findUser(principal.getName());
        grp.setAdminId(currentUser.getMid());
        grp.setAdminRole(currentUser.getRole());
        return messageService.createGroup(grp);
    }
    @GetMapping("/api/get/groups")
    @ResponseBody
    public List<MessageGroup> getAllGroups(Principal principal){
        User currentUser= memberService.findUser(principal.getName());
        return messageService.getAllGroups(currentUser.getMid(), currentUser.getRole());
    }

}
