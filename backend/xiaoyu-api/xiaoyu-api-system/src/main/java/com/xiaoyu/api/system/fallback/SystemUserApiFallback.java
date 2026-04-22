package com.xiaoyu.api.system.fallback;

import com.xiaoyu.api.system.api.SystemUserApi;
import com.xiaoyu.api.system.dto.UserDTO;
import com.xiaoyu.api.system.vo.UserVO;
import com.xiaoyu.common.core.result.PageResult;
import com.xiaoyu.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 系统用户 API 降级实现
 */
@Slf4j
@Component
public class SystemUserApiFallback implements SystemUserApi {

    @Override
    public Result<PageResult<UserVO>> getUserPage(Integer pageNum, Integer pageSize, String username, String nickname, Integer status, Long deptId) {
        log.warn("SystemUserApi.getUserPage 调用降级");
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<UserVO> getUserById(Long userId) {
        log.warn("SystemUserApi.getUserById 调用降级, userId: {}", userId);
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<UserVO> getUserByUsername(String username) {
        log.warn("SystemUserApi.getUserByUsername 调用降级, username: {}", username);
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<Long> createUser(UserDTO userDTO) {
        log.warn("SystemUserApi.createUser 调用降级");
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<Void> updateUser(UserDTO userDTO) {
        log.warn("SystemUserApi.updateUser 调用降级");
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<Void> deleteUser(Long userId) {
        log.warn("SystemUserApi.deleteUser 调用降级, userId: {}", userId);
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<Void> resetPassword(Long userId) {
        log.warn("SystemUserApi.resetPassword 调用降级, userId: {}", userId);
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<Void> changePassword(Long userId, String oldPassword, String newPassword) {
        log.warn("SystemUserApi.changePassword 调用降级, userId: {}", userId);
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<Void> updateStatus(Long userId, Integer status) {
        log.warn("SystemUserApi.updateStatus 调用降级, userId: {}, status: {}", userId, status);
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<Void> assignRoles(Long userId, List<Long> roleIds) {
        log.warn("SystemUserApi.assignRoles 调用降级, userId: {}", userId);
        return Result.fail("服务降级：系统用户服务不可用");
    }

    @Override
    public Result<List<Long>> getUserRoleIds(Long userId) {
        log.warn("SystemUserApi.getUserRoleIds 调用降级, userId: {}", userId);
        return Result.success(Collections.emptyList());
    }
}
