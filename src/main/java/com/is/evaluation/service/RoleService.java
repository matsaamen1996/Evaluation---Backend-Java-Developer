package com.is.evaluation.service;

import com.is.evaluation.model.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRolesByIds(Long[] pRolesId);
    List<Role> getRolesByState(String pState);
    List<Role> getUserRolesByUserId(Long pUserId);
    Role getRolesByName(String pName);
}
