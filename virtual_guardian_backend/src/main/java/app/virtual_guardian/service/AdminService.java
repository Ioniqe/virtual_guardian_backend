package app.virtual_guardian.service;

import app.virtual_guardian.dto.builder.AdminBuilder;
import app.virtual_guardian.entity.Admin;
import app.virtual_guardian.entity.User;
import app.virtual_guardian.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void insertAdmin(User user){
        Admin admin = AdminBuilder.toEntity(user);
        adminRepository.save(admin);
    }

}
