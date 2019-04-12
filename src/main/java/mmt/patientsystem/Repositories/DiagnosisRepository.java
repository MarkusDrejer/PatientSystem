package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.Diagnosis;
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

    public ResultSet getDiagnosis(int id) throws SQLException {
        query = "SELECT diagnosis.*, diagnose_names.d_name, GROUP_CONCAT(medicine.NAME) AS medications_given " +
                "FROM diagnosis, medicine, dm_junction, diagnose_names " +
                "WHERE dm_junction.fk_diagnosis = diagnosis.id AND dm_junction.fk_medicin = medicine.m_id AND diagnosis.fk_diagnose_name = diagnose_names.id AND diagnosis.fk_patient = '" + id + "' " +
                "GROUP BY diagnosis.id " +
                "ORDER BY diagnose_names.d_name";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet getSingleDiagnosis(int id) throws SQLException {
        query = "SELECT diagnosis.*, diagnose_names.d_name " +
                "FROM diagnosis " +
                "INNER JOIN diagnose_names ON diagnosis.fk_diagnose_name = diagnose_names.id " +
                "WHERE diagnosis.id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet getDiagnosisNames() throws SQLException {
        query = "SELECT * FROM diagnose_names " +
                "ORDER BY d_name";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public void deleteDiagnosis(int id) throws SQLException {
        query = "DELETE FROM diagnosis " +
                "WHERE id = '" + id +"'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }

    public ResultSet addDiagnosis(Diagnosis diagnosis) throws SQLException {
        query = "INSERT INTO diagnosis (note, fk_diagnose_name, fk_patient, fk_doctor) " +
                "VALUES (?, ?, ?, ?)";
        preparedStatement = dbAccess.getConnection().prepareStatement(query, statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, diagnosis.getNote());
        preparedStatement.setInt(2, diagnosis.getDiagnosisNameId());
        preparedStatement.setInt(3, diagnosis.getPatientId());
        preparedStatement.setInt(4, diagnosis.getDoctorId());
        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }

    public void addDiagnosisJunction(int key, int medId) throws SQLException {
        query = "INSERT INTO dm_junction (fk_medicin, fk_diagnosis) " +
                "VALUES (?, ?)";
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, medId);
        preparedStatement.setInt(2, key);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
