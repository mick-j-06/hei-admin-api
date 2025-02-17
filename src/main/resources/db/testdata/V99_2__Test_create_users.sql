insert into "user"
(id, first_name, last_name, email, ref, status, sex, birth_date, entrance_datetime, phone, address,
 "role", group_id, key_image_in_bucket)
values ('student1_id', 'Ryan', 'Andria', 'test+ryan@hei.school', 'STD21001', 'ENABLED', 'M',
        '2000-01-01',
        '2021-11-08T08:25:24.00Z', '0322411123', 'Adr 1', 'STUDENT', 'group1_id', 'toky1.jpg');

insert into "user"
(id, first_name, last_name, email, ref, status, sex, birth_date, entrance_datetime, phone, address,
 "role", group_id)
values ('student2_id', 'Two', 'Student', 'test+student2@hei.school', 'STD21002', 'ENABLED', 'F',
        '2000-01-02',
        '2021-11-09T08:26:24.00Z', '0322411124', 'Adr 2', 'STUDENT', 'group1_id'),
       ('student3_id', 'Three', 'Student', 'test+student3@hei.school', 'STD21003', 'ENABLED', 'F',
        '2000-01-02',
        '2021-11-09T08:26:24.00Z', '0322411124', 'Adr 2', 'STUDENT', 'group1_id');

insert into "user"
(id, first_name, last_name, email, ref, status, sex, birth_date, entrance_datetime, phone, address,
 "role")
values ('teacher1_id', 'One', 'Teacher', 'test+teacher1@hei.school', 'TCR21001', 'ENABLED', 'F',
        '1990-01-01',
        '2021-10-08T08:27:24.00Z', '0322411125', 'Adr 3', 'TEACHER'),
       ('teacher2_id', 'Two', 'Teacher', 'test+teacher2@hei.school', 'TCR21002', 'ENABLED', 'M',
        '1990-01-02',
        '2021-10-09T08:28:24.00Z', '0322411126', 'Adr 4', 'TEACHER'),
       ('teacher3_id', 'Three', 'Teach', 'test+teacher3@hei.school', 'TCR21003', 'ENABLED', 'M',
        '1990-01-02',
        '2021-10-09T08:28:24.00Z', '0322411126', 'Adr 4', 'TEACHER'),
       ('manager1_id', 'One', 'Manager', 'test+manager1@hei.school', 'MGR21001', 'ENABLED', 'M',
        '1890-01-01',
        '2021-09-08T08:25:29.00Z', '0322411127', 'Adr 5', 'MANAGER');

