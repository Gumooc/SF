/*
drop table if exists user;
create table user(
	uid int,
    username varchar(16),
    password varchar(16),
    lst date,
    rgt date,
    male boolean,
    phone varchar(13),
    email varchar(30),
    avtivation bool,
    adm bool,
    primary key(uid)
);*/
/*
drop table if exists book;
create table book(
	bid int,
    uid int,
    bookname varchar(40),
    kind varchar(20),
    lst Date,
    shared bool,
    playtime int,
    primary key (bid)
);*/
/*
drop table if exists collector;
create table collector(
	uid int,
    bid int,
    lst date,
    primary key (uid,bid)
);*/
/*
drop table if exists history;
create table history(
	uid int,
    bid int,
    lst date,
    primary key (uid,bid)
)
*/
/*
drop table if exists comment;
create table comment(
	uid int,
    bid int,
    cid int,
    lst date,
    primary key(cid)
)*/
