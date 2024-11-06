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
COMMENT ON TABLE "public"."auth_role" IS '角色表';
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
   CONSTRAINT "link_user_role_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."auth_role" ("id") ON DELETE CASCADE ON UPDATE NO ACTION,
   CONSTRAINT "link_user_role_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."auth_user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION
)
;
COMMENT ON TABLE "public"."link_user_role" IS '用户-角色关联表';
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

CREATE TABLE "public"."link_role_endpoint" (
   "role_id" int8 NOT NULL,
   "endpoint_id" int8 NOT NULL,
   CONSTRAINT "link_role_endpoint_pkey" PRIMARY KEY ("role_id", "endpoint_id"),
   CONSTRAINT "link_role_endpoint_endpoint_id_fkey" FOREIGN KEY ("endpoint_id") REFERENCES "public"."auth_endpoint_perm" ("id") ON DELETE CASCADE ON UPDATE NO ACTION,
   CONSTRAINT "link_role_endpoint_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."auth_role" ("id") ON DELETE CASCADE ON UPDATE NO ACTION
)
;

COMMENT ON TABLE "public"."link_role_endpoint" IS '角色-接口权限关联表';
ALTER TABLE "public"."link_role_endpoint"    OWNER TO "postgres";
COMMENT ON COLUMN "public"."link_role_endpoint"."role_id" IS '角色表ID';
COMMENT ON COLUMN "public"."link_role_endpoint"."endpoint_id" IS '接口权限表ID';


CREATE TABLE "public"."auth_page_router" (
 "id" INT8 NOT NULL,
 "title" TEXT COLLATE "pg_catalog"."default" NOT NULL,
 "path" TEXT COLLATE "pg_catalog"."default" NOT NULL,
 "name" TEXT COLLATE "pg_catalog"."default" NOT NULL,
 "redirect" TEXT COLLATE "pg_catalog"."default",
 "icon" TEXT COLLATE "pg_catalog"."default",
 "show_link" BOOL NOT NULL DEFAULT TRUE,
 "rank" INT2 NOT NULL DEFAULT 1,
 "parent_id" INT8,
 "created_time" TIMESTAMP ( 6 ),
 "updated_time" TIMESTAMP ( 6 ),
 "updated_by_user_id" INT8,
 "created_by_user_id" INT8,
 CONSTRAINT "auth_page_router_pkey" PRIMARY KEY ( "id" ),
 CONSTRAINT "auth_page_router_name_key" UNIQUE ( "name" )
);
ALTER TABLE "public"."auth_page_router" OWNER TO "postgres";
COMMENT ON COLUMN "public"."auth_page_router"."id" IS '主键ID';
COMMENT ON COLUMN "public"."auth_page_router"."title" IS '菜单名称';
COMMENT ON COLUMN "public"."auth_page_router"."path" IS '角色代码';
COMMENT ON COLUMN "public"."auth_page_router"."name" IS '路由名称（必须保持唯一）';
COMMENT ON COLUMN "public"."auth_page_router"."redirect" IS '路由重定向（默认跳转地址）';
COMMENT ON COLUMN "public"."auth_page_router"."icon" IS '菜单图标';
COMMENT ON COLUMN "public"."auth_page_router".show_link IS '是否在菜单中显示';
COMMENT ON COLUMN "public"."auth_page_router"."rank" IS ' 菜单排序，值越高排的越后（只针对顶级路由）';
COMMENT ON COLUMN "public"."auth_page_router"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."auth_page_router"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."auth_page_router"."updated_by_user_id" IS '更新者ID';
COMMENT ON COLUMN "public"."auth_page_router"."created_by_user_id" IS '创建者ID';
COMMENT ON TABLE "public"."auth_page_router" IS '页面路由表';

CREATE TABLE "public"."link_role_page_router" (
  "role_id" INT8 NOT NULL,
  "page_router_id" INT8 NOT NULL,
  CONSTRAINT "link_role_page_router_page_router_id_fkey" FOREIGN KEY ( "page_router_id" ) REFERENCES "public"."auth_page_router" ( "id" ) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT "link_role_page_router_role_id_fkey" FOREIGN KEY ( "role_id" ) REFERENCES "public"."auth_role" ( "id" ) ON DELETE CASCADE ON UPDATE NO ACTION
);
ALTER TABLE "public"."link_role_page_router" OWNER TO "postgres";
COMMENT ON COLUMN "public"."link_role_page_router"."role_id" IS '角色表ID';
COMMENT ON COLUMN "public"."link_role_page_router"."page_router_id" IS '页面路由表ID';
COMMENT ON TABLE "public"."link_role_page_router" IS '角色-页面路由关联表';

CREATE TABLE "public"."link_router_endpoint" (
 "router_id" INT8 NOT NULL,
 "endpoint_id" INT8 NOT NULL,
 CONSTRAINT "link_router_endpoint_pkey" PRIMARY KEY ( "router_id", "endpoint_id" ),
 CONSTRAINT "link_router_endpoint_endpoint_id_fkey" FOREIGN KEY ( "endpoint_id" ) REFERENCES "public"."auth_endpoint_perm" ( "id" ) ON DELETE RESTRICT ON UPDATE NO ACTION,
 CONSTRAINT "link_router_endpoint_router_id_fkey" FOREIGN KEY ( "router_id" ) REFERENCES "public"."auth_page_router" ( "id" ) ON DELETE RESTRICT ON UPDATE NO ACTION
);
ALTER TABLE "public"."link_router_endpoint" OWNER TO "postgres";
COMMENT ON COLUMN "public"."link_router_endpoint"."router_id" IS '前端页面路由表ID';
COMMENT ON COLUMN "public"."link_router_endpoint"."endpoint_id" IS '接口权限表ID';
COMMENT ON TABLE "public"."link_router_endpoint" IS '前端页面路由-接口权限关联表';

