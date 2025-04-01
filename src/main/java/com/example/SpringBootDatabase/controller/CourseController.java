package com.example.SpringBootDatabase.controller;

import com.example.SpringBootDatabase.dto.request.StudentCourseRequest;
import com.example.SpringBootDatabase.dto.response.ApiResponse;
import com.example.SpringBootDatabase.dto.response.CourseResponse;
import com.example.SpringBootDatabase.dto.response.StudentCourseResponse;
import com.example.SpringBootDatabase.service.CourseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseController {

    CourseService courseService;

    @GetMapping
    ApiResponse<List<CourseResponse>> getAllCourses() {
        ApiResponse<List<CourseResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.getAllCourses());
        return apiResponse;
    }

    @GetMapping("/{courseId}")
    ApiResponse<CourseResponse> getCourseById(@PathVariable String courseId){
        ApiResponse<CourseResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.getCourseById(courseId));
        return apiResponse;
    }

    @PostMapping
    ApiResponse<StudentCourseResponse> createStudentCourse(@RequestBody StudentCourseRequest studentCourseRequest){
        ApiResponse<StudentCourseResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(courseService.createStudentCourse(studentCourseRequest));
        return apiResponse;
    }
}
