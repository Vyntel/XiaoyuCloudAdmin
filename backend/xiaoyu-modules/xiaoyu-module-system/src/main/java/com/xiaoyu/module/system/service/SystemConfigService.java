package com.xiaoyu.module.system.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.module.system.entity.SystemConfig;

import java.util.List;
import java.util.Map;

/**
 * 系统配置Service接口
 */
public interface SystemConfigService extends IService<SystemConfig> {

    /**
     * 获取配置列表
     */
    List<SystemConfig> getConfigList();

    /**
     * 根据ID获取配置
     */
    SystemConfig getConfigById(Long configId);

    /**
     * 根据键名获取配置值
     */
    String getConfigValue(String configKey);

    /**
     * 新增配置
     */
    Long createConfig(SystemConfig config);

    /**
     * 修改配置
     */
    void updateConfig(SystemConfig config);

    /**
     * 删除配置
     */
    void deleteConfig(Long configId);

    /**
     * 刷新配置缓存
     */
    void refreshCache();

    /**
     * 批量获取配置
     */
    Map<String, String> getConfigs(List<String> keys);
}