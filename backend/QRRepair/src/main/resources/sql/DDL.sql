
DROP DATABASE IF EXISTS machine_room_repair_system;

CREATE DATABASE machine_room_repair_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

USE machine_room_repair_system;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS repair_record;
DROP TABLE IF EXISTS fault_image;
DROP TABLE IF EXISTS training_building;
DROP TABLE IF EXISTS lab;
DROP TABLE IF EXISTS equipment;

-- 用户表
CREATE TABLE user (
                      id VARCHAR(64) PRIMARY KEY,
                      username VARCHAR(50) UNIQUE NOT NULL COMMENT 'Username',
                      password VARCHAR(255) NOT NULL COMMENT 'Password',
                      phone_number CHAR(11) UNIQUE NOT NULL CHECK(phone_number REGEXP '^1[3456789]\\d{9}$') COMMENT 'user phone',
-- 	email VARCHAR(60) UNIQUE NOT NULL CHECK(email REGEXP ^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$) COMMENT 'User e-mail',
# 	role_id VARCHAR(64) NOT NULL
                      role TINYINT(1) UNSIGNED DEFAULT 2 NOT NULL -- 1 is admin ; 2 is common
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- 角色表
CREATE TABLE role (
                      id VARCHAR(64) PRIMARY KEY,
                      role_name VARCHAR(30) UNIQUE NOT NULL COMMENT 'Role name'
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- 报修记录表
CREATE TABLE repair_record (
                               id VARCHAR(64) PRIMARY KEY,
                               equipment_no int NOT NULL COMMENT 'Equipment No',
                               description VARCHAR(255) NOT NULL COMMENT '故障描述',
                               status TINYINT(1) UNSIGNED DEFAULT 1 COMMENT 'Repair status', -- 1是待维修,2是维修中,3是已维修
                               repair_time datetime DEFAULT NOW() COMMENT 'Repair time',
                               user_id VARCHAR(64) NOT NULL,
                               lab_id VARCHAR(64) NOT NULL COMMENT '所属机房',
                               training_building_id VARCHAR(64) NOT NULL COMMENT '所属实训楼'
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- 匿名报修记录表

-- 故障图片表
CREATE TABLE fault_image (
                             id VARCHAR(64) PRIMARY KEY,
                             image MEDIUMBLOB COMMENT '故障图片',
                             repair_record_id VARCHAR(64) NOT NULL
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- 实训楼表
CREATE TABLE training_building (
                                   id VARCHAR(64) PRIMARY KEY,
                                   building_name VARCHAR(50) UNIQUE NOT NULL COMMENT 'Training building name'
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- 机房表
CREATE TABLE lab (
                     id VARCHAR(64) PRIMARY KEY,
                     lab_name VARCHAR(50) UNIQUE NOT NULL COMMENT 'Lab name', -- unique
                     lab_alias VARCHAR(50) COMMENT 'Lab alias', -- 别名
                     available_equipment INT DEFAULT 0,
                     damage_equipment INT DEFAULT 0,
                     building_id VARCHAR(64) NOT NULL COMMENT 'Training building id'
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- 设备表
# CREATE TABLE equipment (
#                            equipment_no int NOT NULL COMMENT 'Equipment No',
#                            lab_id VARCHAR(64) NOT NULL COMMENT 'lab id',
#                            equipment_name VARCHAR(40) COMMENT 'Equipment name',
#                            is_available TINYINT(1) UNSIGNED DEFAULT 1 COMMENT 'Whether the equipment is available',-- 0 is not available ; 1 is available
#                            PRIMARY KEY (equipment_no,lab_id)
# )ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

-- ------------------------------------------

INSERT INTO user VALUES
                     ('1111','zs','123','19873916666',2),
                     ('1112','lisi','123','19873916667',2),
                     ('1113','admin','admin','19873916668',1);

INSERT INTO role VALUES
                     ('1','admin'),
                     ('2','user');

INSERT INTO repair_record VALUES
                              ('r1',6,'宕机了！',1,'2022-10-02 07:30:02','1111','l1','t1'),
                              ('r2',3,'黑屏了，主机也冒烟了！',1,'2023-12-02 07:30:02','1112','l1','t1'),
                              ('r3',1,'感觉主机要爆炸了！',2,'2023-12-02 09:30:02','1111','l1','t1'),
                              ('r4',5,'宕机了！',2,'2023-11-10 07:30:02','1111','l2','t1');

INSERT INTO fault_image VALUES
                            ('f1','https://clouds/image/sdfjdfl.png','r1'),
                            ('f2','https://clouds/image/sdf.png','r1'),
                            ('f3','https://clouds/image/sdwerfjdfl.png','r2'),
                            ('f4','https://clouds/image/agfgd.png','r2'),
                            ('f5','https://clouds/image/qwerer.png','r3'),
                            ('f6','https://clouds/image/fhfdh.png','r4');

INSERT INTO training_building VALUES
                                  ('t1','敏行楼'),
                                  ('t2','尚善楼'),
                                  ('t3','机械楼');

INSERT INTO lab VALUES
                    ('l1','10321','t1'),
                    ('l2','10221','t1');

INSERT INTO equipment VALUES
                          (1,'l1','Dell computer',1),
                          (2,'l1','Dell computer',1),
                          (3,'l1','Dell computer',1),
                          (4,'l1','Dell computer',1),
                          (5,'l1','Dell computer',1),
                          (6,'l1','Dell computer',1),
                          (7,'l1','Dell computer',1),
                          (1,'l2','macbook air',1),
                          (2,'l2','macbook air',1),
                          (3,'l2','macbook air',1),
                          (4,'l2','macbook air',1),
                          (5,'l2','macbook air',1);
