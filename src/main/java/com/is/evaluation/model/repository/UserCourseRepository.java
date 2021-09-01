package com.is.evaluation.model.repository;

import com.is.evaluation.model.entity.Enrollment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("UserCourseRepository")
public interface UserCourseRepository extends CrudRepository<Enrollment, Long>, QuerydslPredicateExecutor<Enrollment> {

    @Query(value = "select u from Enrollment u where u.user.id=?2 and u.course.id=?1 and u.state<>'DELETED'")
    Optional<Enrollment> getUserCourseByUserIdAndCourseId(long pCourseId, long pUserId);

    @Query(value = "select u from Enrollment u where u.user.id=?1 and u.state<>'DELETED'")
    List<Enrollment> getUserCourseByUserId(long pUserId);

    @Query(value = "select u from Enrollment u where u.id=?1 and u.state<>'DELETED'")
    Enrollment getUserCourseById(long pEnrollmentId);

}
