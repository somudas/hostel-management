package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.ComplaintDao;
import com.example.hostelmanagement.model.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {
    private final ComplaintDao complaintDao;
    @Autowired
    public ComplaintService(ComplaintDao complaintDao) {
        this.complaintDao = complaintDao;
    }
    public int addComplaint(Complaint complaint) {
        return complaintDao.insertComplaint(complaint);
    }
    public List<Complaint> getComplaintsOfUser(Integer mid, String role) {
        return complaintDao.getComplaintsOfMember(mid, role);
    }
    public List<Complaint> getAll() {
        return complaintDao.getAll();
    }
    public int resolveComplaint(Integer cmpId) {
        return complaintDao.resolveComplaint(cmpId);
    }
    public int addFeedback(Integer cmpId, String content) {
        return complaintDao.addFeedback(cmpId, content);
    }
}
