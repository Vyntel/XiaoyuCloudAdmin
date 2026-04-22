package com.xiaoyu.module.system.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoyu.common.core.exception.ServiceException;
import com.xiaoyu.module.system.entity.SystemUser;
import com.xiaoyu.module.system.mapper.SystemUserMapper;
import com.xiaoyu.module.system.service.SystemUserService;
import com.xiaoyu.api.system.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 系统用户 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserMapper systemUserMapper;

    @Override
    public List<UserVO> getUserPage(Integer pageNum, Integer pageSize, String username, String nickname, Integer status, Long deptId) {
        // TODO: 实现分页查询
        return Collections.emptyList();
    }

    @Override
    public UserVO getUserById(Long userId) {
        SystemUser user = systemUserMapper.selectById(userId);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        return convertToVO(user);
    }

    @Override
    public UserVO getUserByUsername(String username) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getUsername, username);
        SystemUser user = systemUserMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        return convertToVO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(SystemUser user) {
        // 检查用户名是否存在
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getUsername, user.getUsername());
        Long count = systemUserMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new ServiceException("用户已存在");
        }

        // 加密密码
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        user.setStatus(0);
        systemUserMapper.insert(user);
        return user.getUserId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SystemUser user) {
        SystemUser existUser = systemUserMapper.selectById(user.getUserId());
        if (existUser == null) {
            throw new ServiceException("用户不存在");
        }

        // 不更新密码
        user.setPassword(null);
        systemUserMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        SystemUser user = systemUserMapper.selectById(userId);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        systemUserMapper.deleteById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId) {
        SystemUser user = systemUserMapper.selectById(userId);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        // 默认密码：123456
        user.setPassword(BCrypt.hashpw("123456"));
        systemUserMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        SystemUser user = systemUserMapper.selectById(userId);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }

        // 验证旧密码
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new ServiceException("原密码错误");
        }

        // 设置新密码
        user.setPassword(BCrypt.hashpw(newPassword));
        systemUserMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, Integer status) {
        SystemUser user = systemUserMapper.selectById(userId);
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        user.setStatus(status);
        systemUserMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(Long userId, List<Long> roleIds) {
        // TODO: 实现角色分配
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        // TODO: 实现获取用户角色ID列表
        return Collections.emptyList();
    }

    /**
     * 转换为VO
     */
    private UserVO convertToVO(SystemUser user) {
        UserVO vo = new UserVO();
        vo.setUserId(user.getUserId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setAvatar(user.getAvatar());
        vo.setSex(user.getSex());
        vo.setDeptId(user.getDeptId());
        vo.setStatus(user.getStatus());
        vo.setCreateTime(user.getCreateTime());
        vo.setUpdateTime(user.getUpdateTime());
        vo.setRemark(user.getRemark());
        return vo;
    }
}