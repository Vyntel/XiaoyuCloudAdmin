package com.xiaoyu.module.system.controller;

import com.xiaoyu.api.system.dto.UserDTO;
import com.xiaoyu.api.system.vo.UserVO;
import com.xiaoyu.common.core.result.PageResult;
import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.system.entity.SystemUser;
import com.xiaoyu.module.system.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户 Controller
 */
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SystemUserController {

    private final SystemUserService systemUserService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    public Result<PageResult<UserVO>> getUserPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "deptId", required = false) Long deptId) {
        List<UserVO> list = systemUserService.getUserPage(pageNum, pageSize, username, nickname, status, deptId);
        return Result.success(new PageResult<>(list, 0L, pageSize, pageNum));
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{userId}")
    public Result<UserVO> getUserById(@PathVariable("userId") Long userId) {
        return Result.success(systemUserService.getUserById(userId));
    }

    /**
     * 根据用户名获取用户信息
     */
    @GetMapping("/username/{username}")
    public Result<UserVO> getUserByUsername(@PathVariable("username") String username) {
        return Result.success(systemUserService.getUserByUsername(username));
    }

    /**
     * 新增用户
     */
    @PostMapping
    public Result<Long> createUser(@RequestBody UserDTO userDTO) {
        SystemUser user = convertToEntity(userDTO);
        return Result.success(systemUserService.createUser(user));
    }

    /**
     * 修改用户
     */
    @PutMapping
    public Result<Void> updateUser(@RequestBody UserDTO userDTO) {
        SystemUser user = convertToEntity(userDTO);
        systemUserService.updateUser(user);
        return Result.success();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userId}")
    public Result<Void> deleteUser(@PathVariable("userId") Long userId) {
        systemUserService.deleteUser(userId);
        return Result.success();
    }

    /**
     * 重置密码
     */
    @PutMapping("/{userId}/reset-password")
    public Result<Void> resetPassword(@PathVariable("userId") Long userId) {
        systemUserService.resetPassword(userId);
        return Result.success();
    }

    /**
     * 修改状态
     */
    @PutMapping("/{userId}/status")
    public Result<Void> updateStatus(
            @PathVariable("userId") Long userId,
            @RequestParam("status") Integer status) {
        systemUserService.updateStatus(userId, status);
        return Result.success();
    }

    /**
     * 分配角色
     */
    @PutMapping("/{userId}/roles")
    public Result<Void> assignRoles(
            @PathVariable("userId") Long userId,
            @RequestBody List<Long> roleIds) {
        systemUserService.assignRoles(userId, roleIds);
        return Result.success();
    }

    /**
     * 获取用户角色ID列表
     */
    @GetMapping("/{userId}/role-ids")
    public Result<List<Long>> getUserRoleIds(@PathVariable("userId") Long userId) {
        return Result.success(systemUserService.getUserRoleIds(userId));
    }

    /**
     * DTO转换为实体
     */
    private SystemUser convertToEntity(UserDTO dto) {
        SystemUser user = new SystemUser();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAvatar(dto.getAvatar());
        user.setSex(dto.getSex());
        user.setDeptId(dto.getDeptId());
        user.setStatus(dto.getStatus());
        user.setRemark(dto.getRemark());
        return user;
    }
}