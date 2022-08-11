DROP TABLE IF EXISTS customer;

CREATE TABLE customer
(
    id         BIGINT NOT NULL,
    name       VARCHAR(100),
    department VARCHAR(255),
    salary     VARCHAR(255),
    time       date,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

DROP TABLE IF EXISTS employee_info;

CREATE TABLE employee_info
(
    id         BIGINT NOT NULL,
    name       VARCHAR(255),
    department VARCHAR(255),
    salary     VARCHAR(255),
    time       date,
    CONSTRAINT pk_employeeinfo PRIMARY KEY (id)
);

DROP TABLE IF EXISTS student_info;

CREATE TABLE student_info
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    email_address     VARCHAR(255),
    purchased_package VARCHAR(255),
    time              date,
    CONSTRAINT pk_studentinfo PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_info;

CREATE TABLE user_info
(
    id         BIGINT NOT NULL,
    name       VARCHAR(255),
    department VARCHAR(255),
    salary     BIGINT,
    time       date,
    CONSTRAINT pk_userinfo PRIMARY KEY (id)
);