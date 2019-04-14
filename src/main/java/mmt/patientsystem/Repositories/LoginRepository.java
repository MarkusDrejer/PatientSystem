package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class LoginRepository {

    @Autowired
    DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private String query;

    /**
     * verifies user login
     * @param user
     **/
    public ResultSet loginVerification(User user) throws SQLException {
        query = "SELECT users.*, roles.role_name FROM users " +
                "INNER JOIN roles ON users.fk_role = roles.id " +
                "WHERE username = ? AND password = ?";

            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            return preparedStatement.executeQuery();
    }
}
