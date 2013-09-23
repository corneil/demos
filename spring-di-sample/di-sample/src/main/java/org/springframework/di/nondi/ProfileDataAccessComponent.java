/**
 * 
 */
package org.springframework.di.nondi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.di.DataAccessException;
import org.springframework.di.ProfileDataAccessInterface;
import org.springframework.di.ProfileInfo;
import org.springframework.di.ProfileStatus;

/**
 * @author corneil
 */
public class ProfileDataAccessComponent implements ProfileDataAccessInterface {
    DataSource dataSource;

    public ProfileDataAccessComponent() throws NamingException, FileNotFoundException, IOException {
        super();
        // In EJB or Web Container
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/profile");
        } catch(Throwable x) {
            // Assume we are not in container.
        }
        if(dataSource == null) {
            // Manual creation outside of container
            Properties props = new Properties();
            File propFile = new File("db.properties");
            props.load(new FileInputStream(propFile));
            BasicDataSource ds = new BasicDataSource();
    
            ds.setDriverClassName(props.getProperty("db.driver"));
            ds.setUsername(props.getProperty("db.username"));
            ds.setPassword(props.getProperty("db.password"));
            ds.setUrl(props.getProperty("db.url"));
            ds.setMaxActive(Integer.parseInt(props.getProperty("db.maxActive", "10")));
            ds.setMaxIdle(Integer.parseInt(props.getProperty("db.maxIdle", "5")));
            ds.setInitialSize(Integer.parseInt(props.getProperty("db.initialSize", "5")));
            ds.setValidationQuery(props.getProperty("db.validationQuery", "SELECT 1"));
            dataSource = ds;
        }
    }

    public void create(ProfileInfo profile) throws DataAccessException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("Exception getting connection:" + e.getMessage(), e);
        }
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("insert into profile (NAME, EMAIL, DOB, STATUS) values(?,?,?,?)");
            stmt.setString(1, profile.getName());
            stmt.setString(2, profile.getEmail());
            stmt.setDate(3, new java.sql.Date(profile.getDateOfBirth().getTime()));
            stmt.setString(4, profile.getStatus().toString());

            stmt.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Exception inserting profile:" + e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataAccessException("Exception closing connection:" + e.getMessage(), e);
            }
        }
    }

    public void delete(ProfileInfo profile) throws DataAccessException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("Exception getting connection:" + e.getMessage(), e);
        }
        try {
            PreparedStatement stmt = connection.prepareStatement("delete from profile where NAME=?");
            stmt.setString(1, profile.getName());

            stmt.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Exception deleting profile:" + e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataAccessException("Exception closing connection:" + e.getMessage(), e);
            }
        }
    }

    public List<ProfileInfo> listAll(String match) throws DataAccessException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("Exception getting connection:" + e.getMessage(), e);
        }
        try {
            final StringBuilder matchLike = new StringBuilder();
            matchLike.append("%");
            matchLike.append(match);
            matchLike.append("%");
            PreparedStatement stmt = connection
                    .prepareStatement("select * from profile where NAME like ? OR EMAIL like ?");
            stmt.setString(1, matchLike.toString());
            stmt.setString(2, matchLike.toString());
            ResultSet rs = stmt.executeQuery();
            List<ProfileInfo> results = new ArrayList<ProfileInfo>();
            while (rs.next()) {
                ProfileInfo profile = new ProfileInfo();
                profile.setName(rs.getString("NAME"));
                profile.setEmail(rs.getString("EMAIL"));
                profile.setDateOfBirth(new java.util.Date(rs.getDate("DOB").getTime()));
                profile.setStatus(ProfileStatus.valueOf(rs.getString("STATUS")));
                results.add(profile);
            }
            return results;
        } catch (SQLException e) {
            throw new DataAccessException("Exception inserting profile:" + e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataAccessException("Exception closing connection:" + e.getMessage(), e);
            }
        }
    }

    public void save(ProfileInfo profile) throws DataAccessException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("Exception getting connection:" + e.getMessage(), e);
        }
        try {
            PreparedStatement stmt = connection
                    .prepareStatement("update profile set EMAIL=?,DOB=?, STATUS=? where NAME =?");
            stmt.setString(1, profile.getEmail());
            stmt.setDate(2, new java.sql.Date(profile.getDateOfBirth().getTime()));
            stmt.setString(3, profile.getStatus().toString());
            stmt.setString(4, profile.getName());

            stmt.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Exception updating profile:" + e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataAccessException("Exception closing connection:" + e.getMessage(), e);
            }
        }
    }
}