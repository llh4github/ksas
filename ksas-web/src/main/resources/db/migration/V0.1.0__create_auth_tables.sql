create table if not exists public.auth_role
(
    id                 bigint                not null
        primary key,
    title              text                  not null,
    code               text                  not null
        unique,
    created_time       timestamp(6),
    updated_time       timestamp(6),
    updated_by_user_id bigint,
    created_by_user_id bigint,
    remark             text default ''::text not null
);

comment on table public.auth_role is '角色表';

comment on column public.auth_role.id is '主键ID';

comment on column public.auth_role.title is '角色名称';

comment on column public.auth_role.code is '角色代码';

comment on column public.auth_role.created_time is '创建时间';

comment on column public.auth_role.updated_time is '更新时间';

comment on column public.auth_role.updated_by_user_id is '更新者ID';

comment on column public.auth_role.created_by_user_id is '创建者ID';

comment on column public.auth_role.remark is '备注';

alter table public.auth_role
    owner to postgres;

create table if not exists public.auth_user
(
    id                 bigint not null
        primary key,
    username           text   not null
        unique,
    password           text   not null,
    nickname           text   not null,
    created_time       timestamp(6),
    updated_time       timestamp(6),
    updated_by_user_id bigint,
    created_by_user_id bigint
);

comment on table public.auth_user is '用户表';

comment on column public.auth_user.id is '主键ID';

comment on column public.auth_user.username is '用户名';

comment on column public.auth_user.password is '密码';

comment on column public.auth_user.nickname is '昵称';

comment on column public.auth_user.created_time is '创建时间';

comment on column public.auth_user.updated_time is '更新时间';

comment on column public.auth_user.updated_by_user_id is '更新者ID';

comment on column public.auth_user.created_by_user_id is '创建者ID';

alter table public.auth_user
    owner to postgres;

create table if not exists public.link_user_role
(
    role_id bigint not null
        references public.auth_role
            on delete cascade,
    user_id bigint not null
        references public.auth_user
            on delete cascade,
    primary key (role_id, user_id)
);

comment on table public.link_user_role is '用户-角色关联表';

comment on column public.link_user_role.role_id is '角色表ID';

comment on column public.link_user_role.user_id is '用户表ID';

alter table public.link_user_role
    owner to postgres;

create table if not exists public.auth_page_router
(
    id                 bigint                 not null
        primary key,
    title              text                   not null,
    path               text                   not null,
    name               text                   not null
        unique,
    redirect           text,
    icon               text,
    show_link          boolean  default true  not null,
    rank               smallint default 1     not null,
    parent_id          bigint,
    created_time       timestamp(6),
    updated_time       timestamp(6),
    updated_by_user_id bigint,
    created_by_user_id bigint,
    menu_type          smallint               not null,
    component          text,
    extra_icon         text,
    enter_transition   text,
    leave_transition   text,
    active_path        text,
    frame_src          text,
    frame_loading      boolean,
    keep_alive         boolean  default false not null,
    hidden_tag         boolean  default false not null,
    fixed_tag          boolean  default false not null
);

comment on table public.auth_page_router is '页面路由表';

comment on column public.auth_page_router.id is '主键ID';

comment on column public.auth_page_router.title is '菜单名称';

comment on column public.auth_page_router.path is '角色代码';

comment on column public.auth_page_router.name is '路由名称（必须保持唯一）';

comment on column public.auth_page_router.redirect is '路由重定向（默认跳转地址）';

comment on column public.auth_page_router.icon is '菜单图标';

comment on column public.auth_page_router.show_link is '是否在菜单中显示';

comment on column public.auth_page_router.rank is ' 菜单排序，值越高排的越后（只针对顶级路由）';

comment on column public.auth_page_router.created_time is '创建时间';

comment on column public.auth_page_router.updated_time is '更新时间';

comment on column public.auth_page_router.updated_by_user_id is '更新者ID';

comment on column public.auth_page_router.created_by_user_id is '创建者ID';

comment on column public.auth_page_router.menu_type is '菜单类型。0代表菜单、1代表iframe、2代表外链、3代表按钮';

comment on column public.auth_page_router.component is '组件路径。传component组件路径，那么path可以随便写，如果不传，component组件路径会跟path保持一致';

comment on column public.auth_page_router.extra_icon is '菜单项右侧的图标';

comment on column public.auth_page_router.enter_transition is '页面加载时的进场动画';

comment on column public.auth_page_router.leave_transition is '页面卸载时的离场动画';

comment on column public.auth_page_router.active_path is '将某个菜单激活，主要用于通过query或params传参的路由，当它们通过配置showLink: false后不在菜单中显示，就不会有任何菜单高亮，而通过设置activePath指定激活菜单即可获得高亮，activePath为指定激活菜单的path';

comment on column public.auth_page_router.frame_src is '需要内嵌的iframe链接地址';

comment on column public.auth_page_router.frame_loading is '内嵌的iframe页面是否开启首次加载动画';

comment on column public.auth_page_router.keep_alive is '是否缓存该路由页面，开启后会保存该页面的整体状态，刷新后会清空状态';

comment on column public.auth_page_router.hidden_tag is '当前菜单名称或自定义信息禁止添加到标签页';

comment on column public.auth_page_router.fixed_tag is '当前菜单名称是否固定显示在标签页且不可关闭';

alter table public.auth_page_router
    owner to postgres;


create table if not exists public.auth_permission
(
    id                 bigint not null
        primary key,
    title              text   not null,
    code               text   not null
        unique,
    remark             text,
    created_time       timestamp(6),
    updated_time       timestamp(6),
    updated_by_user_id bigint,
    created_by_user_id bigint,
    parent_id          bigint
        constraint auth_permission_auth_permission_id_fk
            references public.auth_permission
);

comment on table public.auth_permission is '权限表';

comment on column public.auth_permission.id is '主键ID';

comment on column public.auth_permission.title is '标题';

comment on column public.auth_permission.code is '权限代码';

comment on column public.auth_permission.remark is '备注';

comment on column public.auth_permission.created_time is '创建时间';

comment on column public.auth_permission.updated_time is '更新时间';

comment on column public.auth_permission.updated_by_user_id is '更新者ID';

comment on column public.auth_permission.created_by_user_id is '创建者ID';

alter table public.auth_permission
    owner to postgres;

create table if not exists public.link_router_permission
(
    router_id     bigint not null
        constraint link_router_permission_auth_page_router_id_fk
            references public.auth_page_router
            on delete restrict,
    permission_id bigint not null
        constraint link_router_permission_auth_permission_id_fk
            references public.auth_permission
            on delete restrict,
    constraint link_router_endpoint_pkey
        primary key (router_id, permission_id)
);

comment on table public.link_router_permission is '前端页面路由-权限关联表';

comment on column public.link_router_permission.router_id is '前端页面路由表ID';

comment on column public.link_router_permission.permission_id is '权限表ID';

alter table public.link_router_permission
    owner to postgres;

create table if not exists public.link_role_permission
(
    role_id       bigint not null
        constraint link_role_endpoint_role_id_fkey
            references public.auth_role
            on delete cascade,
    permission_id bigint not null
        constraint link_role_endpoint_permission_id_fkey
            references public.auth_permission
            on delete cascade,
    constraint link_role_permission2_pkey
        primary key (role_id, permission_id)
);

comment on table public.link_role_permission is '角色-权限关联表';

comment on column public.link_role_permission.role_id is '角色表ID';

comment on column public.link_role_permission.permission_id is '权限表ID';

alter table public.link_role_permission
    owner to postgres;

