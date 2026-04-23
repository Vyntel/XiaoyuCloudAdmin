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