CREATE TABLE "public"."auth_role" (
  "id" int8 NOT NULL,
  "title" text COLLATE "pg_catalog"."default" NOT NULL,
  "code" text COLLATE "pg_catalog"."default" NOT NULL,
  "created_time" timestamp(6),
  "updated_time" timestamp(6),
  "updated_by_user_id" int8,
  "created_by_user_id" int8,
  CONSTRAINT "auth_role_pkey" PRIMARY KEY ("id"),
  CONSTRAINT "auth_role_code_key" UNIQUE ("code")
)
;
COMMENT ON TABLE "public"."auth_user" IS '角色表';
ALTER TABLE "public"."auth_role"  OWNER TO "postgres";
COMMENT ON COLUMN "public"."auth_role"."id" IS '主键ID';
COMMENT ON COLUMN "public"."auth_role"."title" IS '角色名称';
COMMENT ON COLUMN "public"."auth_role"."code" IS '角色代码';
COMMENT ON COLUMN "public"."auth_role"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."auth_role"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."auth_role"."updated_by_user_id" IS '更新者ID';
COMMENT ON COLUMN "public"."auth_role"."created_by_user_id" IS '创建者ID';


CREATE TABLE "public"."auth_user" (
  "id" INT8 NOT NULL,
  "username" TEXT COLLATE "pg_catalog"."default" NOT NULL,
  "password" TEXT COLLATE "pg_catalog"."default" NOT NULL,
  "nickname" TEXT COLLATE "pg_catalog"."default" NOT NULL,
  "created_time" TIMESTAMP ( 6 ),
  "updated_time" TIMESTAMP ( 6 ),
  "updated_by_user_id" INT8,
  "created_by_user_id" INT8,
  CONSTRAINT "auth_user_pkey" PRIMARY KEY ( "id" ),
  CONSTRAINT "auth_user_username_key" UNIQUE ( "username" )
);
COMMENT ON TABLE "public"."auth_user" IS '用户表';
ALTER TABLE "public"."auth_user" OWNER TO "postgres";
COMMENT ON COLUMN "public"."auth_user"."id" IS '主键ID';
COMMENT ON COLUMN "public"."auth_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."auth_user"."password" IS '密码';
COMMENT ON COLUMN "public"."auth_user"."nickname" IS '昵称';
COMMENT ON COLUMN "public"."auth_user"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."auth_user"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."auth_user"."updated_by_user_id" IS '更新者ID';
COMMENT ON COLUMN "public"."auth_user"."created_by_user_id" IS '创建者ID';


CREATE TABLE "public"."link_user_role" (
   "role_id" int8 NOT NULL,
   "user_id" int8 NOT NULL,
   CONSTRAINT "link_user_role_pkey" PRIMARY KEY ("role_id", "user_id"),
   CONSTRAINT "link_user_role_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."auth_role" ("id") ON DELETE RESTRICT ON UPDATE NO ACTION,
   CONSTRAINT "link_user_role_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."auth_user" ("id") ON DELETE RESTRICT ON UPDATE NO ACTION
)
;
ALTER TABLE "public"."link_user_role"  OWNER TO "postgres";
COMMENT ON COLUMN "public"."link_user_role"."role_id" IS '角色表ID';
COMMENT ON COLUMN "public"."link_user_role"."user_id" IS '用户表ID';

CREATE TABLE "public"."auth_endpoint_perm" (
   "id" int8 NOT NULL,
   "title" text COLLATE "pg_catalog"."default" NOT NULL,
   "path" text COLLATE "pg_catalog"."default" NOT NULL,
   "perm_code" text COLLATE "pg_catalog"."default" NOT NULL,
   "method" text COLLATE "pg_catalog"."default" NOT NULL,
   "remark" text COLLATE "pg_catalog"."default",
   "created_time" timestamp(6),
   "updated_time" timestamp(6),
   "updated_by_user_id" int8,
   "created_by_user_id" int8,
   CONSTRAINT "auth_endpoint_perm_pkey" PRIMARY KEY ("id"),
   CONSTRAINT "auth_endpoint_perm_code_key" UNIQUE ("perm_code")
)
;

ALTER TABLE "public"."auth_endpoint_perm"  OWNER TO "postgres";
COMMENT ON COLUMN "public"."auth_endpoint_perm"."id" IS '主键ID';
COMMENT ON COLUMN "public"."auth_endpoint_perm"."title" IS '标题';
COMMENT ON COLUMN "public"."auth_endpoint_perm"."path" IS '角色代码';
COMMENT ON COLUMN "public"."auth_endpoint_perm"."perm_code" IS '权限代码';
COMMENT ON COLUMN "public"."auth_endpoint_perm"."method" IS '接口请求方法';
COMMENT ON COLUMN "public"."auth_endpoint_perm"."remark" IS '备注';
COMMENT ON COLUMN "public"."auth_endpoint_perm"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."auth_endpoint_perm"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."auth_endpoint_perm"."updated_by_user_id" IS '更新者ID';
COMMENT ON COLUMN "public"."auth_endpoint_perm"."created_by_user_id" IS '创建者ID';
COMMENT ON TABLE "public"."auth_endpoint_perm" IS '接口权限表';