package com.is.evaluation.service.impl;

import com.is.evaluation.model.entity.Role;
import com.is.evaluation.model.repository.RoleRepository;
import com.is.evaluation.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository ){
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRolesByIds(Long[] pRolesId) {
        return roleRepository.getRolesByIds(pRolesId);
    }

    @Override
    public List<Role> getRolesByState(String pState) {
        return roleRepository.getRolesByState(pState);
    }

    @Override
    public List<Role> getUserRolesByUserId(Long pUserId) {
        return roleRepository.getUserRolesByUserId(pUserId);
    }

    @Override
    public Role getRolesByName(String pName) {
        return roleRepository.getRolesByName(pName);
    }
}
