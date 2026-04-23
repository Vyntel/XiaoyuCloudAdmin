package com.xiaoyu.module.system.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.module.system.entity.SystemDept;

import java.util.List;

/**
 * 部门Service接口
 */
public interface SystemDeptService extends IService<SystemDept> {

    /**
     * 获取部门树
     */
    List<SystemDept> getDeptTree();

    /**
     * 获取所有部门列表
     */
    List<SystemDept> getDeptList();

    /**
     * 根据ID获取部门
     */
    SystemDept getDeptById(Long deptId);

    /**
     * 获取子部门列表
     */
    List<SystemDept> getDeptChildren(Long deptId);

    /**
     * 新增部门
     */
    Long createDept(SystemDept dept);

    /**
     * 修改部门
     */
    void updateDept(SystemDept dept);

    /**
     * 删除部门
     */
    void deleteDept(Long deptId);

    /**
     * 检查部门编码是否唯一
     */
    boolean checkDeptCodeUnique(String deptCode, Long deptId);

    /**
     * 检查部门名称是否唯一
     */
    boolean checkDeptNameUnique(String deptName, Long parentId, Long deptId);
}