package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.DuesDao;
import com.example.hostelmanagement.model.Dues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DuesService {
    private final DuesDao duesDao;
    @Autowired
    public DuesService(DuesDao duesDao) {
        this.duesDao = duesDao;
    }

    public int addDue(Dues dues) {
        return duesDao.addNewDue(dues);
    }

    public int removeDue(int idx) {
        return duesDao.removeDue(idx);
    }
    public int getTotalOfUser(Integer mid, String role) {
        return duesDao.getTotalOfUser(mid, role);
    }
    public List<Dues> getAllDues() {
        return duesDao.getAllDues();
    }
    public List<Dues> getAllDuesOfUser(Integer mid, String role) {
        return duesDao.getAllDuesOfUser(mid, role);
    }

}
