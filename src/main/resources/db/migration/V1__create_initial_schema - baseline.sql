SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE `claim` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `claim_number` varchar(255) DEFAULT NULL,
  `cliente_claim` varchar(255) DEFAULT NULL,
  `patient` varchar(255) DEFAULT NULL,
  `provider_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ar15wy56lqayuqy27vbdmdc9v` (`claim_number`),
  KEY `FKj34fq3yhw0mrauw0xj8rvf14` (`provider_id`),
  CONSTRAINT `FKj34fq3yhw0mrauw0xj8rvf14` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `provider` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address1` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `facility` varchar(255) DEFAULT NULL,
  `provider_name` varchar(255) DEFAULT NULL,
  `provider_ref_num` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `tax_id` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hspqs6liacennbdydegdt5970` (`tax_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS=1;
