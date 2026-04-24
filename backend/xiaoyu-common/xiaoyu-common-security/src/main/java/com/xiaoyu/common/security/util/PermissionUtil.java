package com.xiaoyu.common.security.util;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaoyu.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * 权限校验工具类
 */
@Slf4j
public class PermissionUtil {

    /**
     * 检查是否有指定权限
     *
     * @param permission 权限标识
     * @throws ServiceException 如果没有权限
     */
    public static void checkPermission(String permission) {
        if (permission == null || permission.isEmpty()) {
            return;
        }
        if (!StpUtil.hasPermission(permission)) {
            log.warn("权限校验失败: userId={}, permission={}", StpUtil.getLoginIdAsLong(), permission);
            throw new ServiceException("无权限访问: " + permission);
        }
    }

    /**
     * 检查是否有任一权限
     *
     * @param permissions 权限标识数组
     * @throws ServiceException 如果没有任一权限
     */
    public static void checkAnyPermission(String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        for (String permission : permissions) {
            if (StpUtil.hasPermission(permission)) {
                return;
            }
        }
        log.warn("权限校验失败: userId={}, permissions={}", StpUtil.getLoginIdAsLong(), permissions);
        throw new ServiceException("无权限访问");
    }

    /**
     * 检查是否有所有权限
     *
     * @param permissions 权限标识数组
     * @throws ServiceException 如果没有所有权限
     */
    public static void checkAllPermission(String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        for (String permission : permissions) {
            if (!StpUtil.hasPermission(permission)) {
                log.warn("权限校验失败: userId={}, permission={}", StpUtil.getLoginIdAsLong(), permission);
                throw new ServiceException("无权限访问: " + permission);
            }
        }
    }

    /**
     * 检查是否已登录
     *
     * @throws ServiceException 如果未登录
     */
    public static void checkLogin() {
        if (!StpUtil.isLogin()) {
            throw new ServiceException("请先登录");
        }
    }
}