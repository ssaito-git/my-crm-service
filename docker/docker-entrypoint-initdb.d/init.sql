create database crm_service encoding 'UTF8';

\c crm_service;

create schema crm_service_schema;

create user crm_service_user password 'secret';

grant usage on schema crm_service_schema to crm_service_user;

alter default privileges in schema crm_service_schema grant all on tables to crm_service_user;

alter user crm_service_user set search_path to crm_service_schema;
