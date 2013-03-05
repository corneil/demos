package com.tsctech.springdata.demo.service;

import java.util.List;

import com.tsctech.springdata.demo.data.GroupInfo;
import com.tsctech.springdata.demo.data.GroupMember;
import com.tsctech.springdata.demo.data.UserInfo;

public interface UserGroupDataService {
	// Added for testing
	public void deleteAllData();

	public void deleteGroupInfo(GroupInfo groupInfo);

	public void deleteGroupMember(GroupMember groupMember);

	public void deleteUserInfo(UserInfo userInfo);

	public GroupInfo findGroup(String name);

	public UserInfo findUser(String userId);

	public List<UserInfo> listActiveUsersInGroup(String groupName);

	public List<UserInfo> listAllUsersInGroup(String groupName);

	public List<GroupInfo> listGroupsForUser(String userId);

	public void saveGroupInfo(GroupInfo groupInfo);

	public void saveGroupMember(GroupMember groupMember);

	public void saveUserInfo(UserInfo userInfo);
	
}
