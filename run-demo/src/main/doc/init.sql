-- 用户信息表
CREATE TABLE t_user (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  username varchar(255) NOT NULL COMMENT '用户名',
  password varchar(255) NOT NULL COMMENT '密码',
  create_time datetime NOT NULL COMMENT '注册时间',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  is_del tinyint(1) DEFAULT '0' COMMENT '是否删除: 0:可用 -1:已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- 基础角色信息表
CREATE TABLE t_role (
  id varchar(32) NOT NULL COMMENT '主键',
  name varchar(255) DEFAULT NULL,
  create_time datetime NOT NULL COMMENT '创建时间',
  update_time datetime DEFAULT NULL COMMENT '修改时间',
  is_del tinyint(1) DEFAULT 0 COMMENT '是否删除: 0:可用 -1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础角色信息表';

-- 用户角色信息表
CREATE TABLE t_user_role (
  user_id bigint(20) NOT NULL COMMENT '用户ID',
  role_id varchar(32) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 权限基础信息表
CREATE TABLE t_permission (
  id varchar(32) NOT NULL COMMENT '主键',
  name varchar(255) NOT NULL COMMENT '权限描述',
  url varchar(255) NOT NULL COMMENT '权限链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础权限表';

-- 角色权限信息表
CREATE TABLE t_role_permission (
  role_id varchar(32) NOT NULL COMMENT '角色ID',
  permission_id varchar(32) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';