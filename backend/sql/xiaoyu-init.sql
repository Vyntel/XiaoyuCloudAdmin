-- 小羽快速开发框架 - 数据库初始化脚本
-- 创建数据库

-- 创建数据库
CREATE DATABASE IF NOT EXISTS xiaoyu_cloud DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE xiaoyu_cloud;

-- =====================================================
-- 系统管理相关表
-- =====================================================

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    user_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
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
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (user_id),
    UNIQUE KEY uk_username (username, tenant_id) COMMENT '用户名唯一',
    KEY idx_dept_id (dept_id) COMMENT '部门ID索引',
    KEY idx_tenant_id (tenant_id) COMMENT '租户ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    role_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(50) NOT NULL COMMENT '角色编码',
    role_sort INT DEFAULT 0 COMMENT '显示顺序',
    data_scope TINYINT DEFAULT 1 COMMENT '数据范围（1-全部，2-自定义，3-本部门，4-本部门及以下，5-仅本人）',
    status TINYINT DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (role_id),
    UNIQUE KEY uk_role_code (role_code, tenant_id) COMMENT '角色编码唯一',
    KEY idx_tenant_id (tenant_id) COMMENT '租户ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- 菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
    menu_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    menu_name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    order_num INT DEFAULT 0 COMMENT '显示顺序',
    path VARCHAR(200) DEFAULT NULL COMMENT '路由地址',
    component VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    menu_type CHAR(1) DEFAULT NULL COMMENT '菜单类型（M-目录，F-菜单，B-按钮）',
    visible CHAR(1) DEFAULT '0' COMMENT '菜单状态（0-显示，1-隐藏）',
    status CHAR(1) DEFAULT '0' COMMENT '菜单状态（0-正常，1-禁用）',
    perms VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    icon VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (menu_id),
    KEY idx_parent_id (parent_id) COMMENT '父菜单ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

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
    dept_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    dept_name VARCHAR(50) NOT NULL COMMENT '部门名称',
    dept_code VARCHAR(50) DEFAULT NULL COMMENT '部门编码',
    leader_user_id BIGINT DEFAULT NULL COMMENT '负责人ID',
    phone VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (dept_id),
    KEY idx_parent_id (parent_id) COMMENT '父部门ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统部门表';

-- 岗位表
CREATE TABLE IF NOT EXISTS sys_post (
    post_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    post_code VARCHAR(50) NOT NULL COMMENT '岗位编码',
    post_name VARCHAR(50) NOT NULL COMMENT '岗位名称',
    post_sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (post_id),
    UNIQUE KEY uk_post_code (post_code, tenant_id) COMMENT '岗位编码唯一'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统岗位表';

-- 用户和岗位关联表
CREATE TABLE IF NOT EXISTS sys_user_post (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    post_id BIGINT NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (user_id, post_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和岗位关联表';

-- 字典类型表
CREATE TABLE IF NOT EXISTS sys_dict_type (
    dict_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    dict_name VARCHAR(100) NOT NULL COMMENT '字典名称',
    dict_code VARCHAR(100) NOT NULL COMMENT '字典编码',
    dict_type VARCHAR(100) DEFAULT NULL COMMENT '字典类型',
    status TINYINT DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (dict_id),
    UNIQUE KEY uk_dict_code (dict_code) COMMENT '字典编码唯一'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE IF NOT EXISTS sys_dict_data (
    dict_code BIGINT NOT NULL AUTO_INCREMENT COMMENT '字典标签',
    dict_sort INT DEFAULT 0 COMMENT '字典排序',
    dict_label VARCHAR(100) NOT NULL COMMENT '字典标签',
    dict_value VARCHAR(100) NOT NULL COMMENT '字典键值',
    dict_type VARCHAR(100) NOT NULL COMMENT '字典类型',
    css_class VARCHAR(100) DEFAULT NULL COMMENT '样式属性',
    list_class VARCHAR(100) DEFAULT NULL COMMENT '表格回显样式',
    is_default CHAR(1) DEFAULT 'N' COMMENT '是否默认（Y-是，N-否）',
    status TINYINT DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
    tenant_id BIGINT DEFAULT 1 COMMENT '租户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (dict_code),
    KEY idx_dict_type (dict_type) COMMENT '字典类型索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- 参数配置表
CREATE TABLE IF NOT EXISTS sys_config (
    config_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    config_name VARCHAR(100) NOT NULL COMMENT '参数名称',
    config_key VARCHAR(100) NOT NULL COMMENT '参数键名',
    config_value VARCHAR(500) NOT NULL COMMENT '参数键值',
    config_type CHAR(1) DEFAULT 'N' COMMENT '系统内置（Y-是，N-否）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (config_id),
    UNIQUE KEY uk_config_key (config_key) COMMENT '参数键名唯一'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='参数配置表';

-- 租户表
CREATE TABLE IF NOT EXISTS sys_tenant (
    tenant_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '租户ID',
    tenant_code VARCHAR(50) NOT NULL COMMENT '租户编码',
    tenant_name VARCHAR(100) NOT NULL COMMENT '租户名称',
    tenant_type TINYINT DEFAULT 1 COMMENT '租户类型（1-普通租户）',
    domain VARCHAR(100) DEFAULT NULL COMMENT '域名',
    package_id BIGINT DEFAULT NULL COMMENT '套餐ID',
    expire_time DATETIME DEFAULT NULL COMMENT '过期时间',
    status TINYINT DEFAULT 0 COMMENT '状态（0-正常，1-禁用）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (tenant_id),
    UNIQUE KEY uk_tenant_code (tenant_code) COMMENT '租户编码唯一'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='租户表';

-- =====================================================
-- 初始化数据
-- =====================================================

-- 初始化超级管理员用户 (用户名: admin, 密码: 123456)
INSERT INTO sys_user (user_id, username, password, nickname, status, tenant_id) VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '超级管理员', 0, 1);

-- 初始化租户
INSERT INTO sys_tenant (tenant_id, tenant_code, tenant_name, tenant_type, status) VALUES
(1, 'default', '默认租户', 1, 0);

-- 初始化顶级部门
INSERT INTO sys_dept (dept_id, dept_name, dept_code, status, tenant_id) VALUES
(1, '小羽科技', 'XIAOYU', 0, 1);