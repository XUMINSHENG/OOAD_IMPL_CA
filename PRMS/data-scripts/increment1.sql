use phoenix;

DROP TABLE IF EXISTS `phoenix`.`program-slot`;
DROP TABLE IF EXISTS `phoenix`.`producer` ;
DROP TABLE IF EXISTS `phoenix`.`presenter` ;


-- -----------------------------------------------------
-- Table `phoenix`.`producer`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `phoenix`.`producer` (
  `name` VARCHAR(45) NOT NULL ,
  `user-id` VARCHAR(40) NOT NULL ,
  `isActive` VARCHAR(1) NOT NULL ,
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
  `isActive` VARCHAR(1) NOT NULL ,
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
  `year` INT NOT NULL ,
  `weeknum` INT(2) NOT NULL ,
  `dateOfProgram` DATE NOT NULL ,  
  `startTime` TIME NOT NULL ,  
  `duration` TIME NOT NULL ,
  `program-name` VARCHAR(45) NULL ,
  `producer-name` VARCHAR(40) NULL ,
  `presenter-name` VARCHAR(40) NULL ,
  PRIMARY KEY (`year`,`weeknum`,`dateOfProgram`, `startTime`) ,
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
    ON UPDATE NO ACTION ,
  CONSTRAINT `year_weeknum_ps`
    FOREIGN KEY (`year`,`weeknum` )
    REFERENCES `phoenix`.`weekly-schedule` (`year`,`weeknum`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `name_program_slot` ON `phoenix`.`program-slot` (`program-name` ASC) ;

CREATE INDEX `year_weeknum_program_slot` ON `phoenix`.`program-slot` (`year`,`weeknum` ASC) ;

CREATE UNIQUE INDEX `dateOfProgram_UNIQUE` ON `phoenix`.`program-slot` (`year`,`weeknum`,`dateOfProgram`,`startTime` ASC) ;

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
insert into `phoenix`.`producer` values("dilbert, the hero", "dilbert",'Y');
insert into `phoenix`.`producer` values("wally, the bludger", "wally",'Y');
insert into `phoenix`.`producer` values("dogbert, the CEO", "dogbert",'Y');

-- -----------------------------------------------------
-- Insert Data For Table `phoenix`.`presenter`
-- -----------------------------------------------------

-- name, user-id
insert into `phoenix`.`presenter` values("dilbert, the hero", "dilbert",'Y');

-- -----------------------------------------------------
-- Insert Data For Table `phoenix`.`program-slot`
-- -----------------------------------------------------
-- dateOfProgram, startTime, duration, program-name, producer-name, presenter-name
insert into `phoenix`.`annual-schedule` values(2015, "dilbert");
insert into `phoenix`.`weekly-schedule` values(2015, 1, "dilbert");
insert into `phoenix`.`weekly-schedule` values(2015, 37, "dilbert");  
insert into `phoenix`.`weekly-schedule` values(2015, 38, "dilbert");
insert into `phoenix`.`program-slot` values(2015,1,"2015/01/01", "19:30:00", '00:30:00', "news", "wally, the bludger", "dilbert, the hero");    
insert into `phoenix`.`program-slot` values(2015,37,"2015/09/10", "08:30:00", '00:30:00', "news", "wally, the bludger", "dilbert, the hero");  
insert into `phoenix`.`program-slot` values(2015,38,"2015/09/15", "07:30:00", '00:30:00', "news", "wally, the bludger", "dilbert, the hero");  
insert into `phoenix`.`program-slot` values(2015,38,"2015/09/15", "19:30:00", '00:30:00', "news", "wally, the bludger", "dilbert, the hero");  

