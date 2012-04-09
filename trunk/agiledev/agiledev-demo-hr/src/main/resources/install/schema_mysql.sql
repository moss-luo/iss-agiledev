CREATE TABLE  `agiledev`.`T_DEPARTMENT` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(200) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `agiledev`.`T_EMPLOYEE` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(200) NOT NULL,
  `depId` int(11) NOT NULL,
  `levelId` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `agiledev`.`T_LEVEL` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(200) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `agiledev`.`T_PERMISION` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `permisionName` char(200) NOT NULL,
  `url` char(255) CHARACTER SET latin1 NOT NULL,
  `pid` int(11) NOT NULL,
  `hasChild` int(11),
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `agiledev`.`T_ROLE` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` char(200) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `agiledev`.`T_ROLE_PERMISION` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL,
  `permisionId` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `agiledev`.`T_USER` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` char(200) NOT NULL,
  `password` char(200) NOT NULL,
  `photo` char(200) DEFAULT NULL,
  `roleid` char(200) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(0,'root','','-1',1);

insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(1,'system','',0,1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(2,'basic','',0,1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(3,'chart','',0,1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(4,'report','',0,1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(5,'data','',0,1);

insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(6,'users','system/user.html',1,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(7,'roles','system/role.html',1,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(8,'modules','system/module.html',1,0);


insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(9,'departments','basedata/department.html',2,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(10,'employee','basedata/employee.html',2,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(11,'train type','basedata/train.html',2,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(12,'train','basedata/programs.html',2,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(13,'level','basedata/level.html',2,0);

insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(14,'department train chart','',3,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(15,'level train chart','',3,0);

insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(16,'department train report','',4,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(17,'level train report','',4,0);

insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(18,'train data','',5,0);


