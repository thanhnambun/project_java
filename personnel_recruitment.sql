# create database personnel_recruitment;
use personnel_recruitment;

-- Bảng thông tin ứng viên
CREATE TABLE candidate
(
    id          INT PRIMARY KEY auto_increment,
    name        VARCHAR(100)        NOT NULL,
    email       VARCHAR(100) UNIQUE NOT NULL,
    phone       VARCHAR(20) UNIQUE,
    experience  INT,
    gender      ENUM ('male', 'female', 'other'),
    status      ENUM ('active','block') DEFAULT 'active',
    description TEXT,
    dob         DATE
);

-- Bảng tài khoản liên kết 1-1 với candidate
CREATE TABLE account
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(100) UNIQUE NOT NULL,
    password     VARCHAR(255)        NOT NULL,
    candidate_id INT UNIQUE,
    role         ENUM ('admin', 'candidate'),
    FOREIGN KEY (candidate_id) REFERENCES candidate (id)
);

-- Bảng công nghệ
CREATE TABLE technology
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(100) NOT NULL unique,
    status ENUM ('active','delete')
);

-- Bảng vị trí tuyển dụng
CREATE TABLE recruitment_position
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    description   TEXT,
    minSalary     DECIMAL(10, 2),
    maxSalary     DECIMAL(10, 2),
    minExperience INT,
    createdDate   DATETIME DEFAULT CURRENT_TIMESTAMP,
    expiredDate   DATETIME,
    status        ENUM ('active','delete')
);

-- Bảng đơn ứng tuyển
CREATE TABLE application
(
    id                     INT AUTO_INCREMENT PRIMARY KEY,
    candidateId            INT NOT NULL,
    recruitmentPositionId  INT NOT NULL,
    cvUrl                  VARCHAR(255),
    progress               ENUM ('pending','handling','interviewing','done') DEFAULT 'pending',
    interviewRequestDate   DATETIME,
    interviewRequestResult TEXT,
    interviewLink          VARCHAR(255),
    interviewTime          DATETIME,
    interviewResult        enum ('disqualified,passed,cancel'),
    interviewResultNote    TEXT,
    destroyAt              DATETIME,
    createAt               DATETIME                                          DEFAULT CURRENT_TIMESTAMP,
    updateAt               DATETIME                                          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    destroyReason          TEXT,
    FOREIGN KEY (candidateId) REFERENCES candidate (id),
    FOREIGN KEY (recruitmentPositionId) REFERENCES recruitment_position (id)
);

-- Bảng nhiều-nhiều ứng viên - công nghệ
CREATE TABLE candidate_technology
(
    candidateId  INT,
    technologyId INT,
    PRIMARY KEY (candidateId, technologyId),
    FOREIGN KEY (candidateId) REFERENCES candidate (id),
    FOREIGN KEY (technologyId) REFERENCES technology (id)
);

-- Bảng nhiều-nhiều vị trí tuyển dụng - công nghệ
CREATE TABLE recruitment_position_technology
(
    recruitmentPositionId INT,
    technologyId          INT,
    PRIMARY KEY (recruitmentPositionId, technologyId),
    FOREIGN KEY (recruitmentPositionId) REFERENCES recruitment_position (id),
    FOREIGN KEY (technologyId) REFERENCES technology (id)
);
INSERT INTO candidate (id, name, email, phone, experience, gender, status, description, dob)
VALUES (1, 'Nguyễn Văn A', 'vana@gmail.com', '0901234567', 3, 'male', 'active', 'Ứng viên Java backend', '1997-03-21'),
       (2, 'Trần Thị B', 'tranb@gmail.com', '0912345678', 2, 'female', 'active', 'Front-end React', '1995-07-11'),
       (3, 'Lê Văn C', 'levanc@gmail.com', '0987654321', 0, 'male', 'active', 'Sinh viên mới ra trường', '2001-05-05'),
       (4, 'Admin', 'admin@recruit.com', '0900000000', 10, 'male', 'active', 'Tài khoản quản trị hệ thống',
        '1980-01-01');
INSERT INTO account (username, password, candidate_id, role)
VALUES ('vana@gmail.com', '123456', 1, 'candidate'),
       ('tranb@gmail.com', '123456', 2, 'candidate'),
       ('levanc@gmail.com', '123456', 3, 'candidate'),
       ('admin@recruit.com', 'admin123', 4, 'admin');
INSERT INTO technology (name, status)
VALUES ('Java', 'active'),
       ('Python', 'active'),
       ('ReactJS', 'active'),
       ('MySQL', 'active'),
       ('Docker', 'active'),
       ('Angular', 'delete');
INSERT INTO recruitment_position (name, description, minSalary, maxSalary, minExperience, expiredDate, status)
VALUES ('Back-end Developer', 'Phát triển API sử dụng Java Spring Boot', 800, 1500, 2, '2025-12-31', 'active'),
       ('Front-end Developer', 'Xây dựng giao diện người dùng với ReactJS', 700, 1200, 1, '2025-11-30', 'active'),
       ('Data Analyst', 'Phân tích dữ liệu khách hàng và hệ thống', 900, 1400, 3, '2025-10-01', 'active');
INSERT INTO application (candidateId, recruitmentPositionId, cvUrl, progress)
VALUES (1, 1, 'https://cv.com/van-a.pdf', 'pending'),
       (2, 2, 'https://cv.com/tran-b.pdf', 'handling'),
       (3, 1, 'https://cv.com/le-c.pdf', 'interviewing');
INSERT INTO candidate_technology (candidateId, technologyId)
VALUES (1, 1), -- Java
       (1, 4), -- MySQL
       (2, 3), -- ReactJS
       (2, 4), -- MySQL
       (3, 2); -- Python
INSERT INTO recruitment_position_technology (recruitmentPositionId, technologyId)
VALUES (1, 1), -- Java
       (1, 4), -- MySQL
       (2, 3), -- ReactJS
       (2, 4), -- MySQL
       (3, 2); -- Python


delimiter //
-- login
CREATE PROCEDURE check_account(
    IN username_in VARCHAR(100),
    IN password_in VARCHAR(255)
)
BEGIN
    SELECT *
    FROM account
    WHERE username = username_in
      AND password = password_in;
END;

-- register
CREATE PROCEDURE register_candidate(
    IN name_in VARCHAR(100),
    IN email_in VARCHAR(100),
    IN password_in VARCHAR(255),
    IN phone_in VARCHAR(20),
    IN experience_in INT,
    IN gender_in varchar(100),
    IN description_in TEXT,
    IN dob_in DATE
)
BEGIN
    DECLARE new_candidate_id INT;
    INSERT INTO candidate(name, email, phone, experience, gender, status, description, dob)
    VALUES (name_in, email_in, phone_in, experience_in, gender_in, 'active', description_in, dob_in);
    SET new_candidate_id = LAST_INSERT_ID();
    INSERT INTO account(username, password, candidate_id, role)
    VALUES (email_in, password_in, new_candidate_id, 'candidate');
END;

-- technology

create procedure get_total_technology_page()
begin
    SELECT CEIL(COUNT(*) / 5) AS total_pages FROM technology;
end;

create procedure find_all_technology(page int, page_size int)
begin
    declare offset_technology int;
    set offset_technology = (page - 1) * page_size;
    select *
    from technology
    where status = 'active'
    limit page_size offset offset_technology;
end;

CREATE PROCEDURE save_technology(
    IN name_in VARCHAR(100)
)
BEGIN
    DECLARE name_check INT DEFAULT 0;

    SELECT COUNT(*)
    INTO name_check
    FROM technology
    WHERE name LIKE CONCAT('%', name_in, '%');

    IF name_check > 0 THEN
        UPDATE technology
        SET status = 'active'
        WHERE name LIKE CONCAT('%', name_in, '%');
    ELSE
        INSERT INTO technology (name, status)
        VALUES (name_in, 'active');
    END IF;
END;


create procedure search_technology_by_id(id_in int)
begin
    select * from technology where id = id_in;
end;
create procedure search_technology_by_name(name_in varchar(100))
begin
    select * from technology where name LIKE CONCAT('%', name_in, '%');
end;

create procedure delete_technology(id_in int)
begin
    declare check_category1 int;
    declare check_category2 int;
    select count(technologyId) into check_category1 from candidate_technology where technologyId = id_in;
    select count(technologyId) into check_category2 from recruitment_position_technology where technologyId = id_in;
    if check_category1 > 0 && check_category2 > 0 then
        update technology
        SET status ='delete'
        where id = id_in;
    else
        delete from technology where id = id_in;
    end if;
end;
create procedure update_technology(
    id_in int,
    name_in varchar(100),
    status_in varchar(100)
)
begin
    update technology
    set name   = name_in,
        status = status_in
    where id = id_in;
end;
create procedure is_exit_name(name_in varchar(100))
begin
    select count(id) from technology where name = name_in;
end ;
-- cadidate
-- admin
create procedure check_isExit_email_candidate(email_in varchar(100))
begin
    select count(id) from candidate where email like email_in;
end;
create procedure findById_candidate(id_in int)
begin
    select * from candidate where id = id_in;
end //
CREATE PROCEDURE get_total_candidate_pages()
BEGIN
    SELECT CEIL(COUNT(*) / 5) AS total_pages FROM candidate;
END;
CREATE PROCEDURE find_all_candidate(page INT,page_size int)
BEGIN
    DECLARE offset_candidate INT;
    SET offset_candidate = (page - 1) * page_size;

    SELECT *
    FROM candidate c
             left join account a on c.id = a.candidate_id
    where c.status = 'active'
      and a.role = 'candidate'
    LIMIT page_size OFFSET offset_candidate;
END;

CREATE PROCEDURE search_candidate_by_name(keyword VARCHAR(100))
BEGIN
    SELECT * FROM candidate WHERE name LIKE CONCAT('%', keyword, '%');
END;

CREATE PROCEDURE filter_candidate_by_experience(min_exp INT)
BEGIN
    SELECT * FROM candidate WHERE experience >= min_exp;
END;

CREATE PROCEDURE filter_candidate_by_age(min_age INT, IN max_age INT)
BEGIN
    SELECT *
    FROM candidate
    WHERE TIMESTAMPDIFF(YEAR, dob, CURDATE()) BETWEEN min_age AND max_age;
END;

CREATE PROCEDURE filter_candidate_by_gender(gender_in ENUM ('male', 'female', 'other'))
BEGIN
    SELECT * FROM candidate WHERE gender = gender_in;
END;

CREATE PROCEDURE change_password(
    IN id_in INT,
    IN new_password VARCHAR(255)
)
BEGIN
    UPDATE account
    SET password = new_password
    WHERE id = id_in;
END;
create procedure is_exit_phone(phone_in int)
begin
    select count(id) from candidate where phone = phone_in;
end //
-- recruitment position
create procedure find_recruitment_position_by_id(id_id int)
begin
    select * from recruitment_position where id = id_id;
end;
CREATE PROCEDURE save_recruitment_position(
    name_in VARCHAR(100),
    description_in TEXT,
    min_salary DECIMAL(10, 2),
    max_salary DECIMAL(10, 2),
    min_exp INT,
    expired DATETIME
)
BEGIN
    INSERT INTO recruitment_position(name, description, minSalary, maxSalary, minExperience, createdDate, expiredDate,
                                     status)
    VALUES (name_in, description_in, min_salary, max_salary, min_exp, now(), expired, 'active');
END;
create procedure update_recruitment_position(
    id_in int,
    name_in VARCHAR(100),
    description_in TEXT,
    min_salary_in DECIMAL(10, 2),
    max_salary_in DECIMAL(10, 2),
    min_exp_in INT,
    expired_in DATETIME,
    status_in varchar(100)
)
begin
    update recruitment_position
    set name          = name_in,
        description   = description_in,
        maxSalary     = max_salary_in,
        minSalary     = min_salary_in,
        minExperience =min_exp_in,
        expiredDate   =expired_in,
        status        = status_in
    where id = id_in;
end;
CREATE PROCEDURE get_total_recruitment_position_pages()
BEGIN
    SELECT CEIL(COUNT(*) / 5) AS total_pages FROM recruitment_position;
END;
CREATE PROCEDURE find_all_recruitment_position(page INT,page_size int)
BEGIN
    DECLARE offset_recruitment_position INT;
    SET offset_recruitment_position = (page - 1) * page_size;

    SELECT *
    FROM recruitment_position
    where status = 'active'
    LIMIT page_size OFFSET offset_recruitment_position;
END;
create procedure delete_recruitment_position(id_in int)
begin
    declare check1 int;
    select count(recruitmentPositionId)
    into check1
    from recruitment_position_technology
    where recruitmentPositionId = id_in;
    if check1 > 0 then
        update recruitment_position
        set status = 'delete'
        where id = id_in;
    else
        delete from recruitment_position where id = id_in;
    end if;
end;
-- application
create procedure find_application_by_id(id_in int)
begin
    select * from application where id = id_in;
end;
CREATE PROCEDURE get_total_application_pages()
BEGIN
    SELECT CEIL(COUNT(*) / 5) AS total_pages FROM application;
END;
CREATE PROCEDURE find_all_application(page INT,page_size int)
BEGIN
    DECLARE offset_application INT;
    SET offset_application = (page - 1) * page_size;

    SELECT *
    FROM application
    LIMIT page_size OFFSET offset_application;
END;
CREATE PROCEDURE filter_application_progress(
    progress_in varchar(100)
)
BEGIN
    SELECT *
    FROM application
    WHERE destroyAt IS NULL
      AND progress = progress_in;

END;
CREATE PROCEDURE filter_application_result(
    result_in varchar(100)
)
BEGIN
    SELECT *
    FROM application
    WHERE destroyAt IS NULL
      AND interviewResult =result_in;

END;

create procedure save_application(
    candidateId_in int,
    recruitmentPositionId_in int,
    cvUrl_in varchar(100)
)
begin
    insert into application (candidateId, recruitmentPositionId, cvUrl)
        value (candidateId_in, recruitmentPositionId_in, cvUrl_in);
end;
CREATE PROCEDURE cancel_application(app_id INT, reason TEXT)
BEGIN
    UPDATE application
    SET destroyAt     = NOW(),
        destroyReason = reason
    WHERE id = app_id;
END;

CREATE PROCEDURE view_application_detail(app_id INT)
BEGIN
    UPDATE application
    SET progress = 'handling'
    WHERE id = app_id
      AND progress = 'pending';

    SELECT * FROM application WHERE id = app_id;
END;
CREATE PROCEDURE view_application_detail_candidate(app_id INT)
BEGIN
    SELECT * FROM application WHERE id = app_id;
END;
CREATE PROCEDURE move_to_interviewing(
    app_id INT,
    request_date DATETIME,
    link VARCHAR(255),
    time DATETIME
)
BEGIN
    UPDATE application
    SET interviewRequestDate = request_date,
        interviewLink        = link,
        interviewTime        = time,
        progress             = 'interviewing'
    WHERE id = app_id;
END;
CREATE PROCEDURE update_interview_result(
    app_id INT,
    result_note TEXT,
    result_status varchar(100)
)
BEGIN
    UPDATE application
    SET interviewResultNote = result_note,
        interviewResult     = result_status,
        progress            = 'done'
    WHERE id = app_id;
END;
CREATE PROCEDURE update_interview_request_result(
    IN appId INT,
    IN requestResult TEXT
)
BEGIN
    UPDATE application
    SET interviewRequestResult = requestResult,
        updateAt               = CURRENT_TIMESTAMP
    WHERE id = appId;
END;
create procedure find_application_by_candidate_id(candidate_id_in int)
begin
    select * from application where candidateId = candidate_id_in;
end;
create procedure find_by_candidate_and_position(candidate_id_in int, position_id_in int)
begin
    select *
    from application
    where candidateId = candidate_id_in
      and recruitmentPositionId = position_id_in
      and interviewResult = 'disqualified';
end;
create procedure delete_application_candidate(candidate_id int)
    begin
        update application
            set interviewResult ='cancel'
            and destroyAt = now()
        where candidateId =candidate_id ;
    end //
-- recruitment_position_technology
create procedure save_recruitment_position_technology(
    technology_id_in int,
    recruitment_position_id_in int
)
begin
    insert into recruitment_position_technology(recruitmentPositionId, technologyId)
        value (recruitment_position_id_in, technology_id_in);
end;
create procedure find_technology_by_recruitment_position_id(position_id int)
begin
    select technologyId from recruitment_position_technology where recruitmentPositionId = position_id;
end;
-- candidateTechnology
create procedure save_candiadte_technology(
    technology_id_in int,
    candidate_id_in int
)
begin
    insert into candidate_technology(candidateId, technologyId)
        value (candidate_id_in, technology_id_in);
end;
create procedure find_technology_by_candidate_id(candidate_id_in int)
begin
    select technologyId from candidate_technology where candidateId = candidate_id_in;
end;
-- user

CREATE PROCEDURE update_candidate_profile(
    id_in INT,
    name_in VARCHAR(100),
    phone_in VARCHAR(20),
    experience_in INT,
    gender_in varchar(100),
    description_in TEXT,
    dob_in DATE
)
BEGIN
    UPDATE candidate
    SET name        = name_in,
        phone       = phone_in,
        experience  = experience_in,
        gender      = gender_in,
        description = description_in,
        dob         = dob_in
    WHERE id = id_in;
END;

CREATE PROCEDURE change_password_user(
    id_in INT,
    new_password VARCHAR(255),
    password_in VARCHAR(255)
)
BEGIN
    UPDATE account
    SET password = new_password
    WHERE id = id_in
      and password = password_in;
END;

create procedure find_account_by_id_candidate(candidate_id_in INT)
begin
    select * from account where candidate_id = candidate_id_in;
end;

CREATE PROCEDURE find_applications_by_candidate(candidate_id INT)
BEGIN
    SELECT *
    FROM application
    WHERE candidateId = candidate_id
      AND destroyAt IS NULL;
END;
// delimiter ;
