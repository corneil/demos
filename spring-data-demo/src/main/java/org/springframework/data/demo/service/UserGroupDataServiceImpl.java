package org.springframework.data.demo.service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.demo.data.QGroupMember.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.demo.data.GroupInfo;
import org.springframework.data.demo.data.GroupMember;
import org.springframework.data.demo.data.UserInfo;
import org.springframework.data.demo.repository.GroupMemberRepository;
import org.springframework.data.demo.repository.GroupRepository;
import org.springframework.data.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserGroupDataServiceImpl implements UserGroupDataService {
	@Autowired
	protected GroupRepository groupRepository;

	@Autowired
	protected GroupMemberRepository memberRepository;

	@Autowired
	protected UserRepository userRepository;

	@Override
	public void deleteAllData() {
		memberRepository.deleteAll();
		groupRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Override
	public void deleteGroupInfo(GroupInfo groupInfo) {
		groupRepository.delete(groupInfo);
	}

	@Override
	public void deleteGroupMember(GroupMember groupMember) {
		memberRepository.delete(groupMember);
	}

	@Override
	public void deleteUserInfo(UserInfo userInfo) {
		userRepository.delete(userInfo);
	}

	@Override
	public GroupInfo findGroup(String name) {
		return groupRepository.findByGroupName(name);
	}

	@Override
	public UserInfo findUser(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public List<UserInfo> listActiveUsersInGroup(String groupName) {
		List<UserInfo> userList = new ArrayList<UserInfo>();
		Iterable<GroupMember> members = memberRepository.findAll(groupMember.memberOfgroup.groupName.eq(groupName).and(groupMember.enabled.eq(Boolean.TRUE)),
				groupMember.member.userId.desc());
		for (GroupMember member : members) {
			userList.add(member.getMember());
		}
		return userList;
	}

	@Override
	public List<UserInfo> listAllUsersInGroup(String groupName) {
		List<UserInfo> users = new ArrayList<UserInfo>();
		Iterable<GroupMember> members = memberRepository.findAll(groupMember.memberOfgroup.groupName.eq(groupName));
		for (GroupMember member : members) {
			users.add(member.getMember());
		}
		return users;
	}

	@Override
	public List<GroupInfo> listGroupsForUser(String userId) {
		List<GroupInfo> groups = new ArrayList<GroupInfo>();
		Iterable<GroupMember> members = memberRepository.findAll(groupMember.member.userId.eq(userId).and(groupMember.enabled.eq(Boolean.TRUE)));
		for (GroupMember member : members) {
			groups.add(member.getMemberOfgroup());
		}
		return groups;
	}

	@Override
	public void saveGroupInfo(GroupInfo groupInfo) {
		groupRepository.save(groupInfo);
	}

	@Override
	public void saveGroupMember(GroupMember groupMember) {
		memberRepository.save(groupMember);
	}

	@Override
	public void saveUserInfo(UserInfo userInfo) {
		userRepository.save(userInfo);
	}
}
