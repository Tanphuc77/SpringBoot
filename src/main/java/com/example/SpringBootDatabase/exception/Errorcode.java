package com.example.SpringBootDatabase.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum Errorcode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1005, "Invalid email", HttpStatus.BAD_REQUEST),
    NAME_INVALID(1006, "Name must be at least 3 characters", HttpStatus.BAD_REQUEST),
    USER_EMAIL_ALREADY_EXISTS(1007, "User email already exists", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FAILED(1008, "Authentication failed", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1009, "Uauthenticated", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(1010, "You do not have permission", HttpStatus.FORBIDDEN),
    USER_NOT_EXISTED(1011, "User not existed", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1012, "Role not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_REGISTER_COURSE(1013,"User already registered for this course",HttpStatus.BAD_REQUEST),
    USER_ROLE_REQUIRED(1014,"User Role Required",HttpStatus.BAD_REQUEST ),
    USER_NOT_FOUND(1015, "User not found", HttpStatus.NOT_FOUND),
    COURSE_NOT_FOUND(1016, "Course not found", HttpStatus.NOT_FOUND),
    STUDENT_NOT_FOUND_EMAIL_OR_NAME(1017, "No students found with email or name", HttpStatus.NOT_FOUND),
    ENROLLMENT_NOT_FOUND(1018, "Enrollment not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_ENROLLED(1019, "User is already enrolled in a course", HttpStatus.BAD_REQUEST),
    INVALID_DATE_OF_BIRTH(1020, "Invalid date of birth", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    Errorcode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}

