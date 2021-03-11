
use sora;

DROP TABLE if exists record;
DROP TABLE if exists role;
DROP TABLE if exists player;
DROP TABLE if exists hero_equip;
DROP TABLE if exists hero_soldiers;
DROP TABLE if exists hero_actions;
DROP TABLE if exists hero;
DROP TABLE if exists soldier;
DROP TABLE if exists action;

DROP TABLE if exists equip;
DROP TABLE if exists equip_type;
DROP TABLE if exists hero_equips;
DROP TABLE if exists hero_equip_types;

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
	id bigint PRIMARY KEY,
	name VARCHAR(255),
	life integer,
	attack integer,
	intel integer,
	physic integer,
	magic integer,
	tech integer,
	rang integer,
	soldier_life_inc integer,
	soldier_attack_inc integer,
	soldier_physic_inc integer,
	soldier_magic_inc integer,
	skills VARCHAR(4096),
	hero_equip_id bigint,
	supported_equip_types VARCHAR(1024),
	default_soldier bigint,
	is_physic tinyint(1),
	is_woman tinyint(1),
	hero_type integer
);

create table hero_soldiers (hero_id bigint not null, soldiers_id bigint not null);

create table soldier (
	id BIGINT PRIMARY KEY,
	name VARCHAR(255),
	attack integer not null, 
	life integer not null, 
	magic integer not null, 
	physic integer not null, 
	soldier_type integer not null,
	skills VARCHAR(4096),
	is_physic tinyint(1),
	rang integer
);

create table action (
	id BIGINT PRIMARY KEY,
	coefficient double, 
	hit_time integer, 
	frequency integer,
	name varchar(255), 
	rang integer, 
	skills varchar(255), 
	is_physic tinyint(1),
	battle_type integer
);

 create table equip (
	 id BIGINT PRIMARY KEY,
	 name varchar(255), 
	 attack_skill integer not null, 
	 intel_skill integer not null, 
	 life_skill integer not null, 
	 magic_skill integer not null, 
	 physic_skill integer not null,
	 tech_skill integer not null, 
	 equip_type bigint, 
	 skills varchar(255),
	 owner bigint
 );
 
create table equip_type (
	id BIGINT PRIMARY KEY,
	attack integer not null, 
	intel integer not null, 
	life integer not null, 
	magic integer not null, 
	name varchar(255), 
	part varchar(64), 
	physic integer not null, 
	tech integer not null
);

create table hero_actions (hero_id bigint not null, actions_id bigint not null);

create table hero_equip_types (hero_id bigint not null, equip_types_id bigint not null);


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

INSERT INTO hero(id, name, 
life, attack, intel, physic, magic, tech, soldier_life_inc, soldier_attack_inc, soldier_physic_inc, soldier_magic_inc,
is_physic, is_woman, default_soldier, hero_type, rang, supported_equip_types,  skills) VALUES
(1,'帕希尔',3731,290,550,273,331,111,30,35,15,20,       0,1,1,7,2, '1,12,15,16,17,21,22,23', '1'),
(2,'托娃',4375,560,257,322,319,176,30,30,40,15,        1,1,3,5,1,   '5,6,12,15,18,19,20,21,22,23','2,4002'),
(5,'忍部火美子',3838,533,242,293,263,326,20,20,35,25,    1,1,6,7,2,  '4,7,12,15,18,19,20,21,22','5'),
(7,'希尔达',5372,548,247,357,207,106,20,30,40,10,    1,1,7,2,1,  '5,6,10,13,18,19,20,21,22,23,24','7,4004'),
(8,'沃尔纳',4311,572,233,288,241,122,30,35,20,15,    1,0,8,3,1,  '2,3,6,10,13,18,19,20,21,22','8,4004'),
(9,'降生之光',3308,262,563,245,395,96,25,40,15,20,    0,1,1,8,2,  '1,11,14,16,17,21,22,25','9,4002,4003'),
(10,'玛丽埃尔',3876,543,271,309,322,129,25,40,15,20,    1,1,9,10,1,  '1,8,9,10,11,13,14,18,19,20,21,22','10,4003'),
(11,'罗泽希尔',3497,321,412,278,348,85,20,10,35,35,    0,1,10,10,2,  '1,8,9,11,14,16,17,22,25','11,4004'),
(12,'克洛泰尔',3654,495,515,306,312,113,15,35,35,15,    0,0,11,8,1,  '1,8,9,10,11,13,14,16,17,21,22','12,4004'),
(16,'弗洛朗蒂亚',2813,260,591,211,295,80,40,15,35,20,    0,1,12,8,2,  '1,11,14,16,17,22,25','16,4004'),
(18,'树之贤者',3106,287,514,236,330,86,20,15,35,30,    0,0,2,10,2,  '1,9,11,14,16,17,22,25','18'),
(21,'罗莎莉娅',4841,578,271,263,308,125,20,40,15,25,    1,1,3,10,1,  '1,2,3,6,8,9,10,11,13,14,18,19,20,21,22','21,4003'),
(22,'黎恩',4538,585,283,320,289,231,15,25,30,30,    1,0,13,1,1,  '2,3,8,9,10,13,18,19,20,21,22','22'),
(28,'阿雷斯',4096,599,228,305,321,187,15,40,30,15,    1,0,14,5,1,  '5,6,12,15,18,19,20,21,22,23','28,4004'),
(30,'蒂德莉特',4365,265,567,318,325,152,15,35,15,35,    0,1,15,1,1,  '2,3,8,9,10,13,16,17,21,22,23','30,4002'),
(32,'布琳达',4922,590,233,346,217,172,15,35,35,15,    1,1,16,2,1,  '5,6,10,13,18,19,20,21,22,23,24,26','32'),
(33,'阿卡娅',3612,519,438,271,266,136,25,25,25,25,    1,1,17,3,1,  '2,3,6,10,13,21','33,4002,4003'),
(34,'迷之骑士',3762,295,559,321,286,127,20,35,20,25,    0,1,15,3,1,  '1,8,9,10,13,16,17,21,22,23','34'),
(35,'维拉久',4155,614,259,310,252,150,35,40,15,10,    1,0,18,4,1,  '5,12,15,18,19,20,26','35'),
(40,'浦饭幽助',3788,557,258,319,351,165,15,35,35,15,    1,0,11,10,1,  '8,9,11,12,14,15,18,19,20,21,22','40'),
(41,'飞影',3439,540,240,298,281,322,20,30,25,25,    1,0,19,7,2,  '4,7,12,15,18,19,20,21,22,23','41,4002'),
(46,'艾拉斯卓',3249,558,228,231,268,219,20,35,35,15,    1,1,20,6,2,  '4,7,12,15,18,19,20,21,22','46,4002'),
(47,'克拉蕾特',4100,607,216,298,325,182,10,25,35,30,   1,1,3,5,1,   '5,6,12,15,18,19,20,21,22','47'),
(51,'维拉',2844,312,545,228,303,80,40,15,25,35,   0,0,10,10,2,   '1,8,9,11,14,16,17,22,25','51'),
(52,'西格玛',3609,600,244,263,254,259,30,40,10,20,   1,0,21,7,2,   '4,7,12,15,18,19,20,21,22,23','52'),
(58,'兰迪乌斯',5032,563,247,295,265,132,20,35,35,10,    1,0,22,3,1,  '2,3,6,10,13,18,19,20,21,22','58'),
(59,'蕾加尔',3838,267,516,239,423,117,10,30,20,40,    0,1,1,8,2,  '1,11,14,16,17,21,22,25','59'),
(60,'兰芳特',4691,582,271,307,276,251,40,40,10,10,    1,0,8,1,1,  '2,3,8,9,10,13,18,19,20,21,22','60'),
(61,'妮丝蒂尔',4506,321,343,341,352,102,30,10,20,40,    0,1,16,9,1,   '1,8,9,11,12,14,15,16,17,21,22,23,24,28', '61'),
(62,'泽瑞达',3723,534,240,288,281,331,10,40,10,40,     1,1,5,7,2,   '4,7,12,15,18,19,20,21,22','62'),
(63,'古巨拉',5442,412,247,317,285,124,40,15,35,10,     1,0,4,4,1,  '8,12,15,22,23','63,3011,4002'),
(64,'雪露法妮尔',3347,307,537,228,402,99,20,20,25,35,   0,1,10,10,2,   '1,8,9,11,14,16,17,22,25','64'),
(66,'亚鲁特缪拉',4504,596,268,322,348,179,40,40,10,10,   1,0,16,5,1,   '5,6,12,15,18,19,20,21,22','66,4004'),
(68,'露娜',4683,483,325,331,400,146,10,40,10,40,   1,0,16,5,1,   '5,6,12,15,18,19,20,21,22','68'),
(69,'蒂亚莉丝',3619,308,481,304,348,125,10,10,40,40,    0,1,23,8,2,  '1,11,14,16,17,22,25','69,4002'),
(72,'波赞鲁',4096,287,407,288,377,88,40,10,10,40,   0,0,1,8,2,   '1,11,14,16,17,21,22,25','72'),
(74,'巴恩哈特',4723,546,265,380,281,143,35,35,15,15,   1,0,17,1,1,   '2,3,8,9,10,13,18,19,20,21,22','74,4004'),
(75,'利亚娜',4026,295,443,275,374,103,40,10,10,40,    0,1,10,8,2,  '1,11,14,16,17,22,25','75,4003'),
(76,'埃尔文',5006,578,235,340,270,130,15,35,35,15,    1,0,15,3,1,  '2,3,6,10,13,18,19,20,21,22','76,4003,4004'),
(78,'雷丁',5607,498,271,375,279,117,35,10,35,20,    1,0,26,1,1,  '8,9,10,13,18,19,20,21,22,23,24','78,4003'),
(81,'爱丽丝',3525,288,477,277,314,110,35,10,20,30,    0,1,23,8,2,  '1,11,14,16,17,22,25','81,4004'),
(91,'怀特·茜茜',3806,280,476,262,340,93,30,20,35,15,    0,1,12,10,2,  '1,11,14,16,17,22,25','91,4003'),
(92,'红月之王',3878,295,518,285,335,175,30,20,30,20,    0,0,3,10,1,  '1,9,11,14,16,17,22,25','92'),
(93,'迦游罗',2989,229,474,201,350,124,15,35,15,35,    0,1,1,7,2,  '1,4,12,15,16,17,21,22,25','93')
;


INSERT INTO soldier(id, name, life ,attack, physic,  magic, soldier_type, is_physic, rang, skills) VALUES
(1, '魔女', 583, 677, 297, 468, 8, 0, 2, '2001'),
(2, '魔晶术士', 723, 630, 375,328, 8, 0, 2, '2002'),
(3, '皇家狮鹫', 677,754, 344,390, 5, 1, 1, '2003'),
(4, '龙虾巨兽', 723,630, 437,282, 4, 1, 1, '2004'),
(5, '火焰骷髅弓手', 609,706, 359,342, 9, 1, 2, '2005'),
(6, '地精骑士', 614,723, 344,344, 7, 1, 2, '2006'),
(7, '水晶塑形者', 801,583, 375,375, 2, 1, 1, ''),
(8, '机械骑士', 723,723, 375,344, 3, 1, 1, '2008'),
(9, '高地勇士', 723,677, 375,344, 1, 1, 1, '2009'),
(10, '巫女', 614,537, 297,468, 10, 0, 2, '2010'),
(11, '狂热者', 617,677, 344,344, 10, 1, 1, '2011'),
(12, '森林祭祀', 677,537, 344,390, 10, 0, 2, '2012'),
(13, '钢翼勇士', 723,754, 375,375, 5, 1, 1, '2013'),
(14, '天使', 723,723, 375,437, 5, 1, 1, '2014'),
(15, '独角兽', 723,723, 375,375, 3, 0, 1, '2015'),
(16, '石像鬼', 723,754, 375,375, 5, 1, 1, '2016'),
(17, '钢鬃狼人', 836,706, 407,342, 9, 1, 1, '2017'),
(18, '蜥蜴刀客', 677,723, 375,328, 4, 1, 1, '2018'),
(19, '影侍', 614,723, 344,344, 7, 1, 2, '2019'),
(20, '妖精弓骑兵', 614,677, 344,390, 6, 1, 2, '2020'),
(21, '无面者', 614,723, 344,375, 7, 1, 2, '2021'),
(22, '皇家骑兵', 614,723, 344,375, 3, 1, 1, '2022'),
(23, '旋风游骑兵', 677,630, 375,328, 6, 1, 2, '2023'),
(24, '洗罪者', 801,583,375,375, 10, 1, 1, '2024'),
(25, '方阵列兵', 723,723,390,297, 2, 1, 1, '2025'),
(26, '假面女仆', 617,677,390,390, 1, 1, 1, '2026'),
(27, '树人守卫', 723,723,390,297, 2, 1, 1, '2027'),
(28, '龙骑兵', 723,723,375,328, 3, 1, 1, '2028'),
(29, '熔岩巨人', 879,583,437,328, 2, 1, 1, '2029'),
(30, '岩石巨人', 801,583,468,282, 2, 1, 1, '2030'),
(31, '天空射手', 614,677,297,390, 6, 1, 2, '2031'),
(32, '暗精灵射手', 537,677,297,437, 6, 1, 2, '2032'),
(33, '绯雾女侍', 677,630,375,437, 7, 1, 2, '2033'),
(34, '近卫步兵', 879,630,390,328, 1, 1, 1, '2034'),
(35, '姬武神', 677,723,375,344, 2, 1, 1, '2035'),
(36, '近卫骑兵', 723,677,390,297, 3, 1, 1, '2036'),
(37, '圣天马', 677,723,390,390, 5, 1, 1, '2037'),
(38, '龙裔战士', 723,723,390,375, 5, 1, 1, '2038'),
(39, '驱魔师', 677,630,375,344, 10, 0, 1, '2039'),
(40, '神官骑士', 583,583,328,473, 10, 0, 2, '2040'),
(41, '暗杀者', 677,677,344,344, 7, 1, 2, '2041'),
(42, '突击弩骑兵', 614,630,344,344, 6, 1, 2, '2042'),
(43, '火弩狙击手', 583,677,328,390, 6, 1, 2, '2043'),
(44, '大精灵', 583,677,328,390, 6, 1, 2, '2044'),
(45, '矮人冒险者', 583,630,297,344, 6, 1, 2, '2045'),
(46, '投石车', 537,630,297,344, 6, 1, 2, '2046'),
(47, '近卫枪兵', 723,677,390,297, 2, 1, 1, '2047'),
(48, '暗影百夫长', 801,677,437,282, 2, 1, 1, '2048'),
(49, '重戟百夫长', 801,630,390,297, 2, 1, 1, '2049'),
(50, '重装枪兵', 801,630,437,282, 2, 1, 1, '2050'),
(51, '重装步兵', 723,677,390,328, 1, 1, 1, '2051'),
(52, '狂战士', 677,723,375,328, 1, 1, 1, '2052'),
(53, '素体改造者', 677,723,375,328, 1, 1, 1, '2053'),
(54, '王女亲卫', 723,677,375,390, 1, 1, 1, '2054'),
(55, '食人巨魔', 801,723,344,328, 1, 1, 1, '2055'),
(56, '蛮族勇士', 801,723,344,328, 1, 1, 1, '2056'),
(57, '暗黑卫队', 879,630,390,328, 1, 1, 1, '2057'),
(58, '狂兽人', 723,677,375,297, 1, 1, 1, '2058'),
(59, '海怪', 723,677,390,328, 4, 1, 2, '2059'),
(60, '深洋海盗', 723,677,375,375, 4, 1, 2, '2060'),
(61, '人鱼统领', 723,677,390,328, 4, 1, 1, '2061'),
(62, '潮汐精灵', 723,630,390,375, 4, 1, 1, '2062'),
(63, '禁忌炼金师', 677,677,344,328, 8, 0, 2, '2063'),
(64, '男巫', 583,630,328,390, 8, 0, 2, '2064'),
(65, '吸血蝙蝠', 723,754,375,375, 5, 1, 1, '2065'),
(66, '地狱犬', 677,754,344,328, 3, 1, 1, '2066'),
(67, '骨犀', 614,754,344,328, 3, 1, 1, '2067'),
(68, '重装骑兵', 677,723,375,297, 3, 1, 1, '2068'),
(69, '近卫骑兵', 723,677,390,297, 3, 1, 1, '2069'),
(70, '天琴亲卫', 614,723,375,375, 3, 1, 1, '2070'),
(71, '圣殿骑士', 677,677,375,437, 3, 1, 1, '2071'),
(72, '魔蝎', 723,723,390,297, 3, 1, 1, '2072'),
(73, '重装骷髅', 755,706,391,342, 9, 1, 1, '2073'),
(74, '死灵骑士', 706,706,391,359, 9, 1, 1, '2074'),
(75, '蛛魔精灵', 755,657,391,391, 9, 1, 2, '2075'),
(76, '神官', 583,537,328,468, 10, 0, 2, '2076'),
(77, '武士', 677,677,375,375, 7, 1, 2, '2077')
;


INSERT INTO hero_soldiers(hero_id, soldiers_id) VALUES
(1, 1),(1, 2),(1, 29),(1,30),(1,31),(1,32),(1,33),(1,5),
(2, 3),(2, 25), (2, 28),(2, 34),(2, 35),(2, 69),(2, 37),(2, 38),
(5,6),
(7,7),(7,50),(7,30),(7,69),(7,71),(7,25),(7,22),
(8,8),(9,1),(10,9),(10,11),(10,24),(11,10),(12,11),(16,12),(18,2),
(21,3),(22,3), (22,9), (22,13),(28,13), (28,14),(30,15),(32,16),(33,17),(34,15),(35,18),(40,11),(41,19),
(46,20),(47,2),(52,21),
(58,22),(58,27),(58,47),(58,34),(58,69),(58,71),(58,57),(58,49),
(59,1),(60,8),(61,16),(61,65),
(62,5),(63,4),(63,17), (63,30), (64,10),(66,16),(68,3),(69,23),
(72,1),(72,2),
(74,17),(74,25),(74,29),(75,10),(76,15),(76,26),(78,26),(81,23),
(91,12),(91,10),(91,76),(91,46),(91,43),(91,49),(91,1),(91,50),
(92,3),
(93,1),(93,76),(93,33),(93,32),(93,10),(93,21),(93,77);

INSERT INTO action(id, name, coefficient, rang,  hit_time, frequency, is_physic ,battle_type, skills) VALUES 
(10001,'物理普攻',1,0,1500,40, 1,1,''),
(10002,'魔法普攻',1,0,1500,40, 0,1,''),
(1,'嗜梦',1.5,2,500,40, 0,1,'1601'),
(2,'绝命一击',1.4,2,300,40, 1,1,'1602'),
(3,'优势打击',1.5,1,1300,40, 1,1,'1603'),
(4,'自由之刃',1.4,1,1300,40, 1,1,'1604'),
(5,'闪电手里剑',0.38,5,0,0, 1,2,''),
(6,'威风冲阵',1.4,1,1300,40, 1,1,'1606'),
(7,'剑魂',1.8,1,1300,40, 1,1,''),
(8,'火球',1.5,2,1500,40, 0,1,'1608'),
(9,'心锥',0.3,3,0,0, 0,2,''),
(10,'雷击',1.5,2,1000,30, 0,1,'1610'),
(11,'暗镰',1.3,2,1500,40, 0,1,'1611'),
(12,'遗忘',0.3,3,0,0, 0,2,''),
(13,'湮灭影咒',0.1,6,0,0, 0,2,'1613'),
(14,'岚星斩',1.6,3,500,30, 0,1,'1614'),
(15,'闪速迅袭',1.2,1,750,40, 1,1,'1615'),
(16,'圣光之拥',1.5,2,1500,60, 0,1,'1616'),
(17,'圣言',1.5,2,2400,30, 0,1,'1617'),
(18,'冰冻',1.5,2,1500,30, 0,1,'1618')
;

INSERT INTO hero_actions(hero_id, actions_id) VALUES
(1, 1),(1, 8),(1, 9),(1, 10),(1, 11),(1, 12),(1, 13),
(9, 16),(9,10),(9, 8),(9,18),(8,15),
(62,2),(2,3), (5,5), (28,6), (76,7),(91,17),(92,1),(93,14);

INSERT INTO equip_type(id, name, life, attack, intel, physic, magic, tech, part) VALUES
(1,'Wand', 437, 0, 107, 0, 0, 0, 'Weapon'),
(2,'HeavySword', 437, 107, 0, 0, 0, 0, 'Weapon'),
(3,'LightSword', 0, 107, 0, 0, 0, 43, 'Weapon'),
(4,'Dagger', 0, 96, 0, 0, 0, 53, 'Weapon'),
(5,'Axe', 363, 118, 0, 0, 0, 0, 'Weapon'),
(6,'Spear', 583, 85, 0, 0, 0, 0, 'Weapon'),
(7,'Bow',0, 107, 0, 0, 0, 43, 'Weapon'),
(8,'PhysicHammer', 583, 85, 0, 0, 0, 0,'Weapon'),
(9,'MagicHammer', 583, 0, 85, 0, 0, 0, 'Weapon'),

(10,'HeavyArmor', 437, 0, 0, 65, 0, 0, 'Armor'),
(11,'LightArmor', 583, 0, 0, 54, 0, 0, 'Armor'),
(12,'LeatherArmor',  509, 0, 0, 59, 0, 0, 'Armor'),

(13,'HeavyHelm', 583, 0, 0, 0, 48, 0, 'Helmet'),
(14,'LightHelm', 364, 0, 0, 0, 65, 0, 'Helmet'),
(15,'LeatherHelm',437, 0, 0, 0, 59, 0, 'Helmet'),

(16,'Jewelry-Intel-Life', 509, 0, 75, 0, 0, 0, 'Jewelry'),
(17,'Jewelry-Intel-Magic', 0, 0, 75, 0, 43, 0,'Jewelry'),

(18,'Jewelry-Attack-Life', 509, 75, 0, 0, 0, 0, 'Jewelry'),
(19,'Jewelry-Attack-Physic', 0, 75, 0, 43, 0, 0, 'Jewelry'),
(20,'Jewelry-Attack-Tech', 0, 75, 0, 0, 0, 37, 'Jewelry'),

(21,'Jewelry-Attack-Intel',  0, 75, 75, 0, 0, 0 , 'Jewelry'),
(22,'Jewelry-2-Def', 0, 0, 0, 48, 43, 0 , 'Jewelry'),

(23,'Jewelry-Def-Life', 509, 0, 0, 48, 0, 0 , 'Jewelry'),
(24,'Jewelry-Life', 1102, 0, 0, 0, 0, 0 , 'Jewelry'),
(25,'MingXiang', 0, 0, 49, 0, 32, 0 , 'Jewelry'),
(26,'Jewelry-Attack-Magic', 0, 75, 0, 0, 43, 0 , 'Jewelry'),
(27,'Akaya', 0, 107, 107, 0, 0, 0 , 'Weapon'),
(28,'Lianjia', 414, 55, 0, 0, 0, 0 , 'Weapon')
;


INSERT INTO equip(id, name, life_skill, attack_skill, intel_skill, physic_skill, magic_skill, tech_skill, equip_type, owner, skills) VALUES
(1,'最后之服',0,0,0,10,0,0,  12,0,'1001'),
(2,'逸才权杖',5,0,0,0,0,0,   1,0,'1002'),
(3,'尼约德的羽冠',10,0,0,0,0,0,  15,0,'1003'),
(4,'星之耳坠',0,0,8,0,0,0,   16,0,'1004'),
(5,'拉格纳罗克',0,10,0,0,0,0,  5,0,'1005'),
(6,'神翼护胫',0,8,0,0,0,0,   19,0,'1006'),
(7,'极限魔弓',0,10,0,0,0,0,   7,0,'1007'),
(8,'鲜花礼帽',10,0,0,0,0,0,   15,0,''),
(9,'审判魔符',0,8,0,0,0,0,   19,0,'1009'),
(10,'铸剑者勋章',5,5,5,5,5,5,   22,0,'1010'),
(11,'温暖的回忆',5,0,0,0,0,0,   15,62,''),
(12,'屠龙勋章',0,8,0,0,0,0,   18,0,'1012'),
(13,'镜面铠甲',5,0,0,5,0,0,   10,0,''),
(14,'艾尼亚斯之盔',10,0,0,5,0,0,   13,0,''),
(15,'血之盟约',15,0,0,0,0,0,   24,0,''),
(16,'封印守护者',0,10,0,5,0,0,   2,0,''),
(17,'风王战甲',5,0,0,5,0,0,   10,0,''),
(18,'提尔之怒',0,0,0,10,0,0,   13,0,'1018'),
(19,'晨昏之星',0,5,5,0,0,0,   21,0,'1019'),
(20,'红色之月',5,0,10,0,0,0,   1,0,''),
(21,'天女羽衣',10,0,0,0,0,0,   11,0,''),
(22,'天女头饰',10,0,0,0,0,0,   14,0,''),
(23,'弥米尔的战锤',0,10,0,0,0,0,   8,0,''),
(24,'奥丁的宽檐帽',0,0,0,0,10,0,   14,0,''),
(25,'冥想指环',0,0,5,0,0,0,   25,0,'1025'),
(26,'巨人的抗争',0,0,0,10,0,0,  10,0,''),
(27,'永生者的馈赠',0,0,5,0,0,0,  9,0,''),
(28,'死神之衣',0,0,0,0,0,0,  11,0,''),
(29,'摄魂法帽',0,0,0,10,0,0,  14,0,''),
(30,'兰德维蒂玫瑰',0,0,8,0,0,0,  17,0,''),
(31,'斩风的骑士',0,10,0,0,0,0,  2,0,''),
(32,'均衡之刃',0,0,0,5,5,0,  3,0,''),
(33,'真红死神',0,10,0,0,0,0,  5,0,''),
(34,'王者之冠',5,0,0,0,5,0,  15,0,''),
(35,'红色小棉袜',0,5,0,0,0,0,  26,0,''),
(36,'月之回忆',5,0,0,5,0,0,  10,32,''),
(37,'圣灵之触',0,10,10,0,0,0,  27,33,''),
(38,'领袖之盔',10,0,0,0,0,0,  13,0,''),
(39,'风暴卡路里',0,0,15,0,0,0,  9,0,''),
(40,'冰锋战甲',5,0,0,0,5,0,  10,0,''),
(41,'强袭异铠',0,0,0,10,0,0,  10,35,''),
(42,'逆矢外壳',0,0,0,5,5,0,  12,0,''),
(43,'神行靴',0,0,0,8,0,0,  23,0,''),
(44,'风之翎羽',0,0,0,10,0,0,  15,46,''),
(45,'孤星腕轮',0,10,0,10,0,0, 18,0,''),
(46,'血纹魔铠',5,0,0,5,0,0, 10,0,''),
(47,'正义誓言',0,0,0,8,8,0, 8,0,''),
(48,'护目视镜',5,0,0,0,0,0, 15,52,''),
(49,'水晶锋刺',0,0,0,0,0,0, 4,0,'1049'),
(50,'古巨美的馈赠',5,0,0,0,0,0, 13,63,''),
(51,'奇迹之杖',0,0,0,0,0,0, 1,0,''),
(52,'卡昆西斯之冠',0,0,0,10,0,0, 14,64,''),
(53,'大地之冠',0,0,0,0,0,0, 13,0,''),
(54,'女神的左手',0,0,0,0,0,0, 1,0,''),
(55,'Q巨拉',0,0,0,0,0,0, 16,0,''),
(56,'流浪的骑士',0,10,0,0,0,0, 2,76,''),
(57,'尘世之光',10,0,0,0,0,0, 14,0,''),
(58,'米约尔尼尔',10,0,0,0,0,0, 8,0,''),
(59,'世界树的嫩枝',0,0,0,10,0,0, 6,0,'1059'),
(60,'连枷',10,0,0,0,0,0, 28,0,''),
(61,'卡奥斯的赐福',0,0,0,0,10,0, 11,72,''),
(62,'真十字架',0,0,0,0,0,0, 16,0,'1062'),
(63,'苍白之杖',0,0,0,0,0,0, 1,0,'1063')
;

