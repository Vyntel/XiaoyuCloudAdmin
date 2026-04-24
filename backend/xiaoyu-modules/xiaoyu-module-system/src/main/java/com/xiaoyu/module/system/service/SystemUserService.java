package com.xiaoyu.module.system.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.api.system.vo.UserVO;
import com.xiaoyu.module.system.entity.SystemUser;

import java.util.List;

/**
 * 系统用户 Service
 */
public interface SystemUserService extends IService<SystemUser> {

    /**
     * 分页查询用户列表
     */
    List<UserVO> getUserPage(Integer pageNum, Integer pageSize, String username, String nickname, Integer status, Long deptId);

    /**
     * 根据用户ID获取用户信息
     */
    UserVO getUserById(Long userId);

    /**
     * 根据用户名获取用户信息
     */
    UserVO getUserByUsername(String username);

    /**
     * 新增用户
     */
    Long createUser(SystemUser user);

    /**
     * 修改用户
     */
    void updateUser(SystemUser user);

    /**
     * 删除用户
     */
    void deleteUser(Long userId);

    /**
     * 重置密码
     */
    void resetPassword(Long userId);

    /**
     * 修改密码
     */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 修改状态
     */
    void updateStatus(Long userId, Integer status);

    /**
     * 分配角色
     */
    void assignRoles(Long userId, List<Long> roleIds);

    /**
     * 获取用户角色ID列表
     */
    List<Long> getUserRoleIds(Long userId);
}