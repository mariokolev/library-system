--drop table users;
--drop table roles_permissions;
--drop table roles;
--drop table persmissions;

create table roles(
	id serial not null primary key,
	role_name varchar(255) not null,
	inherit_role_id integer null,
	constraint ux_roles_role_name unique(role_name),
	constraint fk_roles_inherit_role_id foreign key (inherit_role_id) references roles(id)
);
create index ix_roles_inherit_role_id on roles(inherit_role_id);

create table permissions(
	id serial not null primary key,
	permission_name varchar(255) not null,
	constraint ux_permissions_permision_name unique(permission_name)
);

create table roles_permissions(
	role_id int8 not null,
	permission_id int8 not null,
	constraint fk_roles_permissions_role_id foreign key(role_id) references roles(id),
	constraint fk_roles_permissions_permission_id foreign key(permission_id) references permissions(id)
);
create index ix_roles_permissions_role_id on roles_permissions(role_id);
create index ix_roles_permissions_permission_id on roles_permissions(permission_id);

create table users(
	id bigserial not null primary key,
	username varchar(255) not null,
	password varchar(255) not null,
    first_name varchar(255),
    last_name varchar(255),
	role_id int2 not null,
	active bool default true,
	date_added timestamp default now(),
	date_modified timestamp default now(),
	constraint ux_users_username unique(username),
	constraint fk_users_role_id foreign key(role_id) references roles(id)
);
create index ix_users_role_id on users(role_id);

create table libraries(
	id bigserial not null primary key,
	"name" varchar(255) not null,
	constraint ux_libraries_name unique("name")
);

create table users_libraries(
	user_id int not null,
	library_id int not null,
	date_added timestamp default now(),
	constraint fk_users_libraries_user_id foreign key(user_id) references users(id),
	constraint fk_users_libraries_library_id foreign key(library_id) references libraries(id)
);
create index ix_users_libraries_user_id on users_libraries(user_id);
create index ix_users_libraries_library_id on users_libraries(library_id);

create table reading_rooms(
	id bigserial not null primary key,
	library_id int not null,
	"name" varchar(255) not null,
	constraint ux_reading_rooms_name unique("name"),
	constraint fk_reading_rooms_library_id foreign key(library_id) references libraries(id)
);
create index fk_reading_rooms_library_id on reading_rooms(library_id);

create table genres(
	id bigserial not null primary key,
	"name" varchar(255) not null,
	constraint ux_genres_name unique("name")
);

create table books(
	id bigserial not null primary key,
	title varchar(255) not null,
	genre_id int not null,
	author varchar(255),
	status varchar(255) not null,
	reading_room_id int not null,
	date_added timestamp default now(),
	constraint fk_books_genre_id foreign key(genre_id) references genres(id),
	constraint fk_books_reading_room_id foreign key(reading_room_id) references reading_rooms(id)
);
create index ix_books_genre_id on books(genre_id);
create index ix_books_reading_room_id on books(reading_room_id);

create table borrows(
	id bigserial not null primary key,
	book_id int not null,
	reader_id int not null,
	operator_id int not null,
	date_issued timestamp,
	date_due_return timestamp,
	date_returned timestamp,
	date_added timestamp default now(),
	date_modified timestamp default now(),
	constraint fk_borrows_book_id foreign key(book_id) references books(id),
	constraint fk_borrows_reader_id foreign key(reader_id) references users(id),
	constraint fk_borrows_operator_id foreign key(operator_id) references users(id)
);
create index ix_borrows_book_id on borrows(book_id);
create index ix_borrows_reader_id on borrows(reader_id);
create index ix_borrows_operator on borrows(operator_id);

alter table public.books add column book_condition varchar(255) null;

-- roles
INSERT INTO public.roles
(role_name, inherit_role_id)
VALUES('reader', null),
('operator', 1),
('admin', 2);

-- permissions
INSERT INTO public.permissions
(permission_name)
VALUES('MANAGE_BOOKS'),
('VIEW_BOOKS'),
('MANAGE_USERS'),
('MANAGE_READERS'),
('BORROW_BOOK'),
('VIEW_BORROW_BOOKS'),
('VIEW_ALL_BORROW_BOOKS'),
('ADMIN_VIEW_ALL_BORROW_BOOKS');


-- admin
INSERT INTO public.users
(username, "password", first_name, last_name, role_id, active, date_added, date_modified)
VALUES('user1', '$2a$10$m3hJHvGrXGlvToBrXRKxQ.Nz/p3JxEhnBpv3Pu61CljJHY17wyuIq', 'admin', 'admin', 3, true, now(), now());

-- library
INSERT INTO public.libraries
("name")
VALUES('Library one');

-- reading rooms
INSERT INTO public.reading_rooms
(library_id, "name")
VALUES(1, 'room one'),
(1, 'room two'),
(1, 'room three'),
(1, 'room four'),
(1, 'room five');



INSERT INTO public.genres
("name")
VALUES
('роман'),
('романтични'),
('хумор'),
('бизнес'),
('наука'),
('изкуство'),
('фентъзи')
;