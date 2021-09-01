package com.is.evaluation.controller;

import com.is.evaluation.service.UserService;
import com.is.evaluation.service.dto.user.UserAddDto;
import com.is.evaluation.service.dto.user.UserQueryDto;
import com.is.evaluation.service.dto.user.UserQueryPageableDto;
import com.is.evaluation.service.dto.user.UserUpdateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "Endpoints for user management")
@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation(value = "Gets a list of all users for state", authorizations = @Authorization(value = "Bearer"))
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public UserQueryPageableDto getUsers(@ApiParam(value = "User Status") @RequestParam String state,
                                         @ApiParam(value = "Number of page to consult") @RequestParam int page,
                                         @ApiParam(value = "Number of records to consult") @RequestParam int rowsNumber){
        return userService.getUsers(state, page, rowsNumber);
    }



    @ApiOperation(value = "Comnplete a user", authorizations = @Authorization(value = "Bearer"))
    @PutMapping(path = "complete")
    @ResponseStatus(HttpStatus.CREATED)
    public UserQueryDto updateUser(@ApiParam(value = "Data to update") @Valid @RequestBody UserUpdateDto pUserUpdateDto){
        return userService.completeUser(pUserUpdateDto);
    }


    @ApiOperation(value = "Create a user", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserQueryDto addUser(@ApiParam(value = "User data") @Valid @RequestBody UserAddDto userAddDto){
        return userService.addUser(userAddDto);
    }



    @ApiOperation(value = "Delete user", authorizations = @Authorization(value = "Bearer"))
    @DeleteMapping(path = "{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@ApiParam(value = "User indentification") @PathVariable Long userId){
        userService.deleteUser(userId);
    }


    @ApiOperation(value = "Assign role to user", authorizations = @Authorization(value = "Bearer"))
    @PostMapping(path = "{userId}/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRolesByUserId(@ApiParam(value = "User identification") @PathVariable Long userId,
                                 @ApiParam(value = "Roles identification") @Valid @RequestBody Long[] roleIDs) {
        userService.addRolesByUserId(userId, roleIDs);
    }


}
