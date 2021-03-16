insert into users(username, password, enabled)
values ('user', 'blah', true);

insert into users(username, password, enabled)
values ('admin', 'boom', true);

insert into authorities(username, authority)
values ('user', 'ROLE_USER');

insert into authorities(username, authority)
values ('user', 'ROLE_ADMIN');
