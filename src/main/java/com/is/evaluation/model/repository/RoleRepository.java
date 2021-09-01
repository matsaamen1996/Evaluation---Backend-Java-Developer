package com.is.evaluation.model.repository;

import com.is.evaluation.model.entity.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("RoleRepository")
public interface RoleRepository extends CrudRepository<Role, Long>, QuerydslPredicateExecutor<Role> {

    @Query(value = "select d from Role d where d.state = ?1 order by d.role")
    List<Role> getRolesByState(String pState);

    @Query(value = "select d from Role d inner join d.users u where u.id = ?1 order by d.role")
    List<Role> getUserRolesByUserId(long pUserId);

    @Query(value = "select d from Role d where d.id in ?1 order by d.role")
    List<Role> getRolesByIds(Long[] pRoleIds);

    @Query(value = "select d from Role d where d.role=?1")
    Role getRolesByName (String pName);

}
