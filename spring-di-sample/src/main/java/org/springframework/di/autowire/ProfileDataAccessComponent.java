/**
 * 
 */
package org.springframework.di.autowire;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.di.DataAccessException;
import org.springframework.di.ProfileDataAccessInterface;
import org.springframework.di.ProfileInfo;
import org.springframework.di.ProfileStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * @author corneil
 */
@Component("profileDAC")
public class ProfileDataAccessComponent implements ProfileDataAccessInterface {

    private JdbcTemplate template;

    @Autowired
    public ProfileDataAccessComponent(DataSource dataSource) {
        super();
        template = new JdbcTemplate(dataSource);
    }

    public void create(final ProfileInfo profile) throws DataAccessException {
        template.execute("insert into profile (NAME, EMAIL, DOB, STATUS) values(?,?,?,?)",
                new PreparedStatementCallback<Void>() {
                    @Override
                    public Void doInPreparedStatement(PreparedStatement stmt) throws SQLException,
                            org.springframework.dao.DataAccessException {
                        stmt.setString(1, profile.getName());
                        stmt.setString(2, profile.getEmail());
                        stmt.setDate(3, new java.sql.Date(profile.getDateOfBirth().getTime()));
                        stmt.setString(4, profile.getStatus().toString());
                        return null;
                    }
                });
    }

    public void delete(final ProfileInfo profile) throws DataAccessException {
        template.execute("delete from profile where NAME=?", new PreparedStatementCallback<Void>() {
            @Override
            public Void doInPreparedStatement(PreparedStatement stmt) throws SQLException,
                    org.springframework.dao.DataAccessException {
                stmt.setString(1, profile.getName());
                return null;
            }
        });
    }

    public List<ProfileInfo> listAll(String match) throws DataAccessException {
        final StringBuilder matchLike = new StringBuilder();
        matchLike.append("%");
        matchLike.append(match);
        matchLike.append("%");
        return template.query("select * from profile where NAME like ? OR EMAIL like ?", new Object[] { matchLike,
                matchLike }, new RowMapper<ProfileInfo>() {
            @Override
            public ProfileInfo mapRow(ResultSet rs, int index) throws SQLException {
                ProfileInfo profile = new ProfileInfo();
                profile.setName(rs.getString("NAME"));
                profile.setEmail(rs.getString("EMAIL"));
                profile.setDateOfBirth(new java.util.Date(rs.getDate("DOB").getTime()));
                profile.setStatus(ProfileStatus.valueOf(rs.getString("STATUS")));
                return profile;
            }
        });
    }

    public void save(final ProfileInfo profile) throws DataAccessException {
        template.execute("update profile set EMAIL=?,DOB=?, STATUS=? where NAME =?",
                new PreparedStatementCallback<Void>() {
                    @Override
                    public Void doInPreparedStatement(PreparedStatement stmt) throws SQLException,
                            org.springframework.dao.DataAccessException {
                        stmt.setString(1, profile.getEmail());
                        stmt.setDate(2, new java.sql.Date(profile.getDateOfBirth().getTime()));
                        stmt.setString(3, profile.getStatus().toString());
                        stmt.setString(4, profile.getName());
                        return null;
                    }
                });
    }
}