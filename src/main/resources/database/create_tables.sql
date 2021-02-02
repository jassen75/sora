
use sora;

DROP TABLE if exists record;
DROP TABLE if exists role;
DROP TABLE if exists player;
DROP TABLE if exists hero;

CREATE TABLE IF NOT EXISTS player (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(64),
	server VARCHAR(64)
);


CREATE TABLE IF NOT EXISTS record (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	player1 bigint not null,
	player2 bigint not null,
	score1 integer,
	score2 integer,
	season_id bigint not null,
	record_group integer,
	stage integer,
	map integer,
	match_time timestamp not null,
	video_id bigint
);

CREATE TABLE IF NOT EXISTS season (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	number integer,
	match_type VARCHAR(64),
	status integer, 
	start_time timestamp
);

CREATE TABLE IF NOT EXISTS video (
	id bigint AUTO_INCREMENT PRIMARY KEY,
	video_type integer,
	info VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS role (
	id bigint AUTO_INCREMENT PRIMARY KEY,
	season_id bigint not null,
	player_id bigint not null,
	name VARCHAR(255)
);



CREATE TABLE IF NOT EXISTS hero (
	id bigint AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255),
	life integer,
	attack integer,
	intel integer,
	physic_def integer,
	magic_def integer,
	tech integer,
	soldier_life_inc integer,
	soldier_attack_inc integer,
	soldier_physic_def_inc integer,
	soldier_magic_def_inc integer,
	is_physic tinyint(1)

);

INSERT INTO player(name, server) VALUES
('朱老板','羁绊之地'),
('黑豆','羁绊之地'),
('兔兔和土豆','羁绊之地'),
('兔子白','羁绊之地'),
('长腿','羁绊之地'),
('神奇白','卡尔萨斯'),
('上官敏儿','米拉港' ),
('特工','羁绊之地'),
('琳雪','羁绊之地'),
('不是山谷','羁绊之地');

INSERT INTO hero(id, name, life, attack, intel, physic_def, magic_def, tech, soldier_life_inc, soldier_attack_inc,  soldier_physic_def_inc, soldier_magic_def_inc, is_physic) VALUES
(1,'帕希尔',3731,290,550,273,331,111,30,35,15,20,0),
(2,'托娃',4375,560,257,322,319,176,30,30,40,15,1);











