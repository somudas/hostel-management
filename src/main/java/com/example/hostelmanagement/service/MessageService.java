package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.*;
import com.example.hostelmanagement.model.Message;
import com.example.hostelmanagement.model.MessageGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageDao messageDao;
    private final GroupDao groupDao;

    @Autowired
    public MessageService(MessageDao messageDao, GroupDao groupDao) {
        this.messageDao = messageDao;
        this.groupDao = groupDao;
    }
    public Message addMessage(Message msg){return messageDao.addMessage(msg);}
    public List<Message> getAllMessages(int grpId){return messageDao.getAllMessagesOfAGroup(grpId);}

    public int createGroup(MessageGroup grp){return groupDao.addGroup(grp);}
    public List<MessageGroup> getAllGroups(){return groupDao.getAllGroups();}
}
