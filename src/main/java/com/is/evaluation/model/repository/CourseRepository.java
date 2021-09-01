package com.is.evaluation.model.repository;

import com.is.evaluation.model.entity.Course;
import com.is.evaluation.model.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("CourseRepository")
public interface CourseRepository extends CrudRepository<Course, Long>, QuerydslPredicateExecutor<Course> {
    @Query(value = "select count(u) from Course u where u.state=?1")
    Long getCountCoursesByState(String pState);

    @Query(value = "select u from Course u where u.state=?1")
    List<Course> getCoursesPageableByState(String pState, Pageable pPageable);

    @Query(value = "select d from Course d where d.id = ?1")
    Optional<Course> getCourseById(long pUserId);




}
