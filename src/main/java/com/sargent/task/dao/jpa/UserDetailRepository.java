package com.sargent.task.dao.jpa;

import com.sargent.task.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source
 */
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

}
