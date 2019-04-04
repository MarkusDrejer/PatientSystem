package mmt.patientsystem.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class PatientRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    public PatientRepository() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql6.gear.host/patientsystemdb",
                    "patientsystemdb",
                    "Hn5Y-xGfN-8W");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllPatients() throws SQLException{
        query = "SELECT * FROM patients " +
                "ORDER BY name";

        statement = connection.createStatement();

        return statement.executeQuery(query);
    }
}
