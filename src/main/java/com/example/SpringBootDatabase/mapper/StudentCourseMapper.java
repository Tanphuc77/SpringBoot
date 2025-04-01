package com.example.SpringBootDatabase.mapper;

import com.example.SpringBootDatabase.dto.request.StudentCourseRequest;
import com.example.SpringBootDatabase.dto.response.StudentCourseResponse;
import com.example.SpringBootDatabase.entity.StudentCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentCourseMapper {
    @Mapping(target = "user.userId", source = "userId")
    @Mapping(target = "course.courseId", source = "courseId")
    StudentCourse toStudentCourse (StudentCourseRequest request);

    @Mapping(target = "courseId", source = "course.courseId")
    @Mapping(target = "coursename", source = "course.name")
    @Mapping(target = "user", source = "user")
    StudentCourseResponse studentCourseResponse(StudentCourse studentCourse);
}
