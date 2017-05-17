-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=1;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=1;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP schema if exists `mydb`;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`movies` (
  `m_id` TINYINT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  `m_title` VARCHAR(30) NOT NULL,
  `m_release_date` DATE NOT NULL,
  `m_type` ENUM('serial', 'film') NOT NULL,
  `m_age` TINYINT(3) UNSIGNED NOT NULL,
  `m_image_name` VARCHAR(45) NULL DEFAULT NULL,
  `m_duration` TIME NOT NULL,
  `m_deleted` TINYINT(1) NOT NULL DEFAULT '0',
  `m_budget` INT(11) NULL DEFAULT NULL,
  `m_profit` INT(11) NULL DEFAULT NULL,
  `m_description` TEXT NOT NULL,
  PRIMARY KEY (`m_id`),
  INDEX `idx_m_release_date` (`m_release_date` ASC),
  INDEX `idx_m_title` (`m_title` ASC),
  INDEX `idx_m_age` (`m_age` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `u_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `u_login` VARCHAR(45) NOT NULL,
  `u_password` VARCHAR(45) NOT NULL,
  `u_name` VARCHAR(45) NOT NULL,
  `u_email` VARCHAR(45) NOT NULL,
  `u_lastname` VARCHAR(30) NOT NULL,
  `u_position` ENUM('admin', 'user') NOT NULL DEFAULT 'user',
  `u_rating` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0',
  `u_isblock` TINYINT(1) NOT NULL DEFAULT '0',
  `u_local` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE INDEX `idx_login_password` (`u_login` ASC, `u_password` ASC),
  UNIQUE INDEX `idx_email` (`u_email` ASC),
  INDEX `idx_lastname` (`u_lastname` ASC),
  INDEX `idx_name_lastname` (`u_name` ASC, `u_lastname` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`comment` (
  `c_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment` TEXT NULL DEFAULT NULL,
  `m_id` TINYINT(4) UNSIGNED NOT NULL,
  `u_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`c_id`),
  INDEX `fk_comment_movies_idx` (`m_id` ASC),
  INDEX `fk_comment_users1_idx` (`u_id` ASC),
  INDEX `idx_m_id_u_id` (`m_id` ASC, `u_id` ASC),
  CONSTRAINT `fk_comment_movies`
  FOREIGN KEY (`m_id`)
  REFERENCES `mydb`.`movies` (`m_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_users1`
  FOREIGN KEY (`u_id`)
  REFERENCES `mydb`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  AUTO_INCREMENT = 20
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`countries` (
  `c_id` TINYINT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`c_id`),
  UNIQUE INDEX `c_name_UNIQUE` (`c_name` ASC),
  UNIQUE INDEX `c_id_UNIQUE` (`c_id` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 123
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`genres` (
  `g_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `g_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`g_id`),
  INDEX `genres_name` (`g_name` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`m2m_countries_movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`m2m_countries_movies` (
  `countries_c_id` TINYINT(4) UNSIGNED NOT NULL,
  `movies_m_id` TINYINT(4) UNSIGNED NOT NULL,
  PRIMARY KEY (`countries_c_id`, `movies_m_id`),
  INDEX `fk_countries_has_movies_movies1_idx` (`movies_m_id` ASC),
  INDEX `fk_countries_has_movies_countries1_idx` (`countries_c_id` ASC),
  CONSTRAINT `fk_countries_has_movies_countries1`
  FOREIGN KEY (`countries_c_id`)
  REFERENCES `mydb`.`countries` (`c_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_countries_has_movies_movies1`
  FOREIGN KEY (`movies_m_id`)
  REFERENCES `mydb`.`movies` (`m_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`m2m_movies_genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`m2m_movies_genres` (
  `movies_m_id` TINYINT(4) UNSIGNED NOT NULL,
  `genres_g_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`movies_m_id`, `genres_g_id`),
  INDEX `fk_movies_has_genres_genres1_idx` (`genres_g_id` ASC),
  INDEX `fk_movies_has_genres_movies1_idx` (`movies_m_id` ASC),
  CONSTRAINT `fk_movies_has_genres_genres1`
  FOREIGN KEY (`genres_g_id`)
  REFERENCES `mydb`.`genres` (`g_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_has_genres_movies1`
  FOREIGN KEY (`movies_m_id`)
  REFERENCES `mydb`.`movies` (`m_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`mark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`mark` (
  `movie_id` TINYINT(4) UNSIGNED NOT NULL,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `mark` TINYINT(2) NULL DEFAULT NULL,
  PRIMARY KEY (`movie_id`, `user_id`),
  UNIQUE INDEX `idx_m_id_u_id` (`user_id` ASC, `movie_id` ASC),
  INDEX `fk_movies_has_users_users1_idx` (`user_id` ASC),
  INDEX `fk_movies_has_users_movies1_idx` (`movie_id` ASC),
  CONSTRAINT `fk_movies_has_users_movies1`
  FOREIGN KEY (`movie_id`)
  REFERENCES `mydb`.`movies` (`m_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_movies_has_users_users1`
  FOREIGN KEY (`user_id`)
  REFERENCES `mydb`.`users` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`persons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`persons` (
  `p_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `p_name` VARCHAR(45) NOT NULL,
  `p_lastname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`p_id`),
  UNIQUE INDEX `idx_name_lastname` (`p_name` ASC, `p_lastname` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 150
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`positions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`positions` (
  `p_id` TINYINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `p_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`p_id`),
  UNIQUE INDEX `idx_type` (`p_type` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`members` (
  `persons_p_id` INT(11) UNSIGNED NOT NULL,
  `positions_p_id` TINYINT(3) UNSIGNED NOT NULL,
  `movies_m_id` TINYINT(4) UNSIGNED NOT NULL,
  PRIMARY KEY (`persons_p_id`, `positions_p_id`, `movies_m_id`),
  INDEX `fk_persons_has_positions_positions1_idx` (`positions_p_id` ASC),
  INDEX `fk_persons_has_positions_persons1_idx` (`persons_p_id` ASC),
  INDEX `fk_members_movies1_idx` (`movies_m_id` ASC),
  CONSTRAINT `fk_members_movies1`
  FOREIGN KEY (`movies_m_id`)
  REFERENCES `mydb`.`movies` (`m_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_persons_has_positions_persons1`
  FOREIGN KEY (`persons_p_id`)
  REFERENCES `mydb`.`persons` (`p_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_persons_has_positions_positions1`
  FOREIGN KEY (`positions_p_id`)
  REFERENCES `mydb`.`positions` (`p_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;




INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('1', 'action');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('2', 'horror');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('3', 'documentary');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('4', 'animation');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('5', 'comedy');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('6', 'family');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('7', 'romance');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('8', 'drama');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('9', 'cartoon');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('10', 'biography');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('11', 'music');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('12', 'musical');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('13', 'fiction');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('14', 'fantasy');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('15', 'adventure');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('16', 'thriller');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('17', 'criminal');
INSERT INTO `mydb`.`genres` (`g_id`, `g_name`) VALUES ('18', 'melodrama');

INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_budget`,`m_profit`,`m_description`,`m_duration`) VALUES ('1', 'Inside Out', '2015-05-18', 'film','6','inside_out.jpg','175000000','356461711','Growing up can be a bumpy road, and it\'s no exception for Riley, who is uprooted from her Midwest life when her father starts a new job in San Francisco. Like all of us, Riley is guided by her emotions - Joy, Fear, Anger, Disgust and Sadness. The emotions live in Headquarters, the control center inside Riley\'s mind, where they help advise her through everyday life. As Riley and her emotions struggle to adjust to a new life in San Francisco, turmoil ensues in Headquarters. Although Joy, Riley\'s main and most important emotion, tries to keep things positive, the emotions conflict on how best to navigate a new city, house and school.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('2', 'Zootopia', '2016-02-11', 'film','6','zootopia.jpeg' , 'From the largest elephant to the smallest shrew, the city of Zootopia is a mammal metropolis where various animals live and thrive. When Judy Hopps becomes the first rabbit to join the police force, she quickly learns how tough it is to enforce the law. Determined to prove herself, Judy jumps at the opportunity to solve a mysterious case. Unfortunately, that means working with Nick Wilde, a wily fox who makes her job even harder.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('3', 'Mune: Guardian of the Moon', '2015-02-05', 'film','6','mune_le_gardien_de_la_lune.jpg', 'When a faun named Mune becomes the Guardian of the Moon, little did he had unprepared experience with the Moon and an accident that could put both the Moon and the Sun in danger, including a corrupt titan named Necross who wants the Sun for himself and placing the balance of night and day in great peril. Now with the help of a wax-child named Glim and the warrior, Sohone who also became the Sun Guardian, they go out on an exciting journey to get the Sun back and restore the Moon to their rightful place in the sky.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('4', 'Friends', '1994-09-22', 'serial','12','friends.jpg', 'Rachel Green, Ross Geller, Monica Geller, Joey Tribbiani, Chandler Bing and Phoebe Buffay are all friends, living off of one another in the heart of New York City. Over the course of ten years, this average group of buddies goes through massive mayhem, family trouble, past and future romances, fights, laughs, tears and surprises as they learn what it really means to be a friend.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('5', 'Deadpool', '2016-02-12', 'film','18','deadpool.jpg', 'This is the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('6', 'Logan', '2017-03-02', 'film','16','logan.jpg', 'In 2029 the mutant population has shrunk significantly and the X-Men have disbanded. Logan, whose power to self-heal is dwindling, has surrendered himself to alcohol and now earns a living as a chauffeur. He takes care of the ailing old Professor X whom he keeps hidden away. One day, a female stranger asks Logan to drive a girl named Laura to the Canadian border. At first he refuses, but the Professor has been waiting for a long time for her to appear. Laura possesses an extraordinary fighting prowess and is in many ways like Wolverine. She is pursued by sinister figures working for a powerful corporation; this is because her DNA contains the secret that connects her to Logan. A relentless pursuit begins - In this third cinematic outing featuring the Marvel comic book character Wolverine we see the superheroes beset by everyday problems. They are ageing, ailing and struggling to survive financially. A decrepit Logan is forced to ask himself if he can or even wants to put his remaining...','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('7', '1+1', '2011-11-02', 'film','0','1+1.jpg', 'In Paris, the aristocratic and intellectual Philippe is a quadriplegic millionaire who is interviewing candidates for the position of his carer, with his red-haired secretary Magalie. Out of the blue, the rude African Driss cuts the line of candidates and brings a document from the Social Security and asks Phillipe to sign it to prove that he is seeking a job position so he can receive his unemployment benefit. Philippe challenges Driss, offering him a trial period of one month to gain experience helping him. Then Driss can decide whether he would like to stay with him or not. Driss accepts the challenge and moves to the mansion, changing the boring life of Phillipe and his employees.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('8', 'Split', '2017-01-20', 'film','16','split.jpg', 'Kevin Wendell Crumb, a man with 23 different personalities, abducts three teenage girls in his basement for unknown reasons. As Kevin\'s therapist delves deeper into his mysterious disorder, the girls must find ways to escape before the last and sinister 24th personality reveals itself.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('9', 'SHIELD', '2013-09-24', 'serial','12','shield.jpg', 'After the Battle of New York, the world has changed. It now knows not only about the Avengers, but also the powerful menaces that require those superheroes and more to face them. In response, Phil Coulson of the Strategic Homeland Intervention, Enforcement and Logistics Division assembles an elite covert team to find and deal with these threats wherever they are found. With a world rapidly becoming more bizarre and dangerous than ever before as the supervillains arise, these agents of S.H.I.E.L.D. are ready to take them on.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('10', 'Doctor House', '2004-11-16', 'serial','12','doctor_house.jpg', 'This long-running medical drama follows the professional and personal life of Gregory House- a witty, arrogant, rule-breaking, self-destructive, pain-pill addicted but genius diagnostician at the fictional Princeton-Plainsboro Teaching Hospital in New Jersey. House and his team of doctors work against the clock to diagnose and treat patients when other doctors can\'t seem to figure out what\'s wrong with them, often resorting to unorthodox methods based on House\'s hunches on the patients, their families, or his own personal experiences.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('11', 'Family Guy', '1999-01-31', 'serial','18','family_guy.jpg', 'Sick, twisted and politically incorrect, the animated series features the adventures of the Griffin family. Endearingly ignorant Peter and his stay-at-home wife Lois reside in Quahog, R.I., and have three kids. Meg, the eldest child, is a social outcast, and teenage Chris is awkward and clueless when it comes to the opposite sex. The youngest, Stewie, is a genius baby bent on killing his mother and destroying the world. The talking dog, Brian, keeps Stewie in check while sipping martinis and sorting through his own life issues.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('12', 'Futurama', '1999-03-28', 'serial','16','futurama.jpg', 'Phillip Fry is a 25-year-old pizza delivery boy whose life is going nowhere. When he accidentally freezes himself on December 31, 1999, he wakes up 1,000 years in the future and has a chance to make a fresh start. He goes to work for the Planet Express Corporation, a futuristic delivery service that transports packages to all five quadrants of the universe. His companions include the delivery ship\'s captain, Leela, a beautiful one-eyed female alien who kicks some serious butt, and Bender, a robot with very human flaws.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('13', 'The Big Bang theory', '2007-09-24', 'serial','16','the_big_bang_theory.jpg', 'Leonard Hofstadter and Sheldon Cooper are both brilliant physicists working at Cal Tech in Pasadena, California. They are colleagues, best friends, and roommates, although in all capacities their relationship is always tested primarily by Sheldon\'s regimented, deeply eccentric, and non-conventional ways. They are also friends with their Cal Tech colleagues mechanical engineer Howard Wolowitz and astrophysicist Rajesh Koothrappali. The foursome spend their time working on their individual work projects, playing video games, watching science-fiction movies, or reading comic books. As they are self-professed nerds, all have little or no luck with women. When Penny, a pretty woman and an aspiring actress from Omaha, moves into the apartment across the hall from Leonard and Sheldon\'s, Leonard has another aspiration in life, namely to get Penny to be his girlfriend.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('14', 'Spiderman', '2002-04-30', 'film','12','spiderman.jpg', 'Based on Marvel Comics\' superhero character, this is a story of Peter Parker who is a nerdy high-schooler. He was orphaned as a child, bullied by jocks, and can\'t confess his crush for his stunning neighborhood girl Mary Jane Watson. To say his life is "miserable" is an understatement. But one day while on an excursion to a laboratory a runaway radioactive spider bites him... and his life changes in a way no one could have imagined. Peter acquires a muscle-bound physique, clear vision, ability to cling to surfaces and crawl over walls, shooting webs from his wrist ... but the fun isn\'t going to last. An eccentric millionaire Norman Osborn administers a performance enhancing drug on himself and his maniacal alter ego Green Goblin emerges. Now Peter Parker has to become Spider-Man and take Green Goblin to the task... or else Goblin will kill him. They come face to face and the war begins in which only one of them will survive at the end.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('15', 'Spiderman 2', '2004-06-25', 'film','12','spiderman_2.jpg', 'Peter Parker is an unhappy man: after two years of fighting crime as Spider-Man, his life has begun to fall apart. The girl he loves is engaged to someone else, his grades are slipping, he cannot keep any of his jobs, and on top of it, the newspaper Daily Bugle is attacking him viciously, claiming that Spider-Man is a criminal. He reaches the breaking point and gives up the crime fighter\'s life, once and for all. But after a failed fusion experiment, eccentric and obsessive scientist Dr. Otto Octavius is transformed into super villain Doctor Octopus, Doc Ock for short, having four long tentacles as extra hands. Peter guesses it might just be time for Spider-Man to return, but would he act upon it?','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('16', 'Spiderman 3', '2007-04-16', 'film','12','spiderman_3.jpg', 'Peter Parker has finally managed to piece together the once-broken parts of his life, maintaining a balance between his relationship with Mary-Jane and his responsibility as Spider-Man. But more challenges arise for our young hero. Peter\'s old friend Harry Obsourne has set out for revenge against Peter; taking up the mantle of his late father\'s persona as The New Goblin, and Peter must also capture Uncle Ben\'s real killer, Flint Marko, who has been transformed into his toughest foe yet, the Sandman. All hope seems lost when suddenly Peter\'s suit turns jet-black and greatly amplifies his powers. But it also begins to greatly amplify the much darker qualities of Peter\'s personality that he begins to lose himself to. Peter has to reach deep inside himself to free the compassionate hero he used to be if he is to ever conquer the darkness within and face not only his greatest enemies, but also...himself.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('17', 'Ghost rider', '2007-01-15', 'film','12','ghost_rider.jpg', 'When the motorcyclist Johnny Blaze finds that his father Barton Blaze has a terminal cancer, he accepts a pact with the Mephistopheles, giving his soul for the health of his beloved father. But the devil deceives him, and Barton dies in a motorcycle accident during an exhibition. Johnny leaves the carnival, his town, his friends and his girlfriend Roxanne. Years later Johnny Blaze becomes a famous motorcyclist, who risks his life in his shows, and he meets Roxanne again, now a TV reporter. However, Mephistopheles proposes Johnny to release his contract if he become the "Ghost Rider" and defeat his evil son Blackheart, who wants to possess one thousand evil souls and transform hell on earth.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('18', 'Me, Myself and Irene', '2000-06-15', 'film','16','me_myself_and_irene.jpg', 'Charlie is a Rhode Island state trooper with a multiple personalities. He is otherwise mild-mannered and non confrontational until somebody or something pushes him a little too far. That\'s when his maniacal alter-ego, Hank, takes over. Charlie is assigned on a routine mission to return alleged fugitive Irene back to upstate New York, but they wind up on the run from corrupt police officers. And their escape would be a lot simpler on everybody involved if Hank didn\'t keep stepping in at the most inopportune times...','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('19', '1000 words', '2012-03-09', 'film','16','1000_words.jpg', 'Jack McCall, played by Eddie Murphy, finds an unusual tree in his yard after an encounter with a spiritual guru. After discovering that with each word he speaks, a leaf drops off of the tree, Jack refuses to speak at all, as doing so will keep the tree, and him, alive. However, his work, marriage, and friendships are all affected by his choice. Can Jack figure out an alternative method of survival? Or will he simply have to live the rest of his life to the fullest?','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('20', 'La la land', '2016-08-31', 'film','16','la_la_land.jpg', 'In Hollywood, Mia and Sebastian are struggling to make it in their respective careers, about which each has extreme passion. Mia is an actress who moved from small town Nevada and dropped out of college five years ago to pursue her dream. She is enamored with old time Hollywood - the movies on which she grew up - but hates the cattle herding feeling of going on auditions, and her belief that she needs to schmooze at social events to get ahead in the business. Sebastian is a jazz pianist, his style of jazz in the vein of traditionalists like Charlie Parker and Thelonious Monk. He wants to do his part to preserve that tradition, especially as he knows that that style of music is dying. He has trouble emotionally playing music he doesn\'t like just to get a paying gig. His dream is to open his own jazz club, most specifically in what used to be a famous jazz club that has since been converted to a tapas bar cum salsa dance club. Partly because of their individual struggles and partly because of the situations, their initial couple of chance meetings are antagonistic ones. But they eventually become attracted to each other largely because of the passion each sees in the other for what he/she is striving for in life. However, there are many obstacles to a Mia/Sebastian happily ever after. They may be able to endure the life of a struggling artist for so long before those struggles take their toll. The pursuit of their individual dreams may take all their energies, with nothing left for their relationship. And any compromise each may make in getting ahead may change the person with who each has fallen in love.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('21', 'Sing', '2016-09-11', 'film','6','sing.jpg', 'Set in a world like ours but entirely inhabited by animals, Buster Moon, a dapper koala, presides over a once-grand theater that has fallen on hard times. Buster is an eternal-some, might even say delusional-optimist, who loves his theater above all and will do anything to preserve it. Now faced with the crumbling of his life\'s ambition, he has one final chance to restore his fading jewel to its former glory by producing the world\'s greatest singing competition.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('22', 'Marley and Me', '2008-12-25', 'film','12','marley_and_me.jpg', 'After their wedding, newspaper writers John and Jennifer Grogan move to Florida. In an attempt to stall Jennifer\'s "biological clock", John gives her a puppy. While the puppy Marley grows into a 100 pound dog, he loses none of his puppy energy or rambunctiousness. Meanwhile, Marley gains no self-discipline. Marley\'s antics give John rich material for his newspaper column. As the Grogans mature and have children of their own, Marley continues to test everyone\'s patience by acting like the world\'s most impulsive dog.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('23', 'Doctor Strange', '2016-10-13', 'film','16','doctor_strange.jpg', 'Marvel\'s "Doctor Strange" follows the story of the talented neurosurgeon Doctor Stephen Strange who, after a tragic car accident, must put ego aside and learn the secrets of a hidden world of mysticism and alternate dimensions. Based in New York City\'s Greenwich Village, Doctor Strange must act as an intermediary between the real world and what lies beyond, utilising a vast array of metaphysical abilities and artifacts to protect the Marvel Cinematic Universe.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('24', 'Sausage party', '2016-03-14', 'film','18','sausage_party.jpg', 'The products at Shopwell\'s Grocery Store are made to believe a code that helps them live happy lives until it\'s time for them to leave the comfort of the supermarket and head for the great beyond. However, after a botched trip to the great beyond leaves one sausage named Frank and his companion Bun stranded, Frank goes to great lengths (pun intended) to return to his package and make another trip to the great beyond. But as Frank\'s journey takes him from one end of the supermarket to the other, Frank\'s quest to discover the truth about his existence as a sausage turns incredibly dark. Can he expose the truth to the rest of the supermarket and get his fellow products to rebel against their human masters?','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('25', 'Vampire Diaries', '2009-09-10', 'serial','16','vampire_diaries.jpg', 'The vampire brothers Damon and Stefan Salvatore, eternal adolescents, having been leading "normal" lives, hiding their bloodthirsty condition, for centuries, moving on before their non-aging is noticed. They are back in the Virginia town where they became vampires. Stefan is noble, denying himself blood to avoid killing, and tries to control his evil brother Damon, who promised to Stefan an eternity of misery. Stefan falls in love with schoolgirl Elena, who has an uncanny resemblance to the Salvatore brothers old love, Katherine; and whose best friend Bonnie.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('26', 'The Martian', '2015-09-1', 'film','16','the_martian.jpg', 'During a manned mission to Mars, Astronaut Mark Watney is presumed dead after a fierce storm and left behind by his crew. But Watney has survived and finds himself stranded and alone on the hostile planet. With only meager supplies, he must draw upon his ingenuity, wit and spirit to subsist and find a way to signal to Earth that he is alive. Millions of miles away, NASA and a team of international scientists work tirelessly to bring "the Martian" home, while his crewmates concurrently plot a daring, if not impossible, rescue mission. As these stories of incredible bravery unfold, the world comes together to root for Watney\'s safe return.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('27', 'Mad Max Fury Road', '2015-05-07', 'film','16','mad_max_fury_road.jpg', 'An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and almost everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. There\'s Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('28', 'Star Wars 7', '2015-12-14', 'film','12','star_wars_7.jpg', 'When critical information is placed into a simple droid, both the evil First Order and the heroic Resistance go searching for it. Then something neither of them planned for happens: Rey, a scavenger from the planet Jakku finds the droid and keeps it as her own. Finn, an ex-stormtrooper who hopes to leave his past behind him, crosses paths with her and breaks the news of what exactly the importance of the droid is. Next thing either of them know is that they are on the run in order to withhold the droid from the First Order. They meet a pair of old buddies and after a few skirmishes between them and the determined First Order, the droid is brought to the Resistance base. There, multiple plans are hatched against the First Order while the secret information on the droid is slowly and surely working itself out...','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('29', 'Jurassic World', '2015-05-29', 'film','12','jurassic_world.jpg', 'The new Jurassic World, owned by the corporation of Simon Masrani operates in Isla Nublar, Central America, with dinosaurs genetically created by the InGen Company. The workaholic and uptight manager Claire Dearing receives her nephews Gray and Zach in the park, but she is too busy to give attention to them and asks her assistant to escort the boys. Meanwhile the dedicated Owen Grady is training four velociraptors and the InGen security guard Vic Hoskins believes that the animal can be trained for military use. When Owen and two other employees go to an isolated paddock to evaluate the new attraction of the park, the hybrid dinosaur Indominus Rex, the animal lures them, kills the two men and flees from the spot. Owen escapes and asks Masrani to kill the Indominus, but he believes his security team can contain and capture the animal that cost lots of money. However the team is destroyed by the Indominus and Claire orders the evacuation of the tourists from the island. But the dangerous pterosaurs escape from the aviary and the place goes havoc. Meanwhile Gray and Zach are riding a gyro-sphere in the restricted area and Claire and Owen seek them out. With the chaos in the island, Vic assumes the command and decides to use the four velociraptors to locate and destroy the Indominus. Will his plan work?','12:12:00');
INSERT INTO `mydb`.`movies` (`m_id`, `m_title`, `m_release_date`, `m_type`, `m_age`, `m_image_name`,`m_description`,`m_duration`) VALUES ('30', 'Amy', '2015-05-16', 'film','18','amy.jpg', 'A documentary on the life of Amy Winehouse, the immensely talented yet doomed songstress. We see her from her teen years, where she already showed her singing abilities, to her finding success and then her downward spiral into alcoholism and drugs.','12:12:00');

INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('1', '4');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('1', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('1', '6');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('4', '7');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('4', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('3', '4');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('5', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('5', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('6', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('6', '8');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('7', '8');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('7', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('8', '2');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('9', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('10', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('10', '8');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('10', '6');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('11', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('11', '6');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('11', '9');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('12', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('12', '9');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('12', '13');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('13', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('13', '18');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('14', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('14', '13');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('14', '15');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('15', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('15', '13');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('15', '15');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('16', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('16', '13');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('16', '15');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('17', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('17', '14');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('17', '16');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('18', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('19', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('19', '8');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('19', '14');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('20', '8');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('20', '12');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('20', '18');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('21', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('21', '9');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('21', '17');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('22', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('22', '6');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('22', '8');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('23', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('23', '13');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('23', '14');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('24', '5');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('24', '9');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('24', '14');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('25', '2');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('25', '14');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('25', '16');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('26', '13');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('26', '15');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('27', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('27', '13');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('27', '15');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('28', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('28', '13');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('28', '14');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('29', '1');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('29', '13');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('30', '3');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('30', '10');
INSERT INTO `mydb`.`m2m_movies_genres` (`movies_m_id`, `genres_g_id`) VALUES ('30', '11');

INSERT INTO `mydb`.`users` (`u_id`, `u_login`, `u_password`, `u_name`, `u_lastname`, `u_email`, `u_position`) VALUES ('1', '1', '1', 'sergei', 'kalashynski','sergei.kalashynski@gmail.com', 'admin');
INSERT INTO `mydb`.`users` (`u_id`, `u_login`, `u_password`, `u_name`, `u_lastname`, `u_email`, `u_position`, `u_rating`, `u_isblock`) VALUES ('2', '2', '2', 'anrey', 'minich', 'andrey.minich@gmail.com', 'user', '100', '0');
INSERT INTO `mydb`.`users` (`u_id`, `u_login`, `u_password`, `u_name`, `u_lastname`, `u_email`, `u_position`, `u_rating`, `u_isblock`) VALUES ('3', '3', '3', 'Misha', 'Losich', 'misha.losich@gmail.com', 'user', '150', '0');
INSERT INTO `mydb`.`users` (`u_id`, `u_login`, `u_password`, `u_name`, `u_lastname`, `u_email`, `u_position`, `u_rating`, `u_isblock`) VALUES ('4', '4', '4', 'Mark', 'Zuckerberg', 'zuckerberg@gmail.com', 'user', '120', '1');

INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '1', '8');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '2', '5');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '3', '6');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '2', '7');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '4', '5');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '5', '8');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '6', '9');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '7', '9');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '8', '6');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '9', '5');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '10', '9');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '11', '9');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '12', '8');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '14', '9');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '15', '8');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '16', '9');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '17', '8');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '18', '9');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '6', '8');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '20', '5');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '21', '7');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '22', '7');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '23', '9');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '24', '8');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '25', '7');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '26', '9');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '27', '6');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('2', '28', '7');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('3', '29', '7');
INSERT INTO `mydb`.`mark` (`user_id`, `movie_id`, `mark`) VALUES ('4', '30', '6');

INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('2', '1', 'interesting, funny');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('2', '2', 'very funny');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('2', '4', 'Was not interesting enough');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('3', '5', 'I enjoyed it a lot. Worth watching');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('4', '6', 'Cried...');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('2', '7', 'Made me both laugh and think.');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('2', '10', 'Each episode is smth new!');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('3', '11', 'Stupid american, but dead funny!');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('2', '13', 'Not for everyone.');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('3', '14', 'Such a nice hero!');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('4', '15', 'First was really better.');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('2', '16', 'Best part I think');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('3', '17', 'Nicholas Cage is the best!');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('2', '6', 'Cannot believe Wolverine died...');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('4', '21', 'Such a nice family cartoon');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('3', '23', 'That guy is the strongest one!');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('4', '24', 'Made me wanna eat!!!');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('4', '27', 'I expected more');
INSERT INTO `mydb`.`comment` (`u_id`, `m_id`, `comment`) VALUES ('2', '28', 'Part 3.5??');


INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('1', 'Jennifer', 'Aniston');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('2', 'Courteney', 'Cox');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('3', 'David', 'Crane');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('4', 'Gary', 'Halvorson');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('5', 'Omar', 'Sy');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('6', 'Alexandre', 'Heboyan');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('7', 'Benoît', 'Philippon');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('8', 'Jérôme', 'Fansten');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('9', 'Aton', 'Soumache');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('10', 'Dimitri', 'Rassam');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('11', 'Bruno', 'Coulais');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('12', 'Izïa', 'Higelin');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('13', 'Michaël', 'Grégorio');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('14', 'Shafik', 'Ahmad');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('15', 'Benoît', 'Allemane');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('16', 'Shannon', 'Archie');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('17', 'Féodor', 'Atkine');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('18', 'Joshua J.', 'Ballard');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('19', 'Emmanuel', 'Curtil');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('20', 'Ronnie', 'Del Carmen');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('21', 'Pete', 'Docter');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('22', 'John', ' Lasseter');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('23', 'Michael', 'Giacchino');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('24', 'Jonas', 'Rivera');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('25', 'Mark', 'Nielsen');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('26', 'Richard', ' Kind');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('27', 'Phyllis', 'Smith');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('28', 'Diane', 'Lane');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('29', 'Bill', 'Hader');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('30', 'Amy', 'Poehler');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('31', 'Carlos', 'Alazraqui');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('32', 'Lewis ', 'Black');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('33', 'Mindy', 'Kaling');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('34', 'Jess', 'Harnell');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('35', 'Clark', 'Spencer');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('36', 'Alan', 'Tudyk');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('37', 'Jonathan Kimble', 'Simmons');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('38', 'Jason Kent ', 'Bateman');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('39', 'Jenny Sarah', 'Slate');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('40', 'Ginnifer Michelle', 'Goodwin');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('41', 'John Di', 'Maggio');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('42', 'Octavia', 'Spencer');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('43', 'Katie', 'Lowes');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('44', 'Kevin', 'Bright');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('45', 'Michael', 'Lembeck');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('46', 'Marta', 'Kauffman');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('47', 'Ted', 'Cohen');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('48', 'Michel', 'Noher');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('49', 'Nick', 'McLean');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('50', 'Richard', 'Hissong');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('51', 'Michael', 'Skloff');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('52', 'Allee', 'Willis');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('53', 'John', 'Shaffner');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('54', 'Adam', 'Short');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('55', 'Stephen', 'Prime');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('56', 'David', 'Helfand');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('57', 'Andy', 'Zall');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('58', 'Lisa', 'Kudrow');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('59', 'Matt', 'LeBlanc');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('60', 'Matthew', 'Perry');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('61', 'David', 'Schwimmer');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('62', 'James Michael', 'James');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('63', 'Christina', 'Pickles');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('64', 'Maggie', 'Wheeler');

INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('65', 'Billy', 'West');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('66', 'Katey', 'Sagal');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('67', 'Peter', 'Avansino');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('68', 'Bred', 'Haaland');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('69', 'Greg', 'Vantso');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('70', 'David', 'Koen');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('71', 'Claudia', 'Cutts');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('72', 'Jim', 'Parsons');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('73', 'Johnny', 'Galecki');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('74', 'Mark', 'Cenrovski');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('75', 'Antony', 'Rich');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('76', 'Chuck', 'Lory');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('77', 'Tobey', 'Maguire');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('78', 'Willem', 'Dafoe');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('79', 'Kirsten', 'Dunst');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('80', 'Sam', 'Rayme');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('81', 'Enbrice', 'Loraziskin');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('82', 'Nicolas', 'Cage');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('83', 'Eva', 'Mendes');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('84', 'Wes', 'Bentley');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('85', 'Mark Steven', 'Johnson');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('86', 'Gary', 'Foster');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('87', 'Michael', 'De Luco');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('88', 'Jim', 'Carrey');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('89', 'Renee', 'Zellweger');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('90', 'Bobby', 'Farrelle');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('91', 'Peter', 'Farrelle');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('92', 'Bredley', 'Thomas');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('93', 'Eddie', 'Murphy');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('94', 'Cliff', 'Curtes');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('95', 'Clark', 'Duke');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('96', 'Byron', 'Robbins');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('97', 'Allen', 'Shaba');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('98', 'Ryan', 'Gisling');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('99', 'Emma', 'Stone');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('100', 'Demien', 'Shazell');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('101', 'Fred', 'Berger');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('102', 'Ginnifer', 'Goodwin');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('103', 'Jason', 'Bateman');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('104', 'Idris', 'Elba');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('105', 'Byron', 'Hovard');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('106', 'Rich', 'Moor');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('107', 'Clark', 'Spenser');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('108', 'Owen', 'Wilson');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('109', 'David', 'Frenkel');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('110', 'Jill', 'Netter');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('111', 'Benedict', 'Cumberbatch');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('112', 'Tilda', 'Swinten');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('113', 'Scott', 'Dericson');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('114', 'Victoria', 'Alonso');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('115', 'Steven', 'Brusser');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('116', 'Seth', 'Roen');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('117', 'Kristen', 'Wiag');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('118', 'Jonah', 'Hill');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('119', 'Gred', 'Tirnan');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('120', 'Conrad', 'Vernon');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('121', 'Nina', 'Dobref');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('122', 'Paul', 'Wesley');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('123', 'Ian', 'Somerhalder');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('124', 'Chris', 'Grismer');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('125', 'July', 'Pleg');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('126', 'Matt', 'Damon');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('127', 'Jessica', 'Chastein');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('128', 'Riddley', 'Scott');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('129', 'Mark', 'Haffam');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('130', 'Michael', 'Sheffer');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('131', 'Tom', 'Hardy');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('132', 'Charlize', 'Theron');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('133', 'Nicholas', 'Hould');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('134', 'George', 'Miller');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('135', 'Bruce', 'Berman');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('136', 'John', 'Boyega');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('137', 'Daisy', 'Riddley');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('138', 'Oscar', 'Isaac');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('139', 'J.J.', 'Abrams');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('140', 'Byron', 'Berg');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('141', 'Chris', 'Pratt');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('142', 'Bryce Dallas', 'Howard');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('143', 'Nick', 'J. Robinson');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('144', 'Collim', 'Trevorrow');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('145', 'Frank', 'Marshal');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('146', 'Amy', 'Winehouse');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('147', 'Lauren', 'Gilbert');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('148', 'Asif', 'Kapadia');
INSERT INTO `mydb`.`persons` (`p_id`, `p_name`, `p_lastname`) VALUES ('149', 'James', 'Gay-Rees');

INSERT INTO `mydb`.`positions` (`p_id`, `p_type`) VALUES ('4', 'Actor');
INSERT INTO `mydb`.`positions` (`p_id`, `p_type`) VALUES ('5', 'Produser');
INSERT INTO `mydb`.`positions` (`p_id`, `p_type`) VALUES ('6', 'Screenwriter');
INSERT INTO `mydb`.`positions` (`p_id`, `p_type`) VALUES ('7', 'Director');
INSERT INTO `mydb`.`positions` (`p_id`, `p_type`) VALUES ('8', 'Composer');
INSERT INTO `mydb`.`positions` (`p_id`, `p_type`) VALUES ('9', 'Operator');
INSERT INTO `mydb`.`positions` (`p_id`, `p_type`) VALUES ('10', 'Artist');
INSERT INTO `mydb`.`positions` (`p_id`, `p_type`) VALUES ('11', 'Editor');

INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('1', '4', '4');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('2', '4', '4');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('3', '6', '4');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('4', '7', '4');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('3', '5', '4');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('5', '4', '3');


INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('6', '7', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('7', '7', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('8', '6', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('9', '5', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('10', '5', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('7', '6', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('11', '8', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('12', '4', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('13', '4', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('14', '4', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('15', '4', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('16', '4', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('17', '4', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('18', '4', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('19', '4', '3');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('21', '7', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('21', '6', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('20', '7', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('20', '6', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('22', '5', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('24', '5', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('25', '5', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('23', '8', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('26', '4', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('27', '4', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('28', '4', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('29', '4', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('30', '4', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('31', '4', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('32', '4', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('33', '4', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('34', '4', '1');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('15', '8', '5');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('4', '9', '6');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('56', '10', '7');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('45', '11', '8');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('16', '4', '9');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('17', '5', '10');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('18', '6', '11');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('19', '7', '12');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('20', '8', '13');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('21', '9', '14');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('22', '10', '15');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('23', '11', '16');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('24', '4', '17');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('25', '5', '18');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('26', '6', '19');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('27', '7', '20');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('28', '8', '21');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('29', '9', '22');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('3', '10', '23');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('32', '11', '24');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('33', '4', '25');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('34', '5', '26');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('35', '6', '27');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('36', '7', '28');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('37', '8', '29');
INSERT INTO `mydb`.`members` (`persons_p_id`, `positions_p_id`, `movies_m_id`) VALUES ('38', '9', '30');


INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('1', 'angola');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('2', 'argentina');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('3', 'aruba');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('4', 'australia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('5', 'austria');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('6', 'azerbaijan');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('7', 'bahamas');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('8', 'bahrain');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('9', 'belgium');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('10', 'benin');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('11', 'bolivia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('12', 'bonaire');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('13', 'bosnia-herzegovina');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('14', 'botswana');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('15', 'brazil');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('16', 'brunei');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('17', 'bulgaria');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('18', 'cambodia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('19', 'cameroon');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('20', 'canada');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('21', 'chile');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('22', 'china (fuzhou)');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('23', 'china (shanghai)');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('24', 'china (zhongshan)');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('25', 'colombia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('26', 'costa rica');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('27', 'croatia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('28', 'curacao');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('29', 'cyprus');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('30', 'czech republic');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('31', 'democratic republic of congo');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('32', 'denmark');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('33', 'east africa');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('34', 'ecuador');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('35', 'el salvador');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('36', 'el salvador-san miguel');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('37', 'estonia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('38', 'fiji');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('39', 'finland');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('40', 'france');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('41', 'gabon');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('42', 'germany');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('43', 'ghana');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('44', 'great britain');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('45', 'greece');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('46', 'guadaloupe');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('47', 'guatemala');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('48', 'honduras');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('49', 'honduras-tegucigalpa');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('50', 'hong kong');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('51', 'hungary');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('52', 'iceland');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('53', 'india');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('54', 'indonesia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('55', 'ireland');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('56', 'israel');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('57', 'italy');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('58', 'ivory coast');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('59', 'japan');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('60', 'kazakhstan');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('61', 'korea');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('62', 'kosovo');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('63', 'kuwait');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('64', 'latvia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('65', 'lesotho');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('66', 'lithuania');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('67', 'luxembourg');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('68', 'macau');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('69', 'macedonia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('70', 'malaysia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('71', 'martinique');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('72', 'mauritania');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('73', 'mexico');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('74', 'moldova');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('75', 'namibia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('76', 'netherlands');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('77', 'new zealand');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('78', 'nicaragua');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('79', 'nigeria');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('80', 'northern ireland');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('81', 'norway');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('82', 'panama');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('83', 'paraguay');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('84', 'peru');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('85', 'philippines');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('86', 'poland');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('87', 'portugal');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('88', 'puerto rico');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('89', 'qatar');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('90', 'republic of congo');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('91', 'reunion islands');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('92', 'romania');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('93', 'russia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('94', 'rwanda');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('95', 'saba');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('96', 'samoa');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('97', 'saudi arabia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('98', 'senegal');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('99', 'serbia & montenegro');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('100', 'singapore');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('101', 'slovak republic');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('102', 'slovenia');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('103', 'south africa');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('104', 'spain');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('105', 'st. eustatius');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('106', 'st. maarten');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('107', 'suriname');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('108', 'swaziland');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('109', 'sweden');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('110', 'switzerland');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('111', 'taiwan');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('112', 'tanzania');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('113', 'thailand');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('114', 'togo');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('115', 'turkey');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('116', 'uganda');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('117', 'ukraine');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('118', 'united arab emirates');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('119', 'united states');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('120', 'uruguay');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('121', 'venezuela');
INSERT INTO `mydb`.`countries` (`c_id`, `c_name`) VALUES ('122', 'vietnam');

INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('5', '3');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('2', '1');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('2', '2');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('2', '4');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('70', '5');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('69', '6');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('68', '7');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('67', '8');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('66', '9');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('65', '10');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('64', '11');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('63', '12');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('62', '13');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('61', '14');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('60', '15');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('59', '16');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('58', '17');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('57', '18');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('56', '19');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('55', '20');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('54', '21');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('53', '22');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('52', '23');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('51', '24');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('50', '25');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('49', '26');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('48', '27');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('47', '28');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('46', '29');
INSERT INTO `mydb`.`m2m_countries_movies` (`countries_c_id`, `movies_m_id`) VALUES ('45', '30');