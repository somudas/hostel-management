package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.ComplaintDao;
import com.example.hostelmanagement.dao.MemberDao;
import com.example.hostelmanagement.dao.UserDao;
import com.example.hostelmanagement.model.Complaint;
import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {
    private final MemberDao memberDao;
    private final ComplaintDao complaintDao;
    private final UserDao userDao;

    @Autowired
    public MemberService(MemberDao memberDao, ComplaintDao complaintDao, UserDao userDao) {
        this.memberDao = memberDao;
        this.complaintDao = complaintDao;
        this.userDao = userDao;
    }

    @Transactional
    public int insertMember(Member m, User user) {
         return memberDao.insertMember(m) & userDao.insertUser(user);
    }
    public int changeRole(User user, String newRole) {
        return userDao.changeRole(user, newRole);
    }
    public User findUser(String username) {
        return userDao.findByUsername(username);
    }
    public List<Member> viewAll() {
        return memberDao.viewAll();
    }

    public List<Complaint> getAllComplaintsOfMember(Member m){
        return complaintDao.getComplaintsOfMember(m.getMid(),m.getRole());
    }
    public Member getMember(Integer mid, String role){
        return memberDao.getMember(mid, role);
    }
    public List<String> getAllRoles() {
        return memberDao.getAllRoles();
    }
}

