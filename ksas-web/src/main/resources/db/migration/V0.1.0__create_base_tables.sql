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


CREATE TABLE "public"."link_user_role" (
   "role_id" int8 NOT NULL,
   "user_id" int8 NOT NULL,
   CONSTRAINT "link_user_role_pkey" PRIMARY KEY ("role_id", "user_id"),
   CONSTRAINT "link_user_role_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."auth_role" ("id") ON DELETE RESTRICT ON UPDATE NO ACTION,
   CONSTRAINT "link_user_role_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."auth_user" ("id") ON DELETE RESTRICT ON UPDATE NO ACTION
)
;

ALTER TABLE "public"."link_user_role"
    OWNER TO "postgres";

COMMENT ON COLUMN "public"."link_user_role"."role_id" IS '角色表ID';
COMMENT ON COLUMN "public"."link_user_role"."user_id" IS '用户表ID';

