package com.xiaoyu.module.system.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.common.core.utils.XiaoYuBeanUtil;
import com.xiaoyu.common.core.utils.XiaoYuThrowUtil;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    private final SystemUserMapper systemUserMapper;

    @Override
    public List<UserVO> getUserPage(Integer pageNum, Integer pageSize, String username, String nickname, Integer status, Long deptId) {
        return Collections.emptyList();
    }

    @Override
    public UserVO getUserById(Long userId) {
        SystemUser user = systemUserMapper.selectOneById(userId);
        XiaoYuThrowUtil.throwIfNull(user, "用户不存在");
        return convertToVO(user);
    }

    @Override
    public UserVO getUserByUsername(String username) {
        QueryWrapper q = QueryWrapper.create().where("username", username);
        SystemUser user = systemUserMapper.selectOneByQuery(q);
        XiaoYuThrowUtil.throwIfNull(user, "用户不存在");
        return convertToVO(user);
    }

    @Override
    @Transactional
    public Long createUser(SystemUser user) {
        QueryWrapper q = QueryWrapper.create().where("username", user.getUsername());
        XiaoYuThrowUtil.throwIfFalse(systemUserMapper.selectCountByQuery(q) == 0, "用户已存在");
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        user.setStatus(0);
        systemUserMapper.insert(user);
        return user.getId();
    }

    @Override
    @Transactional
    public void updateUser(SystemUser user) {
        XiaoYuThrowUtil.throwIfNull(systemUserMapper.selectOneById(user.getId()), "用户不存在");
        user.setPassword(null);
        systemUserMapper.update(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        XiaoYuThrowUtil.throwIfNull(systemUserMapper.selectOneById(userId), "用户不存在");
        systemUserMapper.deleteById(userId);
    }

    @Override
    @Transactional
    public void resetPassword(Long userId) {
        SystemUser user = systemUserMapper.selectOneById(userId);
        XiaoYuThrowUtil.throwIfNull(user, "用户不存在");
        user.setPassword(BCrypt.hashpw("123456"));
        systemUserMapper.update(user);
    }

    @Override
    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        SystemUser user = systemUserMapper.selectOneById(userId);
        XiaoYuThrowUtil.throwIfNull(user, "用户不存在");
        XiaoYuThrowUtil.throwIfFalse(BCrypt.checkpw(oldPassword, user.getPassword()), "原密码错误");
        user.setPassword(BCrypt.hashpw(newPassword));
        systemUserMapper.update(user);
    }

    @Override
    @Transactional
    public void updateStatus(Long userId, Integer status) {
        SystemUser user = systemUserMapper.selectOneById(userId);
        XiaoYuThrowUtil.throwIfNull(user, "用户不存在");
        user.setStatus(status);
        systemUserMapper.update(user);
    }

    @Override
    @Transactional
    public void assignRoles(Long userId, List<Long> roleIds) {}

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        return Collections.emptyList();
    }

    private UserVO convertToVO(SystemUser user) {
        return XiaoYuBeanUtil.copy(user, UserVO.class);
    }
}