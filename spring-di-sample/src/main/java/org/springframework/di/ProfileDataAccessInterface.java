/**
 * 
 */
package org.springframework.di;

import java.util.List;

/**
 * @author corneil
 */
public interface ProfileDataAccessInterface {
    public void create(ProfileInfo profile) throws DataAccessException;

    public void delete(ProfileInfo profile) throws DataAccessException;

    public List<ProfileInfo> listAll(String match) throws DataAccessException;

    public void save(ProfileInfo profile) throws DataAccessException;
}