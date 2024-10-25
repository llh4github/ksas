CREATE TABLE auth_role
(
    id                 BIGINT NOT NULL,
    title              text NOT NULL,
    code               text  NOT NULL,
    created_time       TIMESTAMP DEFAULT NULL,
    updated_time       TIMESTAMP DEFAULT NULL,
    updated_by_user_id BIGINT DEFAULT NULL,
    created_by_user_id BIGINT DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE (code)
);
COMMENT ON COLUMN auth_role.id IS '主键ID';
COMMENT ON COLUMN auth_role.title IS '角色名称';
COMMENT ON COLUMN auth_role.code IS '角色代码';
COMMENT ON COLUMN auth_role.created_time IS '创建时间';
COMMENT ON COLUMN auth_role.updated_time IS '更新时间';
COMMENT ON COLUMN auth_role.updated_by_user_id IS '更新者ID';
COMMENT ON COLUMN auth_role.created_by_user_id IS '创建者ID';
