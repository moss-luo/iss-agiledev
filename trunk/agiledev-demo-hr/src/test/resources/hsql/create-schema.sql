CREATE TABLE  `dbsns`.`T_DEPARTMENT` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(200) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE  `dbsns`.`T_EMPLOYEE` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(200) NOT NULL,
  `depId` int(11) NOT NULL,
  `levelId` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE  `dbsns`.`T_LEVEL` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(200) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8

CREATE TABLE  `dbsns`.`T_PERMISION` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `permisionName` char(200) NOT NULL,
  `url` char(255) CHARACTER SET latin1 DEFAULT NULL,
  `pid` int(11) NOT NULL,
  `hasChild` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8

CREATE TABLE  `dbsns`.`T_ROLE` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` char(200) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8

CREATE TABLE  `dbsns`.`T_ROLE_PERMISION` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL,
  `permisionId` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8

CREATE TABLE  `dbsns`.`T_USER` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` char(200) NOT NULL,
  `password` char(200) NOT NULL,
  `photo` char(200) DEFAULT NULL,
  `roleid` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8