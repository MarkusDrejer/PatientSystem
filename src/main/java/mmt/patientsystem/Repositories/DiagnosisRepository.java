package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class DiagnosisRepository {

    @Autowired
    DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    public ResultSet getDiagnosis() throws SQLException {
        query = "SELECT diagnosis.*, medicine.name, diagnose_names.d_name " +
                "FROM diagnosis, medicine, dm_junction, diagnose_names " +
                "WHERE dm_junction.fk_diagnosis = diagnosis.id AND dm_junction.fk_medicin = medicine.id AND diagnosis.fk_diagnose_name = diagnose_names.id";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    private PreparedStatement diagnosisFiller(Prescription prescription) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setString(1, prescription.getNote());
        preparedStatement.setString(2, prescription.getMedicationName());
        preparedStatement.setDate(3, (Date) prescription.getDate());

        return preparedStatement;
    }
}
