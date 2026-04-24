package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.common.core.utils.XiaoYuThrowUtil;
import com.xiaoyu.module.system.entity.SystemDept;
import com.xiaoyu.module.system.entity.SystemUser;
import com.xiaoyu.module.system.mapper.SystemDeptMapper;
import com.xiaoyu.module.system.mapper.SystemUserMapper;
import com.xiaoyu.module.system.service.SystemDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemDeptServiceImpl extends ServiceImpl<SystemDeptMapper, SystemDept> implements SystemDeptService {

    private final SystemDeptMapper systemDeptMapper;
    private final SystemUserMapper systemUserMapper;

    @Override
    public List<SystemDept> getDeptTree() {
        // 获取所有部门
        QueryWrapper q = QueryWrapper.create().orderBy("sort", true);
        List<SystemDept> allDepts = systemDeptMapper.selectListByQuery(q);
        
        // 构建树形结构
        return buildDeptTree(0L, allDepts);
    }

    @Override
    public List<SystemDept> getDeptList() {
        QueryWrapper q = QueryWrapper.create().orderBy("sort", true);
        return systemDeptMapper.selectListByQuery(q);
    }

    @Override
    public SystemDept getDeptById(Long deptId) {
        SystemDept dept = systemDeptMapper.selectOneById(deptId);
        XiaoYuThrowUtil.throwIfNull(dept, "部门不存在");
        return dept;
    }

    @Override
    public List<SystemDept> getDeptChildren(Long deptId) {
        QueryWrapper q = QueryWrapper.create()
                .where("parent_id", deptId)
                .orderBy("sort", true);
        return systemDeptMapper.selectListByQuery(q);
    }

    @Override
    @Transactional
    public Long createDept(SystemDept dept) {
        // 检查编码唯一性
        XiaoYuThrowUtil.throwIfFalse(checkDeptCodeUnique(dept.getCode(), null), 
                "部门编码已存在");
        // 检查名称唯一性
        XiaoYuThrowUtil.throwIfFalse(checkDeptNameUnique(dept.getName(), 
                dept.getParentId(), null), "部门名称已存在");
        
        systemDeptMapper.insert(dept);
        log.info("新增部门: {}，ID: {}", dept.getName(), dept.getId());
        return dept.getId();
    }

    @Override
    @Transactional
    public void updateDept(SystemDept dept) {
        SystemDept existing = systemDeptMapper.selectOneById(dept.getId());
        XiaoYuThrowUtil.throwIfNull(existing, "部门不存在");
        
        // 检查编码唯一性
        XiaoYuThrowUtil.throwIfFalse(checkDeptCodeUnique(dept.getCode(), dept.getId()), 
                "部门编码已存在");
        
        XiaoYuThrowUtil.throwIfFalse(checkDeptNameUnique(dept.getName(), 
                dept.getParentId(), dept.getId()), "部门名称已存在");
        
        systemDeptMapper.update(dept);
        log.info("修改部门: {}，ID: {}", dept.getName(), dept.getId());
    }

    @Override
    @Transactional
    public void deleteDept(Long deptId) {
        SystemDept dept = systemDeptMapper.selectOneById(deptId);
        XiaoYuThrowUtil.throwIfNull(dept, "部门不存在");
        
        // 检查是否有子部门
        QueryWrapper q = QueryWrapper.create().where("parent_id", deptId);
        List<SystemDept> children = systemDeptMapper.selectListByQuery(q);
        XiaoYuThrowUtil.throwIf(!children.isEmpty(), "存在下级部门，不可删除");
        
        // 检查是否有用户关联
        QueryWrapper userQ = QueryWrapper.create().where("dept_id", deptId);
        List<SystemUser> users = systemUserMapper.selectListByQuery(userQ);
        XiaoYuThrowUtil.throwIf(!users.isEmpty(), "存在关联用户，不可删除");
        
        // 删除部门
        systemDeptMapper.deleteById(deptId);
        log.info("删除部门: {}，ID: {}", dept.getName(), deptId);
    }

    @Override
    public boolean checkDeptCodeUnique(String deptCode, Long deptId) {
        if (deptCode == null || deptCode.isEmpty()) {
            return true;
        }
        QueryWrapper q = QueryWrapper.create().where("dept_code", deptCode);
        List<SystemDept> list = systemDeptMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (deptId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(deptId);
    }

    @Override
    public boolean checkDeptNameUnique(String deptName, Long parentId, Long deptId) {
        if (deptName == null || deptName.isEmpty()) {
            return true;
        }
        QueryWrapper q = QueryWrapper.create()
                .where("name", deptName)
                .and("parent_id", parentId);
        List<SystemDept> list = systemDeptMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (deptId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(deptId);
    }

    /**
     * 构建部门树
     */
    private List<SystemDept> buildDeptTree(Long parentId, List<SystemDept> allDepts) {
        List<SystemDept> tree = new ArrayList<>();
        for (SystemDept dept : allDepts) {
            if (dept.getParentId().equals(parentId)) {
                tree.add(dept);
            }
        }
        return tree;
    }
}