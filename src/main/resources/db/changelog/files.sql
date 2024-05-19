create table files(
    filename varchar(255) not null,
    date timestamp(6) not null,
    file_content oid not null,
    size bigint not null,
    user_username varchar(255),
    primary key (filename)
)