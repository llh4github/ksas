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
