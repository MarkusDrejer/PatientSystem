package mmt.patientsystem.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class MedicationRepository {

    @Autowired
    DBAccess dbAccess;

    private Statement statement;
    private String query;

    public ResultSet getMedications(int id, int type) throws SQLException {
        if(type == 1) {
            query = "SELECT medicine.* FROM pm_junction " +
                    "INNER JOIN medicine ON pm_junction.fk_medicin = medicine.m_id " +
                    "WHERE fk_prescription = '" + id + "'";
        } else {
            query = "SELECT medicine.* FROM dm_junction " +
                    "INNER JOIN medicine ON dm_junction.fk_medicin = medicine.m_id " +
                    "WHERE fk_diagnosis = '" + id + "'";
        }

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }
}
