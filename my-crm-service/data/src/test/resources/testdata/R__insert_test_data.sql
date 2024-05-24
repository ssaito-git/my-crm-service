-- tenants
insert into crm_service_schema.tenants(id, name)
values ('00000001-0000-0000-0000-000000000001', 'foo'),
       ('00000001-0000-0000-0000-000000000002', 'bar'),
       ('00000001-0000-0000-0000-000000000003', 'baz');

-- token_introspection_client_configs
insert into crm_service_schema.token_introspection_client_configs(tenant_id, resource_server_domain_name, client_id,
                                                                  client_secret,
                                                                  introspection_endpoint, issuer_type)
values ('00000001-0000-0000-0000-000000000001', 'localhost', '00000000-0000-0000-0000-000000000002', 'secret',
        'http://localhost:8091/introspection', 'SERVICE'),
       ('00000001-0000-0000-0000-000000000001', '127.0.0.1', '00000000-0000-0000-0000-000000000002', 'secret',
        'http://127.0.0.1:8091/introspection', 'SYSTEM');

-- roles
insert into crm_service_schema.roles(tenant_id, id, name)
values ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'admin'),
       ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000002', 'member');

-- permissions
insert into crm_service_schema.permissions(tenant_id, role_id, resource, action, is_granted)
values ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'tenant', 'read', true),
       ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'tenant', 'write', true),
       ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'user', 'read', true),
       ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'user', 'write', true),
       ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'system_user', 'read', true),
       ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'system_user', 'write', true);

-- system_users
insert into crm_service_schema.system_users(tenant_id, id, name, email, password, role_id)
values ('00000001-0000-0000-0000-000000000001', '00000002-0000-0000-0000-000000000001', 'alice', 'alice@example.com',
        '{bcrypt}$2a$10$53ybG3v7qINEI6z8L6pioeXqz1iV1yGJW57pWYPjJ9zhqZgsYvwCu', '00000000-0000-0000-0000-000000000001'),
       ('00000001-0000-0000-0000-000000000001', '00000002-0000-0000-0000-000000000002', 'bob', 'bob@example.com',
        '{bcrypt}$2a$10$53ybG3v7qINEI6z8L6pioeXqz1iV1yGJW57pWYPjJ9zhqZgsYvwCu', '00000000-0000-0000-0000-000000000002'),
       ('00000001-0000-0000-0000-000000000001', '00000002-0000-0000-0000-000000000003', 'carol', 'carol@example.com',
        '{bcrypt}$2a$10$53ybG3v7qINEI6z8L6pioeXqz1iV1yGJW57pWYPjJ9zhqZgsYvwCu', '00000000-0000-0000-0000-000000000002');

-- products
insert into crm_service_schema.products(tenant_id, id, sku, name, description, active, created, updated)
values ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', 'foo', 'foo product',
        'foo product', true, '2024-01-01', '2024-01-01'),
       ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000002', 'bar', 'bar product',
        'bar product', true, '2024-01-01', '2024-01-01'),
       ('00000001-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000003', 'baz', 'baz product',
        'baz product', true, '2024-01-01', '2024-01-01');
