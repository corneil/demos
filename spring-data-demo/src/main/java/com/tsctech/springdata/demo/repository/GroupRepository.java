package com.tsctech.springdata.demo.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tsctech.springdata.demo.data.GroupInfo;

@Repository
public interface GroupRepository extends QueryDslPredicateExecutor<GroupInfo>, CrudRepository<GroupInfo, String> {
	public GroupInfo findByGroupName(String name);
}
