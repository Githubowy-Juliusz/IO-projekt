SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ioapp
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ioapp` ;

-- -----------------------------------------------------
-- Schema ioapp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ioapp` ;
USE `ioapp` ;

-- -----------------------------------------------------
-- Table `ioapp`.`equipment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ioapp`.`equipment` ;

CREATE TABLE IF NOT EXISTS `ioapp`.`equipment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `model` VARCHAR(45) NOT NULL,
  `year` INT NOT NULL,
  `cost` FLOAT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ioapp`.`client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ioapp`.`client` ;

CREATE TABLE IF NOT EXISTS `ioapp`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(90) NOT NULL,
  `identification_number` VARCHAR(11) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(16) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ioapp`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ioapp`.`orders` ;

CREATE TABLE IF NOT EXISTS `ioapp`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_equipment` INT NOT NULL,
  `id_client` INT NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_1_idx` (`id_equipment` ASC),
  INDEX `fk_orders_2_idx` (`id_client` ASC),
  UNIQUE INDEX `id_equipment_UNIQUE` (`id_equipment` ASC),
  CONSTRAINT `fk_orders_1`
    FOREIGN KEY (`id_equipment`)
    REFERENCES `ioapp`.`equipment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_2`
    FOREIGN KEY (`id_client`)
    REFERENCES `ioapp`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ioapp`.`admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ioapp`.`admin` ;

CREATE TABLE IF NOT EXISTS `ioapp`.`admin` (
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`login`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ioapp`.`archive`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ioapp`.`archive` ;

CREATE TABLE IF NOT EXISTS `ioapp`.`archive` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_equipment` INT NOT NULL,
  `id_client` INT NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

USE `ioapp`;

DELIMITER $$

USE `ioapp`$$
DROP TRIGGER IF EXISTS `ioapp`.`orders_BEFORE_DELETE` $$
USE `ioapp`$$
CREATE DEFINER = CURRENT_USER TRIGGER `ioapp`.`orders_BEFORE_DELETE` BEFORE DELETE ON `orders` FOR EACH ROW
BEGIN
	INSERT INTO archive VALUES(0, old.id_equipment, old.id_client, old.date);
END$$

USE `ioapp`$$
DROP TRIGGER IF EXISTS `ioapp`.`archive_BEFORE_UPDATE` $$
USE `ioapp`$$
CREATE DEFINER = CURRENT_USER TRIGGER `ioapp`.`archive_BEFORE_UPDATE` BEFORE UPDATE ON `archive` FOR EACH ROW
BEGIN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT =
	'YOU CAN\'T UPDATE THIS TABLE',
    MYSQL_ERRNO = 1;
END$$


USE `ioapp`$$
DROP TRIGGER IF EXISTS `ioapp`.`archive_BEFORE_DELETE` $$
USE `ioapp`$$
CREATE DEFINER = CURRENT_USER TRIGGER `ioapp`.`archive_BEFORE_DELETE` BEFORE DELETE ON `archive` FOR EACH ROW
BEGIN
	IF CURDATE() < old.date + INTERVAL 1 YEAR
    THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT =
		'YOU CAN\'T DELETE ORDER FROM ARCHIVE THAT IS NOT 1 YEAR OLD.',
		MYSQL_ERRNO = 2;
	END IF;
END$$

DELIMITER ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

