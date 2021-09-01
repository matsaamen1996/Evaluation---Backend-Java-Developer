create table course
(
    id bigint auto_increment
        primary key,
    created_by varchar(255) null,
    created_date datetime(6) null,
    deleted_by varchar(255) null,
    deleted_date datetime(6) null,
    last_modified_by varchar(255) null,
    last_modified_date datetime(6) null,
    state varchar(255) not null,
    author varchar(255) null,
    date datetime(6) null,
    name varchar(255) null,
    description varchar(255) null,
    duration varchar(255) null,
    language varchar(255) null,
    rating varchar(255) null
);

create table payment
(
    id bigint auto_increment
        primary key,
    created_by varchar(255) null,
    created_date datetime(6) null,
    deleted_by varchar(255) null,
    deleted_date datetime(6) null,
    last_modified_by varchar(255) null,
    last_modified_date datetime(6) null,
    state varchar(255) not null,
    amount varchar(255) null,
    description varchar(255) null,
    total_amount varchar(255) null,
    type varchar(255) null,
    course_id bigint null,
    constraint FK10va7eoiu5bmbxcfoakxx9omn
        foreign key (course_id) references course (id)
);

create table role
(
    id bigint auto_increment
        primary key,
    created_by varchar(255) null,
    created_date datetime(6) null,
    deleted_by varchar(255) null,
    deleted_date datetime(6) null,
    last_modified_by varchar(255) null,
    last_modified_date datetime(6) null,
    state varchar(255) not null,
    description varchar(255) null,
    role varchar(255) null
);

create table user
(
    id bigint auto_increment
        primary key,
    created_by varchar(255) null,
    created_date datetime(6) null,
    deleted_by varchar(255) null,
    deleted_date datetime(6) null,
    last_modified_by varchar(255) null,
    last_modified_date datetime(6) null,
    state varchar(255) not null,
    email varchar(100) null,
    last_name varchar(100) null,
    name varchar(100) null,
    password varchar(100) null,
    telephone varchar(10) null,
    username varchar(50) null,
    address varchar(255) null,
    birthdate datetime(6) null,
    completed bit null,
    country varchar(255) null,
    education varchar(255) null,
    gender varchar(255) null,
    language varchar(255) null
);

create table enrollments
(
    id bigint auto_increment
        primary key,
    created_by varchar(255) null,
    created_date datetime(6) null,
    deleted_by varchar(255) null,
    deleted_date datetime(6) null,
    last_modified_by varchar(255) null,
    last_modified_date datetime(6) null,
    state varchar(255) not null,
    course_id bigint null,
    payment_id bigint null,
    user_id bigint null,
    constraint FK2fgjdjsu4e4auhdp8uu7yde2g
        foreign key (payment_id) references payment (id),
    constraint FKhcgx82wq6ehyxq2kfo1wlr5le
        foreign key (user_id) references user (id),
    constraint FKi1xf35chebesq0hfodmwhw1nm
        foreign key (course_id) references course (id)
);

create table user_rol
(
    user_id bigint not null,
    role_id bigint not null,
    constraint FKje05j8n9jg14gbosrflgjcjk
        foreign key (role_id) references role (id),
    constraint FKkijwolbkui74em8ip1i6vniu4
        foreign key (user_id) references user (id)
);
