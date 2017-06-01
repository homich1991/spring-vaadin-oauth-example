INSERT INTO users (id, created_date, last_modified_date, enabled, name, password) VALUES (1, null, null, true, 'admin', '$2a$10$Vkl9e8ZnkAxMIZvQle2aP.QVPHFMjl1y4VPpqZvy6eIhmgHiPW8CS');
INSERT INTO users (id, created_date, last_modified_date, enabled, name, password) VALUES (2, null, null, true, 'user', '$2a$10$Vkl9e8ZnkAxMIZvQle2aP.QVPHFMjl1y4VPpqZvy6eIhmgHiPW8CS');

INSERT INTO public.user_role (user_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.user_role (user_id, role) VALUES (2, 'ROLE_USER');