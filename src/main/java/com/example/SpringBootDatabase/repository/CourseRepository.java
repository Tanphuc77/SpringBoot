package com.example.SpringBootDatabase.repository;

import com.example.SpringBootDatabase.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    boolean existsCourseByCourseId(String courseId);
}
