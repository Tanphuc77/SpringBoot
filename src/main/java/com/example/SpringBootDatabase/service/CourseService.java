package com.example.SpringBootDatabase.service;

import com.example.SpringBootDatabase.dto.request.StudentCourseRequest;
import com.example.SpringBootDatabase.dto.response.CourseResponse;
import com.example.SpringBootDatabase.dto.response.StudentCourseResponse;
import com.example.SpringBootDatabase.entity.Course;
import com.example.SpringBootDatabase.entity.StudentCourse;
import com.example.SpringBootDatabase.exception.AppException;
import com.example.SpringBootDatabase.exception.Errorcode;
import com.example.SpringBootDatabase.mapper.CourseMapper;
import com.example.SpringBootDatabase.mapper.StudentCourseMapper;
import com.example.SpringBootDatabase.repository.CourseRepository;
import com.example.SpringBootDatabase.repository.StudentCourseRepository;
import com.example.SpringBootDatabase.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseService {

    CourseRepository courseRepository;
    CourseMapper courseMapper;
    UserRepository userRepository;
    StudentCourseMapper studentCourseMapper;
    StudentCourseRepository studentCourseRepository;

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toCourseResponse)
                .toList();
    }

    public CourseResponse getCourseById(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(Errorcode.COURSE_NOT_FOUND));

        return courseMapper.toCourseResponse(course);
    }

    public StudentCourseResponse createStudentCourse(StudentCourseRequest studentCourseRequest) {
        if (!courseRepository.existsCourseByCourseId(studentCourseRequest.getCourseId())) {
            throw new AppException(Errorcode.COURSE_NOT_FOUND);
        }

        if (!userRepository.existsUserByUserId(studentCourseRequest.getUserId())) {
            throw new AppException(Errorcode.USER_NOT_FOUND);
        }

        StudentCourse studentCourse = studentCourseMapper.toStudentCourse(studentCourseRequest);
        studentCourse = studentCourseRepository.save(studentCourse);
        studentCourse = studentCourseRepository.findByIdWithCourseAndUser(studentCourse.getId());

        return studentCourseMapper.studentCourseResponse(studentCourse);
    }

}
