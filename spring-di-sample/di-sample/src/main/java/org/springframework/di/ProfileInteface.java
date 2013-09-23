/**
 * 
 */
package org.springframework.di;

import java.util.List;

/**
 * @author corneil
 */
public interface ProfileInteface {
    public void createProfile(ProfileInfo profile) throws ResourceException;

    public void deleteProfile(ProfileInfo profile) throws ResourceException;

    public List<ProfileInfo> listProfiles(String match) throws ResourceException;

    public void updateProfile(ProfileInfo profile) throws ResourceException;
}