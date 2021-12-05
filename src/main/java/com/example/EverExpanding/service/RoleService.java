package com.example.EverExpanding.service;

import com.example.EverExpanding.model.entity.Role;
import com.example.EverExpanding.model.entity.enums.RoleNameEnum;

import java.util.List;

public interface RoleService {
    Role findByRole(RoleNameEnum admin);

    void initRoles();
}
