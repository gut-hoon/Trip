-- 기존 스키마 삭제
DROP SCHEMA IF EXISTS ssafytrip;

-- 스키마 생성
CREATE SCHEMA IF NOT EXISTS `ssafytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `ssafytrip`;

-- users 테이블
CREATE TABLE users (
  no INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(45) NOT NULL,
  password VARCHAR(200) NOT NULL,
  name VARCHAR(45) NOT NULL,
  role VARCHAR(10) NOT NULL,
  refresh VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (no),
  UNIQUE INDEX email_UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- article 테이블
CREATE TABLE article (
  no INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(45) NOT NULL,
  users_no INT NOT NULL,
  content VARCHAR(500) NOT NULL,
  reg_date DATE NOT NULL,
  view_cnt INT NOT NULL DEFAULT 0,
  is_article TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (no),
  INDEX fk_article_users_idx (users_no),
  CONSTRAINT fk_article_users
    FOREIGN KEY (users_no) REFERENCES users(no)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- comment 테이블
CREATE TABLE IF NOT EXISTS `ssafytrip`.`comment` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(100) NOT NULL,
  `users_no` INT NOT NULL,
  `article_no` INT NOT NULL,
  `parent_no` INT NULL,
  `reg_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`no`),
  UNIQUE INDEX `no_UNIQUE` (`no` ASC) VISIBLE,
  INDEX `fk_comment_users_idx` (`users_no` ASC) VISIBLE,
  INDEX `fk_comment_article1_idx` (`article_no` ASC) VISIBLE,
  CONSTRAINT `fk_comment_article`
    FOREIGN KEY (`article_no`)
    REFERENCES `ssafytrip`.`article` (`no`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_comment_users`
    FOREIGN KEY (`users_no`)
    REFERENCES `ssafytrip`.`users` (`no`)
    ON DELETE CASCADE
) ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb3;


-- hotplaces 테이블
CREATE TABLE hotplaces (
  no INT NOT NULL AUTO_INCREMENT,
  users_no INT DEFAULT NULL,
  name VARCHAR(50) NOT NULL,
  address VARCHAR(200) NOT NULL,
  date DATE NOT NULL,
  content_type_id INT DEFAULT NULL,
  `desc` VARCHAR(200) DEFAULT NULL,
  img VARCHAR(100) DEFAULT NULL,
  reg_date DATE NOT NULL,
  PRIMARY KEY (no),
  INDEX fk_hotplaces_users1_idx (users_no),
  CONSTRAINT fk_hotplaces_users1
    FOREIGN KEY (users_no) REFERENCES users(no)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- plan 테이블
CREATE TABLE plan (
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  users_no INT NOT NULL,
  `desc` VARCHAR(2000) DEFAULT NULL,
  expense VARCHAR(100) DEFAULT '0',
  start_date DATE DEFAULT NULL,
  end_date DATE DEFAULT NULL,
  share TINYINT DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE INDEX id_UNIQUE (id),
  INDEX fk_plan_users_idx (users_no),
  CONSTRAINT fk_plan_users
    FOREIGN KEY (users_no) REFERENCES users(no)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- plandetail 테이블
CREATE TABLE plandetail (
  place_no INT NOT NULL,
  seq INT NOT NULL,
  plan_id INT NOT NULL,
  place_url VARCHAR(200) DEFAULT NULL,
  place_name VARCHAR(45) DEFAULT NULL,
  place_address VARCHAR(200) DEFAULT NULL,
  INDEX fk_attractions_has_plan_attractions1_idx (place_no),
  INDEX fk_plandetail_plan1_idx (plan_id),
  CONSTRAINT fk_plandetail_plan1
    FOREIGN KEY (plan_id) REFERENCES plan(id)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

