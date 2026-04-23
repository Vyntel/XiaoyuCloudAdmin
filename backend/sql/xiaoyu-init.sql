-- 小羽快速开发框架 - 数据库初始化脚本

CREATE DATABASE IF NOT EXISTS xiaoyu_cloud DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE xiaoyu_cloud;

-- =====================================================
-- 系统管理相关表
-- =====================================================

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT NOT NULL COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
    sex TINYINT DEFAULT 2 COMMENT '性别（0-男，1-女，2-未知）',
    dept_id BIGINT DEFAULT NULL COMMENT '部门ID',
    status TINYINT DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除（0-未删除，1-已删除）',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username, tenant_id) COMMENT '用户名唯一',
    KEY idx_dept_id (dept_id) COMMENT '部门ID索引',
    KEY idx_tenant_id (tenant_id) COMMENT '租户ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    name VARCHAR(50) NOT NULL COMMENT '角色名称',
    code VARCHAR(50) NOT NULL COMMENT '角色编码',
    description VARCHAR(500) DEFAULT NULL COMMENT '角色描述',
    level INT DEFAULT 0 COMMENT '角色级别',
    data_scope INT DEFAULT 1 COMMENT '数据范围(1-全部,2-自定义,3-本部门,4-本部门及以下,5-仅本人)',
    data_scope_dept_ids VARCHAR(2000) DEFAULT NULL COMMENT '自定义数据权限部门ID列表',
    menu_ids VARCHAR(2000) DEFAULT NULL COMMENT '菜单权限ID列表',
    status INT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code, tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    parent_id_path VARCHAR(500) DEFAULT '0' COMMENT '父级ID路径',
    name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    path VARCHAR(200) DEFAULT NULL COMMENT '路由地址',
    component VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    icon VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    menu_type CHAR(1) DEFAULT NULL COMMENT '菜单类型(M-目录,C-菜单,F-按钮)',
    is_show INT DEFAULT 0 COMMENT '是否显示(0-显示,1-隐藏)',
    is_keep_alive INT DEFAULT 0 COMMENT '是否缓存(0-缓存,1-禁用)',
    is_frame INT DEFAULT 0 COMMENT '是否外链(0-否,1-是)',
    permission VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    status INT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';

-- 用户和角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';

-- 角色和菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和菜单关联表';

-- 部门表
CREATE TABLE IF NOT EXISTS sys_dept (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    parent_id_path VARCHAR(500) DEFAULT '0' COMMENT '父级ID路径',
    level INT DEFAULT 1 COMMENT '部门层级',
    name VARCHAR(50) NOT NULL COMMENT '部门名称',
    code VARCHAR(50) DEFAULT NULL COMMENT '部门编码',
    leader_user_id BIGINT DEFAULT NULL COMMENT '负责人ID',
    leader_user_name VARCHAR(50) DEFAULT NULL COMMENT '负责人用户名',
    phone VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- 岗位表
CREATE TABLE IF NOT EXISTS sys_post (
    id BIGINT NOT NULL COMMENT '岗位ID',
    post_code VARCHAR(50) NOT NULL COMMENT '岗位编码',
    post_name VARCHAR(50) NOT NULL COMMENT '岗位名称',
    post_sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除（0-未删除，1-已删除）',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_post_code (post_code, tenant_id) COMMENT '岗位编码唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统岗位表';

-- 用户和岗位关联表
CREATE TABLE IF NOT EXISTS sys_user_post (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    post_id BIGINT NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (user_id, post_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和岗位关联表';

-- 字典类型表
CREATE TABLE IF NOT EXISTS sys_dict_type (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典类型ID',
    name VARCHAR(100) NOT NULL COMMENT '字典名称',
    code VARCHAR(100) NOT NULL COMMENT '字典类型',
    description VARCHAR(500) DEFAULT NULL COMMENT '字典描述',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code, tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE IF NOT EXISTS sys_dict_data (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典数据ID',
    sort INT DEFAULT 0 COMMENT '字典排序',
    label VARCHAR(100) NOT NULL COMMENT '字典标签',
    value VARCHAR(100) NOT NULL COMMENT '字典值',
    dict_type_id BIGINT NOT NULL COMMENT '字典类型ID',
    dict_type_code VARCHAR(100) DEFAULT NULL COMMENT '字典类型代码',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认(0-否,1-是)',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    color_type VARCHAR(20) DEFAULT NULL COMMENT '颜色类型',
    css_class VARCHAR(100) DEFAULT NULL COMMENT 'CSS类名',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY idx_dict_type_id (dict_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- 系统配置表
CREATE TABLE IF NOT EXISTS sys_config (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    config_key VARCHAR(100) NOT NULL COMMENT '配置键名',
    config_value VARCHAR(1000) NOT NULL COMMENT '配置值',
    config_type CHAR(1) DEFAULT 'N' COMMENT '系统内置(Y-N/Y)',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开(0-否,1-是)',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_key (config_key, tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 租户表
CREATE TABLE IF NOT EXISTS sys_tenant (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '租户ID',
    name VARCHAR(100) NOT NULL COMMENT '租户名称',
    code VARCHAR(100) NOT NULL COMMENT '租户编码',
    domain VARCHAR(200) DEFAULT NULL COMMENT '租户域名',
    logo VARCHAR(255) DEFAULT NULL COMMENT '租户logo',
    description VARCHAR(500) DEFAULT NULL COMMENT '租户简介',
    package_id BIGINT DEFAULT NULL COMMENT '套餐ID',
    account_limit INT DEFAULT 0 COMMENT '账号数量限制',
    storage_limit BIGINT DEFAULT NULL COMMENT '空间存储限制(MB)',
    expire_time DATETIME DEFAULT NULL COMMENT '到期时间',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- 初始化默认租户
INSERT INTO sys_tenant (id, name, code, description, account_limit, status) VALUES
(1, '默认租户', 'default', '系统默认租户', 0, 0);

-- =====================================================
-- 初始化数据
-- =====================================================

-- 初始化超级管理员用户 (用户名: admin, 密码: 123456)
INSERT INTO sys_user (id, username, password, nickname, status, tenant_id) VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '超级管理员', 0, 1);

-- 初始化顶级部门
INSERT INTO sys_dept (id, parent_id, name, code, level, status, tenant_id) VALUES
(1, 0, '小羽科技', 'XIAOYU', 1, 0, 1);

-- 初始化菜单数据
-- 系统管理目录
INSERT INTO sys_menu (id, parent_id, parent_id_path, name, path, component, icon, sort, menu_type, is_show, is_keep_alive, is_frame, permission, status, tenant_id) VALUES
(1, 0, '0', '系统管理', '/system', NULL, 'ri-settings-3-line', 1, 'M', 0, 0, 0, 'system:manage', 0, 1);

-- 用户管理
INSERT INTO sys_menu (id, parent_id, parent_id_path, name, path, component, icon, sort, menu_type, is_show, is_keep_alive, is_frame, permission, status, tenant_id) VALUES
(2, 1, '0,1', '用户管理', 'user', 'system/user/index', 'ri-user-line', 1, 'C', 0, 0, 0, 'system:user:list', 0, 1),
(3, 2, '0,1,2', '用户查询', '', NULL, '#', 1, 'F', 0, 0, 0, 'system:user:query', 0, 1),
(4, 2, '0,1,2', '用户新增', '', NULL, '#', 2, 'F', 0, 0, 0, 'system:user:create', 0, 1),
(5, 2, '0,1,2', '用户编辑', '', NULL, '#', 3, 'F', 0, 0, 0, 'system:user:update', 0, 1),
(6, 2, '0,1,2', '用户删除', '', NULL, '#', 4, 'F', 0, 0, 0, 'system:user:delete', 0, 1),
(7, 2, '0,1,2', '重置密码', '', NULL, '#', 5, 'F', 0, 0, 0, 'system:user:reset-password', 0, 1);

-- 角色管理
INSERT INTO sys_menu (id, parent_id, parent_id_path, name, path, component, icon, sort, menu_type, is_show, is_keep_alive, is_frame, permission, status, tenant_id) VALUES
(10, 1, '0,1', '角色管理', 'role', 'system/role/index', 'ri-shield-line', 2, 'C', 0, 0, 0, 'system:role:list', 0, 1),
(11, 10, '0,1,10', '角色查询', '', NULL, '#', 1, 'F', 0, 0, 0, 'system:role:query', 0, 1),
(12, 10, '0,1,10', '角色新增', '', NULL, '#', 2, 'F', 0, 0, 0, 'system:role:create', 0, 1),
(13, 10, '0,1,10', '角色编辑', '', NULL, '#', 3, 'F', 0, 0, 0, 'system:role:update', 0, 1),
(14, 10, '0,1,10', '角色删除', '', NULL, '#', 4, 'F', 0, 0, 0, 'system:role:delete', 0, 1);

-- 菜单管理
INSERT INTO sys_menu (id, parent_id, parent_id_path, name, path, component, icon, sort, menu_type, is_show, is_keep_alive, is_frame, permission, status, tenant_id) VALUES
(20, 1, '0,1', '菜单管理', 'menu', 'system/menu/index', 'ri-menu-3-line', 3, 'C', 0, 0, 0, 'system:menu:list', 0, 1),
(21, 20, '0,1,20', '菜单查询', '', NULL, '#', 1, 'F', 0, 0, 0, 'system:menu:query', 0, 1),
(22, 20, '0,1,20', '菜单新增', '', NULL, '#', 2, 'F', 0, 0, 0, 'system:menu:create', 0, 1),
(23, 20, '0,1,20', '菜单编辑', '', NULL, '#', 3, 'F', 0, 0, 0, 'system:menu:update', 0, 1),
(24, 20, '0,1,20', '菜单删除', '', NULL, '#', 4, 'F', 0, 0, 0, 'system:menu:delete', 0, 1);

-- 部门管理
INSERT INTO sys_menu (id, parent_id, parent_id_path, name, path, component, icon, sort, menu_type, is_show, is_keep_alive, is_frame, permission, status, tenant_id) VALUES
(30, 1, '0,1', '部门管理', 'dept', 'system/dept/index', 'ri-group-line', 4, 'C', 0, 0, 0, 'system:dept:list', 0, 1),
(31, 30, '0,1,30', '部门查询', '', NULL, '#', 1, 'F', 0, 0, 0, 'system:dept:query', 0, 1),
(32, 30, '0,1,30', '部门新增', '', NULL, '#', 2, 'F', 0, 0, 0, 'system:dept:create', 0, 1),
(33, 30, '0,1,30', '部门编辑', '', NULL, '#', 3, 'F', 0, 0, 0, 'system:dept:update', 0, 1),
(34, 30, '0,1,30', '部门删除', '', NULL, '#', 4, 'F', 0, 0, 0, 'system:dept:delete', 0, 1);

-- 字典管理
INSERT INTO sys_menu (id, parent_id, parent_id_path, name, path, component, icon, sort, menu_type, is_show, is_keep_alive, is_frame, permission, status, tenant_id) VALUES
(40, 1, '0,1', '字典管理', 'dict', 'system/dict/index', 'ri-book-2-line', 5, 'C', 0, 0, 0, 'system:dict:list', 0, 1),
(41, 40, '0,1,40', '字典查询', '', NULL, '#', 1, 'F', 0, 0, 0, 'system:dict:query', 0, 1),
(42, 40, '0,1,40', '字典新增', '', NULL, '#', 2, 'F', 0, 0, 0, 'system:dict:create', 0, 1),
(43, 40, '0,1,40', '字典编辑', '', NULL, '#', 3, 'F', 0, 0, 0, 'system:dict:update', 0, 1),
(44, 40, '0,1,40', '字典删除', '', NULL, '#', 4, 'F', 0, 0, 0, 'system:dict:delete', 0, 1);

-- 系统配置
INSERT INTO sys_menu (id, parent_id, parent_id_path, name, path, component, icon, sort, menu_type, is_show, is_keep_alive, is_frame, permission, status, tenant_id) VALUES
(50, 1, '0,1', '系统配置', 'config', 'system/config/index', 'ri-settings-4-line', 6, 'C', 0, 0, 0, 'system:config:list', 0, 1),
(51, 50, '0,1,50', '配置查询', '', NULL, '#', 1, 'F', 0, 0, 0, 'system:config:query', 0, 1),
(52, 50, '0,1,50', '配置新增', '', NULL, '#', 2, 'F', 0, 0, 0, 'system:config:create', 0, 1),
(53, 50, '0,1,50', '配置编辑', '', NULL, '#', 3, 'F', 0, 0, 0, 'system:config:update', 0, 1),
(54, 50, '0,1,50', '配置删除', '', NULL, '#', 4, 'F', 0, 0, 0, 'system:config:delete', 0, 1);

-- 初始化超级管理员角色
INSERT INTO sys_role (id, name, code, description, level, data_scope, status, tenant_id) VALUES
(1, '超级管理员', 'super_admin', '超级管理员拥有所有权限', 0, 1, 0, 1);

-- 初始化角色和菜单关联数据
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7),
(1, 10), (1, 11), (1, 12), (1, 13), (1, 14),
(1, 20), (1, 21), (1, 22), (1, 23), (1, 24),
(1, 30), (1, 31), (1, 32), (1, 33), (1, 34),
(1, 40), (1, 41), (1, 42), (1, 43), (1, 44),
(1, 50), (1, 51), (1, 52), (1, 53), (1, 54);

-- 初始化用户和角色关联
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 初始化字典类型
INSERT INTO sys_dict_type (id, name, code, description, sort, status, tenant_id) VALUES
(1, '用户性别', 'user_sex', '用户性别列表', 1, 0, 1),
(2, '菜单状态', 'menu_status', '菜单状态列表', 2, 0, 1),
(3, '用户状态', 'user_status', '用户状态列表', 3, 0, 1),
(4, '通用状态', 'common_status', '通用状态列表', 4, 0, 1);

-- 初始化字典数据
INSERT INTO sys_dict_data (sort, label, value, dict_type_id, dict_type_code, is_default, status, color_type, tenant_id) VALUES
(1, '男', '0', 1, 'user_sex', 1, 0, 'primary', 1),
(2, '女', '1', 1, 'user_sex', 0, 0, 'success', 1),
(3, '未知', '2', 1, 'user_sex', 0, 0, 'info', 1),
(4, '正常', '0', 2, 'menu_status', 1, 0, 'primary', 1),
(5, '禁用', '1', 2, 'menu_status', 0, 0, 'danger', 1),
(6, '正常', '0', 3, 'user_status', 1, 0, 'primary', 1),
(7, '禁用', '1', 3, 'user_status', 0, 0, 'danger', 1),
(8, '正常', '0', 4, 'common_status', 1, 0, 'primary', 1),
(9, '禁用', '1', 4, 'common_status', 0, 0, 'danger', 1);

-- 初始化系统配置
INSERT INTO sys_config (config_key, config_value, config_type, is_public, status, tenant_id) VALUES
('sys.index.skinName', 'skin-purple-light', 'N', 1, 0, 1),
('sys.index.sidebarStatus', '0', 'N', 1, 0, 1),
('sys.index.topbarStatus', '0', 'N', 1, 0, 1),
('sys.account.captchaStatus', '0', 'N', 1, 0, 1),
('sys.account.registerStatus', '0', 'N', 1, 0, 1);

-- =====================================================
-- AI模块相关表
-- =====================================================

-- AI模型表
CREATE TABLE IF NOT EXISTS ai_model (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '模型ID',
    name VARCHAR(100) NOT NULL COMMENT '模型名称',
    code VARCHAR(100) NOT NULL COMMENT '模型编码',
    provider VARCHAR(50) NOT NULL COMMENT '供应商(openai/anthropic/google/azure等)',
    model_id VARCHAR(200) DEFAULT NULL COMMENT '供应商模型ID',
    api_key VARCHAR(500) DEFAULT NULL COMMENT 'API Key',
    api_base VARCHAR(200) DEFAULT NULL COMMENT 'API地址',
    api_version VARCHAR(50) DEFAULT NULL COMMENT 'API版本',
    icon VARCHAR(255) DEFAULT NULL COMMENT '模型图标',
    description VARCHAR(500) DEFAULT NULL COMMENT '模型描述',
    max_tokens INT DEFAULT 4096 COMMENT '最大Token',
    temperature DECIMAL(3,2) DEFAULT 0.7 COMMENT '默认温度',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code, tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI模型表';

-- AI会话表
CREATE TABLE IF NOT EXISTS ai_conversation (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '会话ID',
    title VARCHAR(200) NOT NULL COMMENT '会话标题',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    model_code VARCHAR(100) DEFAULT NULL COMMENT 'AI模型编码',
    knowledge_id BIGINT DEFAULT NULL COMMENT '知识库ID',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-已归档)',
    message_count INT DEFAULT 0 COMMENT '消息数量',
    last_message_time DATETIME DEFAULT NULL COMMENT '最后消息时间',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI会话表';

-- AI消息表
CREATE TABLE IF NOT EXISTS ai_message (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    conversation_id BIGINT NOT NULL COMMENT '会话ID',
    role VARCHAR(20) NOT NULL COMMENT '角色(user/assistant/system)',
    content LONGTEXT NOT NULL COMMENT '消息内容',
    model_code VARCHAR(100) DEFAULT NULL COMMENT '模型编码',
    model_response TEXT DEFAULT NULL COMMENT '模型响应',
    tokens INT DEFAULT NULL COMMENT '消耗Token',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-失败)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_conversation_id (conversation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI消息表';

-- AI知识库表
CREATE TABLE IF NOT EXISTS ai_knowledge (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
    name VARCHAR(100) NOT NULL COMMENT '知识库名称',
    description VARCHAR(500) DEFAULT NULL COMMENT '知识库描述',
    file_url VARCHAR(500) DEFAULT NULL COMMENT '文件地址',
    file_count INT DEFAULT 0 COMMENT '文件数量',
    word_count INT DEFAULT 0 COMMENT '词数',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI知识库表';

-- 初始化默认AI模型
INSERT INTO ai_model (id, name, code, provider, description, max_tokens, temperature, sort, status, tenant_id) VALUES
(1, 'GPT-4', 'gpt-4', 'openai', 'OpenAI GPT-4模型', 8192, 0.7, 1, 0, 1),
(2, 'GPT-3.5 Turbo', 'gpt-3.5-turbo', 'openai', 'OpenAI GPT-3.5 Turbo模型', 4096, 0.7, 2, 0, 1);

-- =====================================================
-- 工作流相关表
-- =====================================================

-- 流程定义表
CREATE TABLE IF NOT EXISTS wf_definition (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '定义ID',
    process_key VARCHAR(100) NOT NULL COMMENT '流程Key',
    name VARCHAR(100) NOT NULL COMMENT '流程名称',
    description VARCHAR(500) DEFAULT NULL COMMENT '流程描述',
    category VARCHAR(50) DEFAULT NULL COMMENT '流程分类',
    bpmn_xml LONGTEXT DEFAULT NULL COMMENT 'BPMN XML内容',
    diagram_key VARCHAR(100) DEFAULT NULL COMMENT '流程图KEY',
    version INT DEFAULT 1 COMMENT '版本号',
    status TINYINT DEFAULT 0 COMMENT '状态(0-草稿,1-已部署,2-挂起)',
    flowable_definition_id VARCHAR(100) DEFAULT NULL COMMENT 'Flowable流程定义ID',
    deploy_time DATETIME DEFAULT NULL COMMENT '发布时间',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_process_key (process_key),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程定义表';

-- 流程实例表
CREATE TABLE IF NOT EXISTS wf_instance (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '实例ID',
    definition_id BIGINT NOT NULL COMMENT '流程定义ID',
    process_key VARCHAR(100) NOT NULL COMMENT '流程Key',
    name VARCHAR(200) NOT NULL COMMENT '流程名称',
    start_user_id BIGINT NOT NULL COMMENT '发起人ID',
    start_user_name VARCHAR(50) DEFAULT NULL COMMENT '发起人名称',
    current_node_key VARCHAR(100) DEFAULT NULL COMMENT '当前节点Key',
    current_node_name VARCHAR(100) DEFAULT NULL COMMENT '当前节点名称',
    form_data_id BIGINT DEFAULT NULL COMMENT '表单数据ID',
    flowable_instance_id VARCHAR(100) DEFAULT NULL COMMENT 'Flowable流程实例ID',
    status TINYINT DEFAULT 0 COMMENT '状态(0-进行中,1-已完成,2-已取消,3-已驳回)',
    start_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    end_time DATETIME DEFAULT NULL COMMENT '结束时间',
    duration BIGINT DEFAULT NULL COMMENT '耗时(秒)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_definition_id (definition_id),
    KEY idx_start_user_id (start_user_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程实例表';

-- 流程任务表
CREATE TABLE IF NOT EXISTS wf_task (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    instance_id BIGINT NOT NULL COMMENT '流程实例ID',
    definition_id BIGINT NOT NULL COMMENT '流程定义ID',
    task_name VARCHAR(100) NOT NULL COMMENT '任务名称',
    task_key VARCHAR(100) NOT NULL COMMENT '任务Key',
    assignee_id BIGINT DEFAULT NULL COMMENT '办理人ID',
    assignee_name VARCHAR(50) DEFAULT NULL COMMENT '办理人名称',
    candidate_users VARCHAR(2000) DEFAULT NULL COMMENT '候选用户ID列表(JSON)',
    candidate_roles VARCHAR(2000) DEFAULT NULL COMMENT '候选角色ID列表(JSON)',
    form_data_id BIGINT DEFAULT NULL COMMENT '表单数据ID',
    flowable_task_id VARCHAR(100) DEFAULT NULL COMMENT 'Flowable任务ID',
    task_type TINYINT DEFAULT 0 COMMENT '任务类型(0-正常,1-转办,2-委托,3-加签)',
    status TINYINT DEFAULT 0 COMMENT '状态(0-待处理,1-已完成,2-已取消)',
    priority TINYINT DEFAULT 0 COMMENT '优先级(0-普通,1-紧急,2-加急)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    complete_time DATETIME DEFAULT NULL COMMENT '完成时间',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_instance_id (instance_id),
    KEY idx_assignee_id (assignee_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程任务表';

-- 流程历史表
CREATE TABLE IF NOT EXISTS wf_history (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '历史ID',
    instance_id BIGINT NOT NULL COMMENT '流程实例ID',
    task_id BIGINT DEFAULT NULL COMMENT '任务ID',
    definition_id BIGINT NOT NULL COMMENT '流程定义ID',
    definition_version INT DEFAULT NULL COMMENT '流程定义版本',
    node_key VARCHAR(100) DEFAULT NULL COMMENT '节点Key',
    node_name VARCHAR(100) DEFAULT NULL COMMENT '节点名称',
    operate_type TINYINT DEFAULT 0 COMMENT '操作类型(0-提交,1-同意,2-拒绝,3-转办,4-委托,5-退回,6-撤回,7-终止)',
    operate_user_id BIGINT NOT NULL COMMENT '操作人ID',
    operate_user_name VARCHAR(50) DEFAULT NULL COMMENT '操作人名称',
    comment VARCHAR(500) DEFAULT NULL COMMENT '审批意见',
    operate_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    instance_status TINYINT DEFAULT NULL COMMENT '流程实例状态',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_instance_id (instance_id),
    KEY idx_operate_user_id (operate_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程历史表';

-- =====================================================
-- 即时通讯相关表
-- =====================================================

-- IM会话表
CREATE TABLE IF NOT EXISTS im_conversation (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '会话ID',
    type TINYINT DEFAULT 0 COMMENT '会话类型(0-单聊,1-群聊)',
    name VARCHAR(100) DEFAULT NULL COMMENT '会话名称',
    owner_id BIGINT DEFAULT NULL COMMENT '群主ID',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
    last_message_id BIGINT DEFAULT NULL COMMENT '最后消息ID',
    last_message_time DATETIME DEFAULT NULL COMMENT '��后消息时间',
    unread_count INT DEFAULT 0 COMMENT '未读消息数',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-置顶,2-免打扰)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_owner_id (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IM会话表';

-- IM消息表
CREATE TABLE IF NOT EXISTS im_message (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    conversation_id BIGINT NOT NULL COMMENT '会话ID',
    sender_id BIGINT NOT NULL COMMENT '发送者ID',
    sender_name VARCHAR(50) DEFAULT NULL COMMENT '发送者名称',
    sender_avatar VARCHAR(255) DEFAULT NULL COMMENT '发送者头像',
    msg_type TINYINT DEFAULT 0 COMMENT '消息类型(0-文本,1-图片,2-文件,3-语音,4-视频)',
    content LONGTEXT DEFAULT NULL COMMENT '消息内容',
    quote_id BIGINT DEFAULT NULL COMMENT '引用消息ID',
    attachment_url VARCHAR(500) DEFAULT NULL COMMENT '附件URL',
    attachment_name VARCHAR(200) DEFAULT NULL COMMENT '附件名称',
    attachment_size BIGINT DEFAULT NULL COMMENT '附件大小',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-已撤回)',
    send_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_conversation_id (conversation_id),
    KEY idx_sender_id (sender_id),
    KEY idx_send_time (send_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IM消息表';

-- IM群组表
CREATE TABLE IF NOT EXISTS im_group (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '群组ID',
    name VARCHAR(100) NOT NULL COMMENT '群名称',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '群头像',
    owner_id BIGINT NOT NULL COMMENT '群主ID',
    announcement VARCHAR(500) DEFAULT NULL COMMENT '群公告',
    description VARCHAR(500) DEFAULT NULL COMMENT '群描述',
    member_count INT DEFAULT 0 COMMENT '成员数量',
    is_all TINYINT DEFAULT 0 COMMENT '是否全员群(0-否,1-是)',
    join_type TINYINT DEFAULT 0 COMMENT '加群方式(0-允许所有人,1-需要验证,2-禁止加入)',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-解散)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_owner_id (owner_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IM群组表';

-- IM群组成员表
CREATE TABLE IF NOT EXISTS im_group_member (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '成员ID',
    group_id BIGINT NOT NULL COMMENT '群组ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    user_name VARCHAR(50) DEFAULT NULL COMMENT '用户名称',
    user_avatar VARCHAR(255) DEFAULT NULL COMMENT '用户头像',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '群昵称',
    role TINYINT DEFAULT 0 COMMENT '角色(0-成员,1-管理员,2-群主)',
    join_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '入群时间',
    last_speak_time DATETIME DEFAULT NULL COMMENT '最后发言时间',
    mute_end_time DATETIME DEFAULT NULL COMMENT '禁言结束时间',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-退群,2-踢出)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    UNIQUE KEY uk_group_user (group_id, user_id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='IM群组成员表';

-- =====================================================
-- 文件存储相关表
-- =====================================================

-- 文件信息表
CREATE TABLE IF NOT EXISTS file_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '文件ID',
    file_name VARCHAR(200) NOT NULL COMMENT '文件名称',
    file_path VARCHAR(500) DEFAULT NULL COMMENT '文件路径',
    file_url VARCHAR(500) DEFAULT NULL COMMENT '文件URL',
    file_size BIGINT DEFAULT NULL COMMENT '文件大小(字节)',
    file_type VARCHAR(50) DEFAULT NULL COMMENT '文件类型',
    mime_type VARCHAR(100) DEFAULT NULL COMMENT 'MIME类型',
    storage_type VARCHAR(20) DEFAULT 'local' COMMENT '存储引擎(local/minio/oss/cos)',
    bucket_name VARCHAR(100) DEFAULT NULL COMMENT '存储桶/目录',
    file_key VARCHAR(100) DEFAULT NULL COMMENT '唯一标识符',
    upload_user_id BIGINT DEFAULT NULL COMMENT '上传者ID',
    upload_user_name VARCHAR(50) DEFAULT NULL COMMENT '上传者名称',
    folder_id BIGINT DEFAULT NULL COMMENT '文件夹ID',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-删除)',
    upload_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_folder_id (folder_id),
    KEY idx_upload_user_id (upload_user_id),
    KEY idx_storage_type (storage_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表';

-- 文件夹表
CREATE TABLE IF NOT EXISTS file_folder (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '文件夹ID',
    name VARCHAR(100) NOT NULL COMMENT '文件夹名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父文件夹ID',
    parent_path VARCHAR(500) DEFAULT '0' COMMENT '父级路径',
    storage_type VARCHAR(20) DEFAULT 'local' COMMENT '存储类型',
    file_count INT DEFAULT 0 COMMENT '文件数量',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-删除)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件夹表';

-- =====================================================
-- 报表服务相关表
-- =====================================================

CREATE TABLE IF NOT EXISTS report_definition (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '报表ID',
    name VARCHAR(100) NOT NULL COMMENT '报表名称',
    code VARCHAR(100) NOT NULL COMMENT '报表编码',
    description VARCHAR(500) DEFAULT NULL COMMENT '报表描述',
    sql_content LONGTEXT DEFAULT NULL COMMENT 'SQL查询语句',
    config_json LONGTEXT DEFAULT NULL COMMENT '配置JSON',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code, tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报表定义表';

-- =====================================================
-- 系统监控相关表
-- =====================================================

CREATE TABLE IF NOT EXISTS sys_log (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    module VARCHAR(50) DEFAULT NULL COMMENT '模块名',
    biz_type VARCHAR(50) DEFAULT NULL COMMENT '业务类型',
    user_id BIGINT DEFAULT NULL COMMENT '用户ID',
    user_name VARCHAR(50) DEFAULT NULL COMMENT '用户名',
    method VARCHAR(200) DEFAULT NULL COMMENT '请求方法',
    url VARCHAR(500) DEFAULT NULL COMMENT '请求地址',
    ip VARCHAR(50) DEFAULT NULL COMMENT 'IP地址',
    location VARCHAR(100) DEFAULT NULL COMMENT '操作地点',
    params TEXT DEFAULT NULL COMMENT '请求参数',
    result TEXT DEFAULT NULL COMMENT '返回结果',
    status TINYINT DEFAULT 0 COMMENT '状态(0-成功,1-失败)',
    duration BIGINT DEFAULT NULL COMMENT '耗时(毫秒)',
    error_msg TEXT DEFAULT NULL COMMENT '错误信息',
    trace_id VARCHAR(100) DEFAULT NULL COMMENT '追踪ID',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

-- =====================================================
-- 代码生成相关表
-- =====================================================

CREATE TABLE IF NOT EXISTS gen_table (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '表ID',
    table_name VARCHAR(100) NOT NULL COMMENT '表名称',
    table_comment VARCHAR(200) DEFAULT NULL COMMENT '表描述',
    class_name VARCHAR(100) DEFAULT NULL COMMENT '实体类名',
    package_name VARCHAR(100) DEFAULT NULL COMMENT '包名',
    module_name VARCHAR(50) DEFAULT NULL COMMENT '模块名',
    business_name VARCHAR(50) DEFAULT NULL COMMENT '业务名',
    function_name VARCHAR(50) DEFAULT NULL COMMENT '功能名',
    function_author VARCHAR(50) DEFAULT NULL COMMENT '功能作者',
    template_path VARCHAR(200) DEFAULT NULL COMMENT '模板路径',
    status TINYINT DEFAULT 0 COMMENT '状态(0-正常,1-禁用)',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成表';

CREATE TABLE IF NOT EXISTS gen_table_column (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '字段ID',
    table_id BIGINT NOT NULL COMMENT '所属表ID',
    column_name VARCHAR(100) NOT NULL COMMENT '字段名称',
    column_comment VARCHAR(200) DEFAULT NULL COMMENT '字段注释',
    column_type VARCHAR(50) DEFAULT NULL COMMENT '字段类型',
    java_type VARCHAR(100) DEFAULT NULL COMMENT 'Java类型',
    java_field VARCHAR(100) DEFAULT NULL COMMENT 'Java字段名',
    is_pk TINYINT DEFAULT 0 COMMENT '是否主键(0-否,1-是)',
    is_increment TINYINT DEFAULT 0 COMMENT '是否自增(0-否,1-是)',
    is_required TINYINT DEFAULT 0 COMMENT '是否必填(0-否,1-是)',
    is_insert TINYINT DEFAULT 0 COMMENT '是否为插入字段(0-否,1-是)',
    is_edit TINYINT DEFAULT 0 COMMENT '是否编辑字段(0-否,1-是)',
    is_list TINYINT DEFAULT 0 COMMENT '是否列表字段(0-否,1-是)',
    is_query TINYINT DEFAULT 0 COMMENT '是否查询字段(0-否,1-是)',
    query_type VARCHAR(50) DEFAULT 'EQ' COMMENT '查询方式(EQ/NE/LIKE/GT/LT/BETWEEN)',
    html_type VARCHAR(50) DEFAULT NULL COMMENT '前端组件类型(input/select/radio/checkbox/datetime/editor)',
    dict_type VARCHAR(100) DEFAULT NULL COMMENT '字典类型',
    sort INT DEFAULT 0 COMMENT '排序',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志(0-未删除,1-已删除)',
    PRIMARY KEY (id),
    KEY idx_table_id (table_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成字段表';

-- =====================================================
-- 登录日志表
-- =====================================================

CREATE TABLE IF NOT EXISTS sys_login_log (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    user_id BIGINT DEFAULT NULL COMMENT '用户ID',
    username VARCHAR(50) DEFAULT NULL COMMENT '用户名',
    ip VARCHAR(50) DEFAULT NULL COMMENT 'IP地址',
    location VARCHAR(100) DEFAULT NULL COMMENT '登录地点',
    browser VARCHAR(100) DEFAULT NULL COMMENT '浏览器',
    os VARCHAR(100) DEFAULT NULL COMMENT '操作系统',
    status TINYINT DEFAULT 0 COMMENT '登录状态(0-成功,1-失败)',
    msg VARCHAR(500) DEFAULT NULL COMMENT '提示消息',
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_login_time (login_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统登录日志表';

-- =====================================================
-- 定时任务相关表
-- =====================================================

CREATE TABLE IF NOT EXISTS sys_job (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    job_name VARCHAR(100) NOT NULL COMMENT '任务名称',
    job_group VARCHAR(100) DEFAULT 'DEFAULT' COMMENT '任务组名',
    job_handler VARCHAR(100) DEFAULT NULL COMMENT '任务处理器',
    cron_expression VARCHAR(100) DEFAULT NULL COMMENT 'Cron表达式',
    misfire_policy INT DEFAULT 0 COMMENT '失败策略',
    concurrent INT DEFAULT 1 COMMENT '是否并发(0-否,1-是)',
    param_json TEXT DEFAULT NULL COMMENT '参数JSON',
    status TINYINT DEFAULT 0 COMMENT '状态(0-停止,1-运行)',
    description VARCHAR(500) DEFAULT NULL COMMENT '任务描述',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标志',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务表';