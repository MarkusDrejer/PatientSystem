package mmt.patientsystem.Repositories;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserData implements DBAccess {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
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
}
