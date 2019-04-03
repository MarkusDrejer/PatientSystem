package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.User;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserData implements DBAccess {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String query;

    public UserData() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql6.gear.host/patientsystemdb",
                    "patientsystemdb",
                    "Hn5Y-xGfN-8W");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet get() {
        return null;
    }

    @Override
    public void create() {

    }

    @Override
    public void edit() {

    }

    @Override
    public void delete() {

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
