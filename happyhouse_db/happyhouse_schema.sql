-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema hhdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hhdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hhdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `hhdb` ;

-- -----------------------------------------------------
-- Table `hhdb`.`sidocode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`sidocode` (
  `sidoCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sidoCode`),
  UNIQUE INDEX `sidoName` (`sidoName` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`dongcode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`dongcode` (
  `dongCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`dongCode`),
  INDEX `sidoName` (`sidoName` ASC) VISIBLE,
  CONSTRAINT `dongcode_ibfk_1`
    FOREIGN KEY (`sidoName`)
    REFERENCES `hhdb`.`sidocode` (`sidoName`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`baseaddress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`baseaddress` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  `dongCode` VARCHAR(10) NULL DEFAULT NULL,
  `lat` VARCHAR(20) NULL DEFAULT NULL,
  `lng` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `dongCode` (`dongCode` ASC) VISIBLE,
  CONSTRAINT `baseaddress_ibfk_1`
    FOREIGN KEY (`dongCode`)
    REFERENCES `hhdb`.`dongcode` (`dongCode`))
ENGINE = InnoDB
AUTO_INCREMENT = 3597
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`bike`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`bike` (
  `id` TEXT NULL DEFAULT NULL,
  `place` TEXT NULL DEFAULT NULL,
  `gugunname` TEXT NULL DEFAULT NULL,
  `placedetail` TEXT NULL DEFAULT NULL,
  `lat` DOUBLE NULL DEFAULT NULL,
  `lng` DOUBLE NULL DEFAULT NULL,
  `maxcount` INT NULL DEFAULT NULL,
  `btype` TEXT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hhdb`.`bus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`bus` (
  `id` TEXT NULL DEFAULT NULL,
  `ARS-ID` TEXT NULL DEFAULT NULL,
  `station` TEXT NULL DEFAULT NULL,
  `lng` DOUBLE NULL DEFAULT NULL,
  `lat` DOUBLE NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hhdb`.`guguncode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`guguncode` (
  `gugunCode` VARCHAR(10) NOT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`gugunCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`clinic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`clinic` (
  `gugunCode` TEXT NULL DEFAULT NULL,
  `gugunName` TEXT NULL DEFAULT NULL,
  `gigwanName` TEXT NULL DEFAULT NULL,
  `gigwanAddress` TEXT NULL DEFAULT NULL,
  `weekdayTime` TEXT NULL DEFAULT NULL,
  `saturdayTime` TEXT NULL DEFAULT NULL,
  `sundayTime` TEXT NULL DEFAULT NULL,
  `tel` TEXT NULL DEFAULT NULL,
  `info` TEXT NULL DEFAULT NULL,
  `lat` TEXT NULL DEFAULT NULL,
  `lng` TEXT NULL DEFAULT NULL,
  CONSTRAINT `clinic_gugunCode_fk`
    FOREIGN KEY ()
    REFERENCES `hhdb`.`guguncode` ()
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hhdb`.`environment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`environment` (
  `fac_name` TEXT NULL DEFAULT NULL,
  `agreement_num` TEXT NULL DEFAULT NULL,
  `job_code` TEXT NULL DEFAULT NULL,
  `job_name` TEXT NULL DEFAULT NULL,
  `map_checkdate` TEXT NULL DEFAULT NULL,
  `check_ag` TEXT NULL DEFAULT NULL,
  `check_agname` TEXT NULL DEFAULT NULL,
  `map_check_div` TEXT NULL DEFAULT NULL,
  `disposal` TEXT NULL DEFAULT NULL,
  `check_desc` TEXT NULL DEFAULT NULL,
  `check_result` TEXT NULL DEFAULT NULL,
  `road_address` TEXT NULL DEFAULT NULL,
  `address` TEXT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hhdb`.`notice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`notice` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `date` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `title` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `content` VARCHAR(200) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `hit` INT NULL DEFAULT '0',
  PRIMARY KEY (`no`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`fileinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`fileinfo` (
  `no` INT NOT NULL,
  `savefolder` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NULL DEFAULT NULL,
  `originfile` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NULL DEFAULT NULL,
  `savefile` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NULL DEFAULT NULL,
  INDEX `fileinfo_no_fk_idx` (`no` ASC) VISIBLE,
  CONSTRAINT `fileinfo_no_fk`
    FOREIGN KEY (`no`)
    REFERENCES `hhdb`.`notice` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`houseinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`houseinfo` (
  `aptCode` INT NOT NULL,
  `aptName` VARCHAR(50) NULL DEFAULT NULL,
  `dongCode` VARCHAR(10) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  `buildYear` INT NULL DEFAULT NULL,
  `jibun` VARCHAR(30) NULL DEFAULT NULL,
  `lat` VARCHAR(30) NULL DEFAULT NULL,
  `lng` VARCHAR(30) NULL DEFAULT NULL,
  `img` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`aptCode`),
  INDEX `dongCode` (`dongCode` ASC) VISIBLE,
  CONSTRAINT `houseinfo_ibfk_1`
    FOREIGN KEY (`dongCode`)
    REFERENCES `hhdb`.`dongcode` (`dongCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`housedeal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`housedeal` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `aptCode` INT NULL DEFAULT NULL,
  `dealAmount` VARCHAR(20) NULL DEFAULT NULL,
  `dealYear` INT NULL DEFAULT NULL,
  `dealMonth` INT NULL DEFAULT NULL,
  `dealDay` INT NULL DEFAULT NULL,
  `area` VARCHAR(30) NULL DEFAULT NULL,
  `floor` VARCHAR(30) NULL DEFAULT NULL,
  `type` VARCHAR(30) NULL DEFAULT NULL,
  `rentMoney` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `aptCode` (`aptCode` ASC) VISIBLE,
  CONSTRAINT `housedeal_ibfk_1`
    FOREIGN KEY (`aptCode`)
    REFERENCES `hhdb`.`houseinfo` (`aptCode`))
ENGINE = InnoDB
AUTO_INCREMENT = 68865
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`users` (
  `userId` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `userName` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `password` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `email` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NULL DEFAULT NULL,
  `phone` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NULL DEFAULT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`jjimdong`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`jjimdong` (
  `userId` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `dongNo` INT NOT NULL,
  PRIMARY KEY (`userId`, `dongNo`),
  INDEX `fk_users_has_baseaddress_baseaddress1_idx` (`dongNo` ASC) VISIBLE,
  INDEX `fk_users_has_baseaddress_users1_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `fk_users_has_baseaddress_baseaddress1`
    FOREIGN KEY (`dongNo`)
    REFERENCES `hhdb`.`baseaddress` (`no`),
  CONSTRAINT `fk_users_has_baseaddress_users1`
    FOREIGN KEY (`userId`)
    REFERENCES `hhdb`.`users` (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`qna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`qna` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `date` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `title` VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  `content` VARCHAR(200) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci' NOT NULL,
  PRIMARY KEY (`no`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`qnacomment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`qnacomment` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(16) NULL DEFAULT NULL,
  `comment` VARCHAR(500) NULL DEFAULT NULL,
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `qnano` INT NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `qnacomment_qnano_fk_idx` (`qnano` ASC) VISIBLE,
  CONSTRAINT `qnacomment_qnano_fk`
    FOREIGN KEY (`qnano`)
    REFERENCES `hhdb`.`qna` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hhdb`.`starbucks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`starbucks` (
  `name` TEXT NULL DEFAULT NULL,
  `address` TEXT NULL DEFAULT NULL,
  `lat` TEXT NULL DEFAULT NULL,
  `lng` TEXT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hhdb`.`station`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`station` (
  `no` TEXT NULL DEFAULT NULL,
  `name` TEXT NULL DEFAULT NULL,
  `lat` TEXT NULL DEFAULT NULL,
  `lng` TEXT NULL DEFAULT NULL,
  `address` TEXT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `hhdb`.`store`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hhdb`.`store` (
  `no` INT NULL DEFAULT NULL,
  `name` TEXT NULL DEFAULT NULL,
  `type` TEXT NULL DEFAULT NULL,
  `sidoName` TEXT NULL DEFAULT NULL,
  `gugunName` TEXT NULL DEFAULT NULL,
  `dongCode` TEXT NULL DEFAULT NULL,
  `dongName` TEXT NULL DEFAULT NULL,
  `lat` TEXT NULL DEFAULT NULL,
  `lng` TEXT NULL DEFAULT NULL,
  CONSTRAINT `store_dongCode_fk`
    FOREIGN KEY ()
    REFERENCES `hhdb`.`dongcode` ()
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
