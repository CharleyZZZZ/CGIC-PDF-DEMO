DROP TABLE
IF EXISTS pubud.sys_user_role;

CREATE TABLE pubud.sys_user_role (
	`USER_ROLE_ID` BIGINT NOT NULL auto_increment PRIMARY KEY,
	`USER_ID` BIGINT NOT NULL,
	`ROLE_ID` BIGINT NOT NULL,
	OBJECT_VERSION_NUMBER BIGINT DEFAULT 1 NOT NULL,
	CREATION_DATE datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CREATED_BY BIGINT NOT NULL DEFAULT - 1,
	LAST_UPDATED_BY BIGINT NOT NULL DEFAULT - 1,
	LAST_UPDATE_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE pubud.sys_user_role COMMENT '用户角色关系表';

ALTER TABLE pubud.sys_user_role MODIFY `USER_ROLE_ID` BIGINT NOT NULL auto_increment COMMENT '表ID，主键，供其他表做外键';

ALTER TABLE pubud.sys_user_role MODIFY `USER_ID` BIGINT NOT NULL COMMENT '用户ID';

ALTER TABLE pubud.sys_user_role MODIFY `ROLE_ID` BIGINT NOT NULL COMMENT '角色ID';

ALTER TABLE pubud.sys_user_role MODIFY `OBJECT_VERSION_NUMBER` BIGINT DEFAULT 1 NOT NULL COMMENT '行版本号，用来处理锁';

ALTER TABLE pubud.sys_user_role MODIFY `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间';

ALTER TABLE pubud.sys_user_role MODIFY `CREATED_BY` BIGINT DEFAULT 1 NOT NULL COMMENT '创建人';

ALTER TABLE pubud.sys_user_role MODIFY `LAST_UPDATED_BY` BIGINT DEFAULT 1 NOT NULL COMMENT '上次更新人';

ALTER TABLE pubud.sys_user_role MODIFY `LAST_UPDATE_DATE` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '上次更新时间';