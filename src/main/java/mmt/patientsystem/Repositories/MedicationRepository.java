package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.Medication;
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

    public ResultSet getMedications(int id, int type) throws SQLException {
        if(type == 1) {
            query = "SELECT medicine.* FROM pm_junction " +
                    "INNER JOIN medicine ON pm_junction.fk_medicin = medicine.m_id " +
                    "WHERE fk_prescription = '" + id + "'";
        } else if(type == 2){
            query = "SELECT medicine.* FROM dm_junction " +
                    "INNER JOIN medicine ON dm_junction.fk_medicin = medicine.m_id " +
                    "WHERE fk_diagnosis = '" + id + "'";
        } else {
            query = "SELECT * FROM medicine " +
                    "ORDER BY name";
        }

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public void addMedicine(Medication medication, int type) throws SQLException {
        if(type == 1) {
            query = "INSERT INTO pm_junction (fk_medicin, fk_prescription) " +
                    "VALUES (?, ?)";
        } else if (type == 2) {
            query = "INSERT INTO dm_junction (fk_medicin, fk_diagnosis) " +
                    "VALUES (?, ?)";
        }

        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, medication.getId());
        preparedStatement.setInt(2, medication.getCouplingId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
