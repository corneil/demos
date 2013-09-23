/**
 * 
 */
package org.springframework.di;

/**
 * @author corneil
 */
public interface ProfileNotificationInterface {
    public void notifyCreate(ProfileInfo profile) throws MessagingException;

    public void notifyDelete(ProfileInfo profile) throws MessagingException;

    public void notifyUpdate(ProfileInfo profile) throws MessagingException;
}