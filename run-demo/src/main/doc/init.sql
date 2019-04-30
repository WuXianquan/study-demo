-- 用户信息表
CREATE TABLE user (
  id int(11) NOT NULL,
  password varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  create_time datetime NOT NULL COMMENT '注册时间',
  update_time datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;