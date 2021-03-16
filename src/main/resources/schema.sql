create table InstanceConfig
(
    id UUID not null
        constraint InstanceConfig_pk
            PRIMARY KEY,
    tenantId varchar(50) not null,
    entityId varchar(50),
    name varchar(80) not null,
    createdAt timestamptz,
    updatedAt timestamptz
);

create table Schedule
(
    id UUID not null
        constraint Schedule_pk
            PRIMARY KEY,
    instanceConfigId UUID not null,
    category varchar(255) not null,
    cronString varchar(25) not null,
    createdAt timestamptz,
    updatedAt timestamptz
);

create table IORealm
(
    id UUID not null
        constraint IORealm_pk
            PRIMARY KEY,
    instanceConfigId UUID not null,
    category varchar(255) not null,
    name varchar(80) not null,
    host varchar(30) not null,
    port smallint not null,
    username varchar(255) not null,
    secret text,
    sshKey text,
    sourcePath text,
    inclusion text,
    exclusion text,
    createdAt timestamptz,
    updatedAt timestamptz
);

create table Mapping
(
    id UUID not null
        constraint Mapping_pk
            primary key,
    instanceConfigId UUID not null,
    category varchar(255) not null,
    fromFieldName varchar(255) not null,
    fromFieldDataType varchar(50) not null,
    usageFieldName varchar(255) not null,
    objectName varchar(255) not null,
    createdAt timestamptz,
    updatedAt timestamptz
);

create table users
(
    username varchar(255) not null
        constraint users_pk
            primary key,
    password text not null,
    enabled boolean default true not null
);

create table authorities
(
    username varchar(255) not null
        constraint authorities_users_username_fk
            references users,
    authority varchar(255) not null
);
