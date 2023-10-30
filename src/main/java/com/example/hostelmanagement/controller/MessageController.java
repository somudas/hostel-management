package com.example.hostelmanagement.controller;

import com.example.hostelmanagement.dao.MemberDao;
import com.example.hostelmanagement.dao.MessageDao;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MessageController {

    private final MessageService messageService;
    private final MemberService memberService;
    private final MemberDao memberDao;
    private final MessageDao messageDao;
    @Autowired
    public MessageController(MessageService messageService, MemberService memberService, MemberDao memberDao, MessageDao messageDao) {
        this.messageService = messageService;
        this.memberService= memberService;
        this.memberDao = memberDao;
        this.messageDao =messageDao;
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
    public int createGroup(Principal principal,@NotNull @RequestBody MessageGroup newGroup){
        User currentUser= memberService.findUser(principal.getName());
        newGroup.setAdminId(currentUser.getMid());
        newGroup.setAdminRole(currentUser.getRole());
        System.out.println(newGroup);
        List<Member> memberList = new ArrayList<Member>(newGroup.getMembers());

        Member currentMember=new Member();
        currentMember.setRole(currentUser.getRole());
        currentMember.setMid(currentUser.getMid());
        memberList.add(currentMember);

        newGroup.setMembers(memberList);
        return messageService.createGroup(newGroup);

    }
    @GetMapping("/chats")
    public String getAllGroups(Principal principal, Model model){
        User currentUser= memberService.findUser(principal.getName());
        System.out.println(currentUser.getRole());
        List<MessageGroup> msgGrps = messageService.getAllGroups(currentUser.getMid(), currentUser.getRole());
        List<Member> members = memberService.viewAll();
        members.removeIf(mem -> mem.getMid().equals(currentUser.getMid()) && mem.getRole().equals(currentUser.getRole()));
        model.addAttribute("messageGroups", msgGrps);
        model.addAttribute("allMembers", members);
        model.addAttribute("role", currentUser.getRole());
        return "chat";
    }
    @GetMapping("/api/groups")
    @ResponseBody
    public List<Integer> getAllGroupIds(Principal principal, Model model){
        User currentUser= memberService.findUser(principal.getName());
        List<MessageGroup> allGroups= memberDao.getAllGroups(currentUser.getMid(), currentUser.getRole());
        List<Integer> groupIds = new ArrayList<Integer>();
        for(MessageGroup grp: allGroups)
            groupIds.add(grp.getGrpId());
        return groupIds;
    }

    @GetMapping("/api/update-unread-cnt/{grpId}")
    @ResponseBody
    public Integer updateUnreadCnt(Principal principal, @PathVariable Integer grpId){
        User currentUser= memberService.findUser(principal.getName());
        return messageDao.updateUnreadCnt(grpId,currentUser.getMid(), currentUser.getRole());
    }
}
