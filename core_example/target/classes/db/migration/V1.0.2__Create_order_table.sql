CREATE TABLE `OrderT` (
  `orderId` varchar(36) NOT NULL,
  `no` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `callNo` int(3) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `updatedAt` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '创建时间',
  `founderId` varchar(36) NOT NULL DEFAULT '' COMMENT '创建人ID',
  `founderName` varchar(30) NOT NULL DEFAULT '' COMMENT '创建人姓名',
  `modifierId` varchar(36) NOT NULL DEFAULT '' COMMENT '修改人ID',
  `modifierName` varchar(30) NOT NULL DEFAULT '' COMMENT '修改人姓名',
  `createDateTime` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '创建时间',
  `modifyDateTime` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '修改时间',
  `deleteFlag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1是',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
