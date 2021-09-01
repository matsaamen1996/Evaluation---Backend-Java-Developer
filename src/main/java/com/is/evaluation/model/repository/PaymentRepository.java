package com.is.evaluation.model.repository;

import com.is.evaluation.model.entity.Course;
import com.is.evaluation.model.entity.Payment;
import com.is.evaluation.model.entity.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("PaymentRepository")
public interface PaymentRepository extends CrudRepository<Payment, Long>, QuerydslPredicateExecutor<Payment> {

    @Query(value = "select d from Payment d where d.course.id=?1 and d.state<>'DELETED'")
    List<Payment> getPaymentByCourseId(long pCourseId);

    @Query(value = "select d from Payment d where d.id=?1 and d.state<>'DELETED'")
    Payment getPaymentById(long pPaymentId);
}
