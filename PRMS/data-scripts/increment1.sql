use phoenix;

DROP TABLE IF EXISTS `phoenix`.`program-slot` ;
DROP TABLE IF EXISTS `phoenix`.`producer` ;
DROP TABLE IF EXISTS `phoenix`.`presenter` ;


-- -----------------------------------------------------
-- Table `phoenix`.`producer`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `phoenix`.`producer` (
  `name` VARCHAR(45) NOT NULL ,
  `user-id` VARCHAR(40) NOT NULL ,
  PRIMARY KEY (`name`) ,
  CONSTRAINT `FK_producer_user`
    FOREIGN KEY (`user-id` )
    REFERENCES `phoenix`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `phoenix`.`producer` (`name` ASC) ;

-- -----------------------------------------------------
-- Table `phoenix`.`presenter`
-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `phoenix`.`presenter` (
  `name` VARCHAR(45) NOT NULL ,
  `user-id` VARCHAR(40) NOT NULL ,
  PRIMARY KEY (`name`) ,
  CONSTRAINT `FK_presenter_user`
    FOREIGN KEY (`user-id` )
    REFERENCES `phoenix`.`user` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `phoenix`.`presenter` (`name` ASC) ;

-- -----------------------------------------------------
-- Table `phoenix`.`program-slot`
-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `phoenix`.`program-slot` (
  `dateOfProgram` DATE NOT NULL ,  
  `startTime` TIME NOT NULL ,  
  `duration` TIME NOT NULL ,
  `program-name` VARCHAR(45) NULL ,
  `producer-name` VARCHAR(40) NULL ,
  `presenter-name` VARCHAR(40) NULL ,
  PRIMARY KEY (`dateOfProgram`, `startTime`) ,
  CONSTRAINT `FK_slot_program`
    FOREIGN KEY (`program-name` )
    REFERENCES `phoenix`.`radio-program` (`name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION ,
  CONSTRAINT `FK_slot_producer`
    FOREIGN KEY (`producer-name` )
    REFERENCES `phoenix`.`producer` (`name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION ,
  CONSTRAINT `FK_slot_presenter`
    FOREIGN KEY (`presenter-name` )
    REFERENCES `phoenix`.`presenter` (`name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- ALTER Table `phoenix`.`user`
-- -----------------------------------------------------

alter table `phoenix`.`user` 
	 add `address` varchar(100) null after `name`,
	add `joining_date` date null after `role`;
    
-- -----------------------------------------------------
-- UPDATE Table `phoenix`.`user`
-- -----------------------------------------------------
update `phoenix`.`user` set
	`address`="jurong east",
    `joining_date`="2014-03-10" where `id`="catbert";

update `phoenix`.`user` set
	`address`="clementi",
    `joining_date`="2014-11-10" where `id`="dilbert";

update `phoenix`.`user` set
	`address`="Lakeside",
    `joining_date`="2013-11-10" where `id`="dogbert";

update `phoenix`.`user` set
	`address`="clementi",
    `joining_date`="2015-01-10" where `id`="pointyhead";
    
update `phoenix`.`user` set
	`address`="boon lay",
    `joining_date`="2014-11-10" where `id`="wally";

-- -----------------------------------------------------
-- Insert Data For Table `phoenix`.`producer`
-- -----------------------------------------------------

-- name, user-id
insert into `phoenix`.`producer` values("dilbert, the hero", "dilbert");
insert into `phoenix`.`producer` values("wally, the bludger", "wally");
insert into `phoenix`.`producer` values("dogbert, the CEO", "dogbert");

-- -----------------------------------------------------
-- Insert Data For Table `phoenix`.`presenter`
-- -----------------------------------------------------

-- name, user-id
insert into `phoenix`.`presenter` values("dilbert, the hero", "dilbert");

-- -----------------------------------------------------
-- Insert Data For Table `phoenix`.`program-slot`
-- -----------------------------------------------------
-- dateOfProgram, startTime, duration, program-name, producer-name, presenter-name
insert into `phoenix`.`program-slot` values("2015/09/15", "07:30:00", '00:30:00', "news", "wally, the bludger", "dilbert, the hero");  
insert into `phoenix`.`program-slot` values("2015/09/15", "19:30:00", '00:30:00', "news", "wally, the bludger", "dilbert, the hero");  
