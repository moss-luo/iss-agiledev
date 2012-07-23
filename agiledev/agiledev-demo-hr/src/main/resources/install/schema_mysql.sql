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


create table `agiledev`.`T_PERMISION` (
	`uid` double ,
	`permisionName` varchar (600),
	`url` varchar (765),
	`pid` double ,
	`hasChild` double ,
	`pname` varchar (600),
	`version` varchar (600)
); 

insert into `t_user` (uid,userid,password,photo) values('1','admin','admin','');

insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('1','系统管理','','0','1',NULL,'easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('2','分析数据','','0','1',NULL,'easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('3','数据维护','','0','1',NULL,'easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('4','基础数据','','0','1',NULL,'easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('5','统计报表','','0','1',NULL,'easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('6','用户管理','system/user.html','1','0','系统管理','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('7','模块管理','system/module.html','1','0','系统管理','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('8','角色管理','system/role.html','1','0','系统管理','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('9','部门培训分析','','2','0','分析数据','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('10','职级培训分析','','2','0','分析数据','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('11','培训数据','','3','0','数据维护','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('12','部门管理','basedata/department.html','4','0','基础数据','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('13','培训类别','basedata/train.html','4','0','基础数据','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('14','职员管理','basedata/employee.html','4','0','基础数据','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('15','培训项目','basedata/programs.html','4','0','基础数据','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('16','职级管理','basedata/level.html','4','0','基础数据','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('17','部门培训统计','','5','0','统计报表','easyui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('18','职级培训统计','','5','0','统计报表','easyui');


insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('19','系统管理','','-1','1',NULL,'ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('20','分析数据','','-1','1',NULL,'ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('21','数据维护','','-1','1',NULL,'ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('22','基础数据','','-1','1',NULL,'ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('23','统计报表','','-1','1',NULL,'ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('24','用户管理','system/user-ligerui.html','19','0','系统管理','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('25','模块管理','system/module-ligerui.html','19','0','系统管理','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('26','角色管理','system/role-ligerui.html','19','0','系统管理','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('27','部门培训分析','','20','0','分析数据','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('28','职级培训分析','','20','0','分析数据','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('29','培训数据','','21','0','数据维护','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('30','部门管理','basedata/department-ligerui.html','22','0','基础数据','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('31','培训类别','basedata/train-ligerui.html','22','0','基础数据','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('32','职员管理','basedata/employee-ligerui.html','22','0','基础数据','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('33','培训项目','basedata/programs-ligerui.html','22','0','基础数据','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('34','职级管理','basedata/level-ligerui.html','22','0','基础数据','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('35','部门培训统计','','23','0','统计报表','ligerui');
insert into `T_PERMISION` (`uid`, `permisionName`, `url`, `pid`, `hasChild`, `pname`, `version`) values('36','职级培训统计','','23','0','统计报表','ligerui');


/*
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(1,'root','','-1',1);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(2,'system','',1,1);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(3,'basic','',1,1);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(4,'chart','',1,1);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(5,'report','',1,1);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(6,'data','',1,1);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(7,'users','system/user.html',2,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(8,'roles','system/role.html',2,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(9,'modules','system/module.html',2,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(10,'departments','basedata/department.html',3,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(11,'employee','basedata/employee.html',3,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(12,'train type','basedata/train.html',3,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(13,'train','basedata/programs.html',3,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(14,'level','basedata/level.html',3,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(15,'department train chart','',4,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(16,'level train chart','',4,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(17,'department train report','',5,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(18,'level train report','',5,0);
insert into T_PERMISION (uid,permisionname,url,parentId,hasChild) values(19,'train data','',6,0);*/
