package com.xiaoyu.module.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.common.core.utils.XiaoYuThrowUtil;
import com.xiaoyu.module.system.entity.SystemConfig;
import com.xiaoyu.module.system.mapper.SystemConfigMapper;
import com.xiaoyu.module.system.service.SystemConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 系统配置 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {

    private final SystemConfigMapper systemConfigMapper;
    
    // 配置缓存
    private static final Map<String, String> CONFIG_CACHE = new ConcurrentHashMap<>();

    @Override
    public List<SystemConfig> getConfigList() {
        QueryWrapper q = QueryWrapper.create().orderBy("sort", true);
        return systemConfigMapper.selectListByQuery(q);
    }

    @Override
    public SystemConfig getConfigById(Long configId) {
        SystemConfig config = systemConfigMapper.selectOneById(configId);
        XiaoYuThrowUtil.throwIfNull(config, "配置不存在");
        return config;
    }

    @Override
    public String getConfigValue(String configKey) {
        // 先从缓存获取
        String cachedValue = CONFIG_CACHE.get(configKey);
        if (cachedValue != null) {
            return cachedValue;
        }
        
        // 从数据库获取
        QueryWrapper q = QueryWrapper.create().where("config_key", configKey);
        SystemConfig config = systemConfigMapper.selectOneByQuery(q);
        
        if (config != null) {
            String value = config.getConfigValue();
            CONFIG_CACHE.put(configKey, value);
            return value;
        }
        return null;
    }

    @Override
    @Transactional
    public Long createConfig(SystemConfig config) {
        // 检查键名唯一性
        QueryWrapper q = QueryWrapper.create().where("config_key", config.getConfigKey());
        List<SystemConfig> existing = systemConfigMapper.selectListByQuery(q);
        XiaoYuThrowUtil.throwIf(!existing.isEmpty(), "配置键名已存在");
        
        systemConfigMapper.insert(config);
        log.info("新增系统配置: {}，键名: {}", config.getName(), config.getConfigKey());
        return config.getId();
    }

    @Override
    @Transactional
    public void updateConfig(SystemConfig config) {
        SystemConfig existing = systemConfigMapper.selectOneById(config.getId());
        XiaoYuThrowUtil.throwIfNull(existing, "配置不存在");
        
        // 内置配置不允许修改
        XiaoYuThrowUtil.throwIf(existing.getIsBuiltin() == 1, "内置配置不允许修改");
        
        systemConfigMapper.update(config);
        
        // 更新缓存
        CONFIG_CACHE.put(config.getConfigKey(), config.getConfigValue());
        
        log.info("修改系统配置: {}，键名: {}", config.getName(), config.getConfigKey());
    }

    @Override
    @Transactional
    public void deleteConfig(Long configId) {
        SystemConfig config = systemConfigMapper.selectOneById(configId);
        XiaoYuThrowUtil.throwIfNull(config, "配置不存在");
        
        // 内置配置不允许删除
        XiaoYuThrowUtil.throwIf(config.getIsBuiltin() == 1, "内置配置不允许删除");
        
        systemConfigMapper.deleteById(configId);
        
        // 清除缓存
        CONFIG_CACHE.remove(config.getConfigKey());
        
        log.info("删除系统配置: {}，键名: {}", config.getName(), config.getConfigKey());
    }

    @Override
    public void refreshCache() {
        CONFIG_CACHE.clear();
        
        // 重新加载所有配置到缓存
        List<SystemConfig> configs = getConfigList();
        for (SystemConfig config : configs) {
            if (StrUtil.isNotBlank(config.getConfigKey())) {
                CONFIG_CACHE.put(config.getConfigKey(), config.getConfigValue());
            }
        }
        
        log.info("系统配置缓存已刷新，共 {} 条配置", configs.size());
    }

    @Override
    public Map<String, String> getConfigs(List<String> keys) {
        Map<String, String> result = new HashMap<>();
        for (String key : keys) {
            String value = getConfigValue(key);
            if (value != null) {
                result.put(key, value);
            }
        }
        return result;
    }
}