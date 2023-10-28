package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.*;
import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.Message;
import com.example.hostelmanagement.model.MessageGroup;
import com.example.hostelmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Message addMessage(Message msg){
        Message addedMessage=messageDao.addMessage(msg);
        groupDao.updateUnreadCnt(addedMessage.getGrpId());
        return addedMessage;
    }
    @Transactional
    public int createGroup(MessageGroup grp){
        Integer grpId=groupDao.addGroup(grp);
        for(Member member: grp.getMembers())
            groupDao.addGroupMember(grpId,member);
        return grpId;
    }

    public List<MessageGroup> getAllGroups(Integer mid, String role){
        List<MessageGroup> messageGroupList = new ArrayList<MessageGroup>();
        List<MessageGroup> messageGroups = memberDao.getAllGroups(mid, role);
        for (MessageGroup grp : messageGroups) {
            MessageGroup msgGrp = groupDao.getGroupById(grp.getGrpId());
            msgGrp.setMessages(messageDao.getAllMessagesOfAGroup(grp.getGrpId()));
            msgGrp.setMembers(groupDao.getAllMembers(grp.getGrpId()));
            msgGrp.setUnreadCnt(grp.getUnreadCnt());
            messageGroupList.add(msgGrp);
        }
        return messageGroupList;
    }
}
