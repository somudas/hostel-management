package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.*;
import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.Message;
import com.example.hostelmanagement.model.MessageGroup;
import com.example.hostelmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final MessageDao messageDao;
    private final GroupDao groupDao;
    private final MemberDao memberDao;
    @Autowired
    public MessageService(MessageDao messageDao, GroupDao groupDao, MemberDao memberDao) {
        this.messageDao = messageDao;
        this.groupDao = groupDao;
        this.memberDao = memberDao;
    }
    public Message addMessage(Message msg){return messageDao.addMessage(msg);}
    public List<Message> getAllMessages(int grpId){return messageDao.getAllMessagesOfAGroup(grpId);}


    public int createGroup(MessageGroup grp){
        Integer grpId=groupDao.addGroup(grp);
        for(Member member: grp.getMembers())
            groupDao.addGroupMember(grpId,member);
        return grpId;
    }

    public List<MessageGroup> getAllGroups(Integer mid, String role){
        List<MessageGroup> messageGroupList = new ArrayList<MessageGroup>();
        List<Integer> messageGroupIds = memberDao.getAllGroups(mid, role);
        for (Integer grpId : messageGroupIds) {
            MessageGroup msgGrp = groupDao.getGroupById(grpId);
            msgGrp.setMessages(messageDao.getAllMessagesOfAGroup(grpId));
            msgGrp.setMembers(groupDao.getAllMembers(grpId));
            messageGroupList.add(msgGrp);
        }
        return messageGroupList;
    }
}
