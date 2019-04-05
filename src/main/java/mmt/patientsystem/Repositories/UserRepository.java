package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {

    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String query;

    @Autowired
    DBAccess dbAccess;

    public ResultSet verifyUser(User user){
        query = "SELECT * FROM users " +
                "WHERE username = ? AND password = ?";
        try {
            preparedStatement = dbAccess.getConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
