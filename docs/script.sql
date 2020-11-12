CREATE TABLE `beer` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(100) NOT NULL,
	`ingredients` VARCHAR(255) NOT NULL,
	`alcohol_content` VARCHAR(100) NOT NULL,
	`price` DECIMAL(10,2) NOT NULL,
	`category` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
;
