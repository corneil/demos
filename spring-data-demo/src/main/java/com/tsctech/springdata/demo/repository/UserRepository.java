package com.tsctech.springdata.demo.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tsctech.springdata.demo.data.UserInfo;

@Repository
public interface UserRepository extends QueryDslPredicateExecutor<UserInfo>, CrudRepository<UserInfo, String> {
	public UserInfo findByUserId(String userId);
}
