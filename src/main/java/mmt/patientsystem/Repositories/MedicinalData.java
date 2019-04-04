package mmt.patientsystem.Repositories;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class MedicinalData {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    public MedicinalData() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql6.gear.host/patientsystemdb",
                    "patientsystemdb",
                    "Hn5Y-xGfN-8W");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
