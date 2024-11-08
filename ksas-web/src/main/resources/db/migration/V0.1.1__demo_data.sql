-- for dev environment
-- pwd is 123456
INSERT INTO "public"."auth_user" ("id", "username", "password", "nickname", "created_time", "updated_time",
                                  "updated_by_user_id", "created_by_user_id")
VALUES (114514, 'Tom', '$2a$10$a1bAuE1ZZlIPzrqXiJH5Xupp4mJsqlXs5JvCdvqH84KVZl8DoRrsO',
        'Tom', '2024-11-03 11:16:05', '2024-11-03 11:16:09', NULL, NULL);


INSERT INTO "public"."auth_role" ("id", "title", "code", "created_time", "updated_time", "updated_by_user_id", "created_by_user_id") VALUES (1, '管理员', 'admin', NULL, NULL, NULL, NULL);
INSERT INTO "public"."auth_role" ("id", "title", "code", "created_time", "updated_time", "updated_by_user_id", "created_by_user_id") VALUES (2, '舰长', 'captain', NULL, NULL, NULL, NULL);

INSERT INTO "public"."link_user_role" ("role_id", "user_id") VALUES (1, 114514);
INSERT INTO "public"."link_user_role" ("role_id", "user_id") VALUES (2, 114514);

INSERT INTO "public"."auth_endpoint_perm" ("id", "title", "path", "perm_code", "method", "remark", "created_time", "updated_time", "updated_by_user_id", "created_by_user_id") VALUES (1, '角色列表', '/auth/role/page', 'auth:role:page', 'POST', NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."auth_endpoint_perm" ("id", "title", "path", "perm_code", "method", "remark", "created_time", "updated_time", "updated_by_user_id", "created_by_user_id") VALUES (2, '新增角色', '/auth/role', 'auth:role:create', 'POST', NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."auth_endpoint_perm" ("id", "title", "path", "perm_code", "method", "remark", "created_time", "updated_time", "updated_by_user_id", "created_by_user_id") VALUES (3, '角色详情', '/auth/role', 'auth:role:detail', 'GET', NULL, NULL, NULL, NULL, NULL);

INSERT INTO "public"."link_role_endpoint" ("role_id", "endpoint_id") VALUES (1, 1);
INSERT INTO "public"."link_role_endpoint" ("role_id", "endpoint_id") VALUES (1, 2);
INSERT INTO "public"."link_role_endpoint" ("role_id", "endpoint_id") VALUES (1, 3);
INSERT INTO "public"."link_role_endpoint" ("role_id", "endpoint_id") VALUES (2, 1);

INSERT INTO "public"."auth_page_router" ("id", "title", "path", "name", "redirect", "icon", show_link, "rank", "created_time", "updated_time", "updated_by_user_id", "created_by_user_id") VALUES (1, '角色列表', '/views/role/index', 'ROLE_PAGE', NULL, NULL, 't', '1', NULL, NULL, NULL, NULL);

INSERT INTO "public"."link_router_endpoint" ("router_id", "endpoint_id") VALUES (1, 1);
INSERT INTO "public"."link_router_endpoint" ("router_id", "endpoint_id") VALUES (1, 2);
INSERT INTO "public"."link_router_endpoint" ("router_id", "endpoint_id") VALUES (1, 3);

INSERT INTO "public"."link_role_page_router" ("role_id", "page_router_id") VALUES (1, 1);
