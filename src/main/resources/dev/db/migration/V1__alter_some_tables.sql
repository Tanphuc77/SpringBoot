ALTER TABLE user
    MODIFY name VARCHAR(255) NOT NULL,
    MODIFY email VARCHAR(100) NOT NULL UNIQUE,
    MODIFY username VARCHAR(50) NOT NULL UNIQUE,
    MODIFY password VARCHAR(100) NOT NULL;

-- ALTER TABLE student_course
--     MODIFY enrollment_date DATE NOT NULL,
--     ADD CONSTRAINT fk_student_course_user FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
--     ADD CONSTRAINT fk_student_course_course FOREIGN KEY (course_id) REFERENCES course(course_id) ON DELETE CASCADE;
