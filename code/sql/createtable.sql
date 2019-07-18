
drop table if exists user;
create table user(
	uid int,
    username varchar(16),
    password varchar(16),
    nickname varchar(16) default "user???",
    lst date not null default "0000-01-01",
    rgt date not null default "0000-01-01",
    male boolean default false,
    phone varchar(13) default "",
    email varchar(30) default "",
    activation bool default false,
    adm bool default false,
    primary key(uid),
    forbidden bool not null default false
);
/*
drop table if exists book;
create table book(
	bid int,
    uid int,
    bookname varchar(40),
    kind varchar(20) default 0,
    lst date not null default "0000-01-01",
    shared bool,
    playtime int,
    primary key (bid),
    forbidden bool not null default false
);*/
/*
drop table if exists collector;
create table collector(
	uid int,
    bid int,
    lst date not null default "0000-01-01",
    primary key (uid,bid)
);*/
/*
drop table if exists history;
create table history(
	uid int,
    bid int,
    lst date not null default "0000-01-01",
    primary key (uid,bid)
)
*/
/*
drop table if exists comment;
create table comment(
	uid int,
    bid int,
    cid int,
    lst date not null default "0000-01-01",
    primary key(cid)
)*/
