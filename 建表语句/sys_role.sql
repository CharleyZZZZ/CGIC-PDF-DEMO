DROP TABLE
IF EXISTS pubud.sys_role;

CREATE TABLE pubud.sys_role (
	`ROLE_ID` BIGINT NOT NULL auto_increment PRIMARY KEY,
	`ROLE_NAME` VARCHAR (255) NOT NULL,
	`ROLE_CODE` VARCHAR (255) NOT NULL,
	OBJECT_VERSION_NUMBER BIGINT DEFAULT 1 NOT NULL,
	CREATION_DATE datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CREATED_BY BIGINT NOT NULL DEFAULT - 1,
	LAST_UPDATED_BY BIGINT NOT NULL DEFAULT - 1,
	LAST_UPDATE_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE pubud.sys_role COMMENT '用户角色表';

ALTER TABLE pubud.sys_role MODIFY `ROLE_ID` BIGINT NOT NULL auto_increment COMMENT '表ID，主键，供其他表做外键';

ALTER TABLE pubud.sys_role MODIFY `ROLE_CODE` VARCHAR (255) NOT NULL COMMENT '角色编码';

ALTER TABLE pubud.sys_role MODIFY `ROLE_NAME` VARCHAR (255) NOT NULL COMMENT '角色名称';

ALTER TABLE pubud.sys_role MODIFY `OBJECT_VERSION_NUMBER` BIGINT DEFAULT 1 NOT NULL COMMENT '行版本号，用来处理锁';

ALTER TABLE pubud.sys_role MODIFY `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间';

ALTER TABLE pubud.sys_role MODIFY `CREATED_BY` BIGINT DEFAULT 1 NOT NULL COMMENT '创建人';

ALTER TABLE pubud.sys_role MODIFY `LAST_UPDATED_BY` BIGINT DEFAULT 1 NOT NULL COMMENT '上次更新人';

ALTER TABLE pubud.sys_role MODIFY `LAST_UPDATE_DATE` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '上次更新时间';