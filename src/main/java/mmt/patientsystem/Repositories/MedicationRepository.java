package mmt.patientsystem.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class MedicationRepository {

    @Autowired
    DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    public ResultSet getMedicationsPrescription(int id) throws SQLException {
        query = "SELECT medicine.* FROM pm_junction " +
                "INNER JOIN medicine ON pm_junction.fk_medicin = medicine.m_id " +
                "WHERE fk_prescription = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }
}
