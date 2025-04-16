create database personnel_recruitment;
use personnel_recruitment;
CREATE TABLE candidate
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)        NOT NULL,
    email       VARCHAR(100) UNIQUE NOT NULL,
    password    VARCHAR(255)        NOT NULL,
    phone       VARCHAR(20),
    experience  INT,
    gender      ENUM ('male', 'female', 'other'),
    status      ENUM ('active', 'inactive') DEFAULT 'active',
    description TEXT,
    dob         DATE
);

CREATE TABLE technology
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE recruitment_position
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    description   TEXT,
    minSalary     DECIMAL(10, 2),
    maxSalary     DECIMAL(10, 2),
    minExperience INT,
    createdDate   DATETIME DEFAULT CURRENT_TIMESTAMP,
    expiredDate   DATETIME
);

CREATE TABLE application
(
    id                     INT AUTO_INCREMENT PRIMARY KEY,
    candidateId            INT NOT NULL,
    recruitmentPositionId  INT NOT NULL,
    cvUrl                  VARCHAR(255),
    progress               ENUM ('pending', 'handling', 'interviewing', 'done') DEFAULT 'pending',
    interviewRequestDate   DATETIME,
    interviewRequestResult TEXT,
    interviewLink          VARCHAR(255),
    interviewTime          DATETIME,
    interviewResult        TEXT,
    interviewResultNote    TEXT,
    destroyAt              DATETIME,
    createAt               DATETIME                                             DEFAULT CURRENT_TIMESTAMP,
    updateAt               DATETIME                                             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    destroyReason          TEXT,
    FOREIGN KEY (candidateId) REFERENCES candidate (id),
    FOREIGN KEY (recruitmentPositionId) REFERENCES recruitment_position (id)
);

CREATE TABLE candidate_technology
(
    candidateId  INT,
    technologyId INT,
    PRIMARY KEY (candidateId, technologyId),
    FOREIGN KEY (candidateId) REFERENCES candidate (id),
    FOREIGN KEY (technologyId) REFERENCES technology (id)
);

CREATE TABLE recruitment_position_technology
(
    recruitmentPositionId INT,
    technologyId          INT,
    PRIMARY KEY (recruitmentPositionId, technologyId),
    FOREIGN KEY (recruitmentPositionId) REFERENCES recruitment_position (id),
    FOREIGN KEY (technologyId) REFERENCES technology (id)
);
