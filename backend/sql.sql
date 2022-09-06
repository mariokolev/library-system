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