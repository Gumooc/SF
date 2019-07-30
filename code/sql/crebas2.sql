/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/7/3 10:15:12                            */
/*==============================================================*/
drop database if exists audiobook;
create database audiobook;
use audiobook;

drop table if exists ban;

drop table if exists book;

drop table if exists bookClass;

drop table if exists classification;

drop table if exists collectionitems;

drop table if exists collector;

drop table if exists comment;

drop table if exists commentLog;

drop table if exists history;

drop table if exists recommend;

drop table if exists subscribe;

drop table if exists up;

drop table if exists user;

/*==============================================================*/
/* Table: ban                                                   */
/*==============================================================*/
create table ban
(
   user_id              int not null,
   admin_id             int not null,
   time                 date,
   reason               varchar(255),
   primary key (user_id, admin_id)
);

/*==============================================================*/
/* Table: book                                                  */
/*==============================================================*/
create table book
(
   bookname             varchar(50),
   shared               bool,
   region               varchar(20),
   time                 date,
   book_id              int not null,
   user_id              int,
   primary key (book_id)
);

/*==============================================================*/
/* Table: bookClass                                             */
/*==============================================================*/
create table bookClass
(
   label              	varchar(20) not null,
   primary key (label)
);

/*==============================================================*/
/* Table: classification                                        */
/*==============================================================*/
create table classification
(
   book_id              int not null,
   label              varchar(20) not null,
   primary key (book_id, label)
);

/*==============================================================*/
/* Table: collectionitems                                       */
/*==============================================================*/
create table collectionitems
(
   Fid                  int not null,
   book_id              int not null,
   primary key (Fid, book_id)
);

/*==============================================================*/
/* Table: collector                                             */
/*==============================================================*/
create table collector
(
   Fid                  int not null,
   user_id              int,
   locked               bool,
   primary key (Fid)
);

/*==============================================================*/
/* Table: comment                                               */
/*==============================================================*/
create table comment
(
   Cid                  int not null,
   content              varchar(255),
   time                 date,
   primary key (Cid)
);

/*==============================================================*/
/* Table: commentLog                                            */
/*==============================================================*/
create table commentLog
(
   Cid                  int not null,
   user_id              int not null,
   book_id              int not null,
   time                 date,
   primary key (Cid, user_id, book_id)
);

/*==============================================================*/
/* Table: history                                               */
/*==============================================================*/
create table history
(
   Hid                  int not null,
   user_id              int,
   book_id                  int,
   time                 date,
   primary key (Hid)
);

/*==============================================================*/
/* Table: recommend                                             */
/*==============================================================*/
create table recommend
(
   user_id              int not null,
   book_id              int not null,
   primary key (user_id, book_id)
);

/*==============================================================*/
/* Table: subscribe                                             */
/*==============================================================*/
create table subscribe
(
   user_do_id           int not null,
   user_done_id         int not null,
   primary key (user_do_id, user_done_id)
);

/*==============================================================*/
/* Table: up                                                    */
/*==============================================================*/
create table up
(
   user_do_id           int not null,
   use_done_id          int not null,
   primary key (user_do_id, use_done_id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   password             varchar(12),
   email                varchar(30),
   phone                varchar(11),
   gender               bool,
   isadmin              bool,
   banned               bool,
   nickname             varchar(20),
   registertime         date,
   state                bool,
   user_id              int not null,
   primary key (user_id)
);

alter table ban add constraint FK_ban foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table ban add constraint FK_ban2 foreign key (admin_id)
      references user (user_id) on delete restrict on update restrict;

alter table book add constraint FK_create foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table classification add constraint FK_classification foreign key (book_id)
      references book (book_id) on delete restrict on update restrict;

alter table classification add constraint FK_classification2 foreign key (label)
      references bookClass (label) on delete restrict on update restrict;

alter table collectionitems add constraint FK_collectionitems foreign key (Fid)
      references collector (Fid) on delete restrict on update restrict;

alter table collectionitems add constraint FK_collectionitems2 foreign key (book_id)
      references book (book_id) on delete restrict on update restrict;

alter table collector add constraint FK_collection foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table commentLog add constraint FK_commentLog foreign key (Cid)
      references comment (Cid) on delete restrict on update restrict;

alter table commentLog add constraint FK_commentLog2 foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table commentLog add constraint FK_commentLog3 foreign key (book_id)
      references book (book_id) on delete restrict on update restrict;

alter table history add constraint FK_readhistory foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table recommend add constraint FK_recommend foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table recommend add constraint FK_recommend2 foreign key (book_id)
      references book (book_id) on delete restrict on update restrict;

alter table subscribe add constraint FK_subscribe foreign key (user_do_id)
      references user (user_id) on delete restrict on update restrict;

alter table subscribe add constraint FK_subscribe2 foreign key (user_done_id)
      references user (user_id) on delete restrict on update restrict;

alter table up add constraint FK_up foreign key (user_do_id)
      references user (user_id) on delete restrict on update restrict;

alter table up add constraint FK_up2 foreign key (use_done_id)
      references user (user_id) on delete restrict on update restrict;

