create table students (
    id identity, /* postgres: serial, msqsql: auto_increment */
    last_name varchar(255),
    first_name varchar(255),
    gender varchar(30),
    student_number int,
    class varchar(5),
    primary key (id),
    unique (class, student_number)
);