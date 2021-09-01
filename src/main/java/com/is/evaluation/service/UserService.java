package com.is.evaluation.service;

import com.is.evaluation.model.entity.User;
import com.is.evaluation.service.dto.role.RoleQueryDto;
import com.is.evaluation.service.dto.user.UserAddDto;
import com.is.evaluation.service.dto.user.UserQueryDto;
import com.is.evaluation.service.dto.user.UserQueryPageableDto;
import com.is.evaluation.service.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {

    UserQueryPageableDto getUsers(String pState, int pPage, int pRowsNumber);

    UserQueryDto addUser(UserAddDto pUserAddDto);

    void deleteUser(long pUserId);

    User getUserByUserName(String pUserName);

    List<RoleQueryDto> getUserRolesByUserId(long pUserId);

    void addRolesByUserId(long pUserId, Long[] roleIds);

    User getUserByUsername(String pUsername);

    UserQueryDto completeUser (UserUpdateDto pUserUpdateDto);

}
