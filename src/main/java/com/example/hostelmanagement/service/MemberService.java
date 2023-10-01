package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.ComplaintDao;
import com.example.hostelmanagement.dao.MemberDao;
import com.example.hostelmanagement.model.Complaint;
import com.example.hostelmanagement.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberDao memberDao;
    private final ComplaintDao complaintDao;

    @Autowired
    public MemberService(MemberDao memberDao, ComplaintDao complaintDao) {
        this.memberDao = memberDao;this.complaintDao=complaintDao;
    }

    public int insertMember(Member m) {
        return memberDao.insertMember(m);
    }

    public List<Member> viewAll() {
        return memberDao.viewAll();
    }

    public List<Complaint> getAllComplaintsOfMember(Member m){
        return complaintDao.getComplaintsOfMember(m.getMid(),m.getRole());
    }
}

