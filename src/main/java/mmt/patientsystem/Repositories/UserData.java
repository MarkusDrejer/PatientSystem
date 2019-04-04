package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserData {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String query;

    @Autowired
    DBAccess dbAccess;

    public UserData() {
        connection = dbAccess.getConnection();
    }

    public ResultSet verifyUser(User user){
        query = "SELECT * FROM users " +
                "WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "'";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
