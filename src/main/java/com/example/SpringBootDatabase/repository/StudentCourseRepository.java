package com.example.SpringBootDatabase.repository;

import com.example.SpringBootDatabase.dto.response.StudentCourseResponse;
import com.example.SpringBootDatabase.entity.StudentCourse;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, String> {

    @Query("SELECT sc FROM StudentCourse sc JOIN FETCH sc.course JOIN FETCH sc.user WHERE sc.id = :id")
    StudentCourse findByIdWithCourseAndUser(@Param("id") Long id);



}
