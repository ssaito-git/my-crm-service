-- tenants
create table crm_service_schema.tenants
(
    id   UUID primary key,
    name varchar(100) not null
);

create policy tenant_isolation_policy on crm_service_schema.tenants using (id = (current_setting('app.current_tenant'))::uuid);
alter table crm_service_schema.tenants ENABLE row level security;

-- token_introspection_client_configs
create table crm_service_schema.token_introspection_client_configs
(
    tenant_id                   UUID         not null,
    resource_server_domain_name varchar(100) not null,
    client_id                   varchar(100) not null,
    client_secret               varchar(100) not null,
    introspection_endpoint      varchar(200) not null,
    issuer_type                 varchar(50)  not null,
    primary key (tenant_id, resource_server_domain_name)
);

-- system_users
create table crm_service_schema.system_users
(
    tenant_id UUID         not null,
    id        UUID         not null,
    name      varchar(100) not null,
    email     varchar(256) not null,
    password  varchar(100) not null,
    role_id   UUID         not null,
    primary key (tenant_id, id),
    unique (tenant_id, email)
);

create policy tenant_isolation_policy on crm_service_schema.system_users using (tenant_id = (current_setting('app.current_tenant'))::uuid);
alter table crm_service_schema.system_users ENABLE row level security;

-- roles
create table crm_service_schema.roles
(
    tenant_id UUID         not null,
    id        UUID         not null,
    name      varchar(100) not null,
    primary key (tenant_id, id)
);

create policy tenant_isolation_policy on crm_service_schema.roles using (tenant_id = (current_setting('app.current_tenant'))::uuid);
alter table crm_service_schema.roles ENABLE row level security;

-- permissions
create table crm_service_schema.permissions
(
    tenant_id  UUID         not null,
    role_id    UUID         not null,
    resource   varchar(100) not null,
    action     varchar(100) not null,
    is_granted boolean      not null,
    primary key (tenant_id, role_id, resource, action)
);

create policy tenant_isolation_policy on crm_service_schema.permissions using (tenant_id = (current_setting('app.current_tenant'))::uuid);
alter table crm_service_schema.permissions ENABLE row level security;

-- products
create table crm_service_schema.products
(
    tenant_id   UUID         not null,
    id          UUID         not null,
    sku         varchar(50)  not null,
    name        varchar(100) not null,
    description varchar(500) not null,
    active      boolean      not null,
    primary key (tenant_id, id),
    unique (tenant_id, sku)
);

create policy tenant_isolation_policy on crm_service_schema.products using (tenant_id = (current_setting('app.current_tenant'))::uuid);
alter table crm_service_schema.products ENABLE row level security;
