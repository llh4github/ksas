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
ALTER TABLE "public"."auth_user" OWNER TO "postgres";
COMMENT ON COLUMN "public"."auth_user"."id" IS '主键ID';
COMMENT ON COLUMN "public"."auth_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."auth_user"."password" IS '密码';
COMMENT ON COLUMN "public"."auth_user"."nickname" IS '昵称';
COMMENT ON COLUMN "public"."auth_user"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."auth_user"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."auth_user"."updated_by_user_id" IS '更新者ID';
COMMENT ON COLUMN "public"."auth_user"."created_by_user_id" IS '创建者ID';
