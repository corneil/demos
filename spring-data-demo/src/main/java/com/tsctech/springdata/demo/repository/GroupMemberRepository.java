package com.tsctech.springdata.demo.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tsctech.springdata.demo.data.GroupMember;

@Repository
public interface GroupMemberRepository extends QueryDslPredicateExecutor<GroupMember>, CrudRepository<GroupMember, String> {
}
