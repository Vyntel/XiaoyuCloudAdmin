package com.xiaoyu.module.system.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.common.core.utils.XiaoYuBeanUtil;
import com.xiaoyu.common.core.utils.XiaoYuThrowUtil;
import com.xiaoyu.module.system.entity.SystemUser;
import com.xiaoyu.module.system.entity.SystemUserRole;
import com.xiaoyu.module.system.mapper.SystemUserMapper;
import com.xiaoyu.module.system.mapper.SystemUserRoleMapper;
import com.xiaoyu.module.system.service.SystemUserService;
import com.xiaoyu.api.system.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    private final SystemUserMapper systemUserMapper;
    private final SystemUserRoleMapper systemUserRoleMapper;

    @Override
    public List<UserVO> getUserPage(Integer pageNum, Integer pageSize, String username, String nickname, Integer status, Long deptId) {
        QueryWrapper q = QueryWrapper.create()
                .like("username", username)
                .like("nickname", nickname)
                .eq("status", status)
                .eq("dept_id", deptId)
                .orderBy("create_time", false);
        
        Page<SystemUser> page = systemUserMapper.paginate(pageNum, pageSize, q);
        return page.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
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
        SystemUser user = systemUserMapper.selectOneById(userId);
        XiaoYuThrowUtil.throwIfNull(user, "用户不存在");
        // 删除用户角色关联
        QueryWrapper q = QueryWrapper.create().where("user_id", userId);
        systemUserRoleMapper.deleteByQuery(q);
        // 逻辑删除用户
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
    public void assignRoles(Long userId, List<Long> roleIds) {
        SystemUser user = systemUserMapper.selectOneById(userId);
        XiaoYuThrowUtil.throwIfNull(user, "用户不存在");
        // 删除旧关联
        QueryWrapper q = QueryWrapper.create().where("user_id", userId);
        systemUserRoleMapper.deleteByQuery(q);
        // 插入新关联
        for (Long roleId : roleIds) {
            SystemUserRole ur = new SystemUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            systemUserRoleMapper.insert(ur);
        }
        log.info("用户ID: {} 角色分配完成，共 {} 个角色", userId, roleIds.size());
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        QueryWrapper q = QueryWrapper.create().where("user_id", userId);
        List<SystemUserRole> list = systemUserRoleMapper.selectListByQuery(q);
        return list.stream().map(SystemUserRole::getRoleId).collect(Collectors.toList());
    }

    private UserVO convertToVO(SystemUser user) {
        return XiaoYuBeanUtil.copy(user, UserVO.class);
    }
}