/**
 * 
 */
package org.springframework.di.autowire;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.di.ProfileDataAccessInterface;
import org.springframework.di.ProfileInfo;
import org.springframework.di.ProfileInteface;
import org.springframework.di.ProfileNotificationInterface;
import org.springframework.di.ResourceException;
import org.springframework.stereotype.Service;

/**
 * @author corneil
 */
@Service("profileService")
public class ProfileService implements ProfileInteface {
    private ProfileDataAccessInterface dataAccess;
    private ProfileNotificationInterface notification;

    @Autowired
    public ProfileService(ProfileDataAccessInterface dataAccess, ProfileNotificationInterface notification) {
        super();
        this.dataAccess = dataAccess;
        this.notification = notification;
    }

    public void createProfile(ProfileInfo profile) throws ResourceException {
        dataAccess.create(profile);
        notification.notifyCreate(profile);
    }

    public void deleteProfile(ProfileInfo profile) throws ResourceException {
        dataAccess.delete(profile);
        notification.notifyDelete(profile);
    }

    public List<ProfileInfo> listProfiles(String match) throws ResourceException {
        return dataAccess.listAll(match);
    }

    public void updateProfile(ProfileInfo profile) throws ResourceException {
        dataAccess.save(profile);
        notification.notifyUpdate(profile);
    }
}