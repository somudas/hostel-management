package com.example.hostelmanagement.service;

import com.example.hostelmanagement.dao.MemberDao;
import com.example.hostelmanagement.dao.ServiceDao;
import com.example.hostelmanagement.model.Member;
import com.example.hostelmanagement.model.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceDao serviceDao;
    private final MemberDao memberDao;

    @Autowired
    public ServiceService(ServiceDao serviceDao, MemberDao memberDao){
        this.serviceDao=serviceDao;this.memberDao=memberDao;
    }

    public Integer addService(Service service){return serviceDao.addService(service);}
    public List<Service> getAllServicesAssignedToMember(Integer memberId, String memberRole){
        List <Service> services=serviceDao.getAllServicesAssignedToMember(memberId,memberRole);

        for (Service service: services){
            Member assignedStaff=memberDao.getMember(service.getAssignedToId(),service.getAssignedToRole());
            service.setFirstname(assignedStaff.getFirstname());
            service.setLastname(assignedStaff.getLastname());
        }

        return services;
    }

    public Integer updateServiceStatus (Service service){
        return serviceDao.updateServiceStatus(service.getServiceId());
    }
    public List<Service> getAllServices(){
        List <Service> services=serviceDao.getAllServices();

        for (Service service: services){
            Member assignedStaff=memberDao.getMember(service.getAssignedToId(),service.getAssignedToRole());
            service.setFirstname(assignedStaff.getFirstname());
            service.setLastname(assignedStaff.getLastname());
        }

        return services;
    }
}
