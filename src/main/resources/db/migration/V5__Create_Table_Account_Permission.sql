CREATE TABLE IF NOT EXISTS `account_permission` (
  `id_account` bigint(20) NOT NULL,
  `id_permission` bigint(20) NOT NULL,
  PRIMARY KEY (`id_account`,`id_permission`),
  KEY `fk_account_permission_permission` (`id_permission`),
  CONSTRAINT `fk_account_permission` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`),
  CONSTRAINT `fk_account_permission_permission` FOREIGN KEY (`id_permission`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB;