package com.xiaoyu.api.system.api;

import com.xiaoyu.api.system.dto.UserDTO;
import com.xiaoyu.api.system.vo.UserVO;
import com.xiaoyu.api.system.fallback.SystemUserApiFallback;
import com.xiaoyu.common.core.result.PageResult;
import com.xiaoyu.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户 Feign API
 */
@FeignClient(
        name = "xiaoyu-system",
        path = "/system/user",
        fallback = SystemUserApiFallback.class
)
public interface SystemUserApi {

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    Result<PageResult<UserVO>> getUserPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "deptId", required = false) Long deptId
    );

    /**
     * 获取用户详情
     */
    @GetMapping("/{userId}")
    Result<UserVO> getUserById(@PathVariable("userId") Long userId);

    /**
     * 根据用户名获取用户信息
     */
    @GetMapping("/username/{username}")
    Result<UserVO> getUserByUsername(@PathVariable("username") String username);

    /**
     * 新增用户
     */
    @PostMapping
    Result<Long> createUser(@RequestBody UserDTO userDTO);

    /**
     * 修改用户
     */
    @PutMapping
    Result<Void> updateUser(@RequestBody UserDTO userDTO);

    /**
     * 删除用户
     */
    @DeleteMapping("/{userId}")
    Result<Void> deleteUser(@PathVariable("userId") Long userId);

    /**
     * 重置密码
     */
    @PutMapping("/{userId}/reset-password")
    Result<Void> resetPassword(@PathVariable("userId") Long userId);

    /**
     * 修改密码
     */
    @PutMapping("/{userId}/change-password")
    Result<Void> changePassword(
            @PathVariable("userId") Long userId,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword
    );

    /**
     * 修改状态
     */
    @PutMapping("/{userId}/status")
    Result<Void> updateStatus(
            @PathVariable("userId") Long userId,
            @RequestParam("status") Integer status
    );

    /**
     * 分配角色
     */
    @PutMapping("/{userId}/roles")
    Result<Void> assignRoles(
            @PathVariable("userId") Long userId,
            @RequestBody List<Long> roleIds
    );

    /**
     * 获取用户角色ID列表
     */
    @GetMapping("/{userId}/role-ids")
    Result<List<Long>> getUserRoleIds(@PathVariable("userId") Long userId);
}
