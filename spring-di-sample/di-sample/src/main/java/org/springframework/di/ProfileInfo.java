/**
 * 
 */
package org.springframework.di;

import java.io.Serializable;
import java.util.Date;

/**
 * @author corneil
 */
public class ProfileInfo implements Serializable {
    private static final long serialVersionUID = -6443301435230690078L;
    private Date dateOfBirth;
    private String email;
    private String name;
    private ProfileStatus status;

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public ProfileStatus getStatus() {
        return status;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
    }
}