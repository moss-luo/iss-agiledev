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
  `url` char(255),
  `pid` int(11) NOT NULL,
  `pname` int(11),
  `hasChild` int(11),
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

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



insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(1,'root','','-1',1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(2,'system','',1,1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(3,'basic','',1,1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(4,'chart','',1,1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(5,'report','',1,1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(6,'data','',1,1);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(7,'users','system/user.html',2,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(8,'roles','system/role.html',2,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(9,'modules','system/module.html',2,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(10,'departments','basedata/department.html',3,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(11,'employee','basedata/employee.html',3,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(12,'train type','basedata/train.html',3,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(13,'train','basedata/programs.html',3,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(14,'level','basedata/level.html',3,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(15,'department train chart','',4,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(16,'level train chart','',4,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(17,'department train report','',5,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(18,'level train report','',5,0);
insert into T_PERMISION (uid,permisionname,url,pid,hasChild) values(19,'train data','',6,0);