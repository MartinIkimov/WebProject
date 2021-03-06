package com.example.EverExpanding.init;

import com.example.EverExpanding.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RolesInit implements CommandLineRunner {

    private final RoleService roleService;

    public RolesInit(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.initRoles();
    }
}
