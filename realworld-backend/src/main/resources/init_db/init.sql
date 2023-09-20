create table user
(
    create_at     datetime(6)  not null,
    modified_at   datetime(6)  not null,
    user_id       bigint       not null auto_increment,
    bio           varchar(255),
    image         varchar(255),
    user_email    varchar(255) not null,
    user_password varchar(255) not null,
    user_status   enum ('ACTIVE','DELETED'),
    username      varchar(255) not null,

    primary key (user_id)
) engine = InnoDB;

alter table user
    add constraint UK_j09k2v8lxofv2vecxu2hde9so unique (user_email);

alter table user
    add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);