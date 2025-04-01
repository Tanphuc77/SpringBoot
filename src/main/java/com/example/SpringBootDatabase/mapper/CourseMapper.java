package com.example.SpringBootDatabase.mapper;

import com.example.SpringBootDatabase.dto.response.CourseResponse;
import com.example.SpringBootDatabase.dto.response.UserResponse;
import com.example.SpringBootDatabase.entity.Course;
import com.example.SpringBootDatabase.entity.StudentCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "studentCourses", target = "users", qualifiedByName = "mapUsers")
    CourseResponse toCourseResponse(Course course);

    @Named("mapUsers")
    default Set<UserResponse> mapUsers(List<StudentCourse> studentCourses) {
        if (studentCourses == null) return null;
        return studentCourses.stream()
                .map(studentCourse -> new UserResponse(
                        studentCourse.getUser().getUserId(),
                        studentCourse.getUser().getName(),
                        studentCourse.getUser().getDob(),
                        studentCourse.getUser().getEmail(),
                        studentCourse.getUser().getUsername(),
                        studentCourse.getEnrollmentDate(),
                        null,
                        null
                ))
                .collect(Collectors.toSet());
    }

}
