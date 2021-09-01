package com.is.evaluation.service.impl;

import com.is.evaluation.exception.Message;
import com.is.evaluation.exception.MessageDescription;
import com.is.evaluation.model.entity.Role;
import com.is.evaluation.model.entity.User;
import com.is.evaluation.model.repository.UserRepository;
import com.is.evaluation.service.RoleService;
import com.is.evaluation.service.UserService;
import com.is.evaluation.service.dto.role.RoleQueryDto;
import com.is.evaluation.service.dto.user.UserAddDto;
import com.is.evaluation.service.dto.user.UserQueryDto;
import com.is.evaluation.service.dto.user.UserQueryPageableDto;
import com.is.evaluation.service.dto.user.UserUpdateDto;
import com.is.evaluation.util.Constants;
import com.google.common.base.Strings;
import com.is.evaluation.util.Security;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserQueryPageableDto getUsers(String pState, int pPage, int pRowsNumber) {
        UserQueryPageableDto vUserQueryPageableDto = new UserQueryPageableDto();
        if (Strings.isNullOrEmpty(pState)) {
            throw Message.GetBadRequest(MessageDescription.stateNullOrEmpty,null);
        }
        long vTotalRows = userRepository.getCountUsersByState(pState);
        if (vTotalRows > 0) {
            List<User> vUserList = userRepository.getUsersPageableByState(pState, PageRequest.of(pPage, pRowsNumber));
            List<UserQueryDto> vUserQueryDtoList = new ArrayList<>();
            for (User vUser : vUserList) {
                UserQueryDto vUserQueryDto = new UserQueryDto();
                BeanUtils.copyProperties(vUser, vUserQueryDto);
                vUserQueryDtoList.add(vUserQueryDto);
            }
            vUserQueryPageableDto.setTotalRows(vTotalRows);
            vUserQueryPageableDto.setUserQueryDtoList(vUserQueryDtoList);
        } else {
            vUserQueryPageableDto.setTotalRows(0);
        }
        return vUserQueryPageableDto;
    }

    @Override
    public UserQueryDto addUser(UserAddDto pUserAddDto) {
        User vUser = userRepository.getUserByUsername(pUserAddDto.getUsername()).orElse(null);
        if (vUser != null) {
            Object[] obj = {"name of user", pUserAddDto.getUsername()};
            throw Message.GetBadRequest(MessageDescription.repeated, obj);
        }
        vUser = new User();
        BeanUtils.copyProperties(pUserAddDto, vUser);
        vUser.setPassword(passwordEncoder.encode(pUserAddDto.getPassword()));
        Role vRole = roleService.getRolesByName(Constants.ROLE_USER);
        List<Role> vRoleList = new ArrayList<>();
        vRoleList.add(vRole);
        vUser.setRoles(vRoleList);
        vUser.setCompleted(false);
        userRepository.save(vUser);
        UserQueryDto vUserQueryDto = new UserQueryDto();
        BeanUtils.copyProperties(vUser, vUserQueryDto);
        return vUserQueryDto;
    }

    @Override
    public UserQueryDto completeUser( UserUpdateDto pUserUpdateDto) {

        User vUser = userRepository.getUserByUsername(Security.getUserOfAuthenticatedUser()).orElse(null);
        if (vUser == null) {
            Object[] obj = {"User", "username", String.valueOf(Security.getUserOfAuthenticatedUser())};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        BeanUtils.copyProperties(pUserUpdateDto, vUser);
        vUser.setCompleted(true);
        userRepository.save(vUser);
        UserQueryDto vUserQueryDto = new UserQueryDto();
        BeanUtils.copyProperties(vUser, vUserQueryDto);
        return vUserQueryDto;
    }

    @Override
    public void deleteUser(long pUserId) {
        User vUser = userRepository.getUserById(pUserId).orElse(null);
        if (vUser == null) {
            Object[] obj = {"User", "id", String.valueOf(pUserId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        vUser.setDeletedDate(new Date());
        vUser.setState(Constants.STATE_DELETED);
        userRepository.save(vUser);
    }

    @Override
    public User getUserByUserName(String pUserName) {
        User user = userRepository.getUserByUsername(pUserName).orElse(null);
        if (user == null) {
            Object[] obj = {"User", "username", String.valueOf(pUserName)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        return user;
    }

    @Override
    public List<RoleQueryDto> getUserRolesByUserId(long pUserId) {
        List<Role> vRoleList = roleService.getRolesByState(Constants.STATE_ACTIVE);
        List<Role> vUserRoleList = roleService.getUserRolesByUserId(pUserId);
        List<RoleQueryDto> vRoleQueryDtoList = new ArrayList<>();
        for (Role vRole : vRoleList) {
            RoleQueryDto vRoleQueryDto = new RoleQueryDto();
            vRoleQueryDto.setId(vRole.getId());
            vRoleQueryDto.setRole(vRole.getRole());
            vRoleQueryDto.setDescription(vRole.getDescription());
            Role vUserRole = vUserRoleList.stream().filter(x -> x.getId() == vRole.getId()).
                    findFirst().orElse(null);
            if (vUserRole == null) {
                vRoleQueryDto.setAssigned(false);
            } else {
                vRoleQueryDto.setAssigned(true);
            }
            vRoleQueryDtoList.add(vRoleQueryDto);
        }
        return vRoleQueryDtoList;
    }

    @Override
    public void addRolesByUserId(long pUserId, Long[] roleIds) {
        User vUser = userRepository.getUserById(pUserId).orElse(null);
        if (vUser == null) {
            Object[] obj = {"User", "id", String.valueOf(pUserId)};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }        List<Role> vRoleList = roleService.getRolesByIds(roleIds);
        vUser.setRoles(vRoleList);
        userRepository.save(vUser);
    }

    @Override
    public User getUserByUsername(String pUsername) {
        return userRepository.getUserByUsername(pUsername).orElse(null);
    }
}
