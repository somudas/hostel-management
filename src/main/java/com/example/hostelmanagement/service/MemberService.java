package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.MemberDao;
import com.example.hostelmanagement.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public int insertMember(Member m) {
        return memberDao.insertMember(m);
    }

    public List<Member> viewAll() {
        return memberDao.viewAll();
    }
}

