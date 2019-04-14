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

    /**
     * returns diagnosis
     * @param id
     **/
    public ResultSet getDiagnosis(int id) throws SQLException {
        query = "SELECT diagnosis.*, diagnose_names.d_name, GROUP_CONCAT(medicine.NAME) AS medications_given " +
                "FROM diagnosis, medicine, dm_junction, diagnose_names " +
                "WHERE dm_junction.fk_diagnosis = diagnosis.id AND dm_junction.fk_medicin = medicine.m_id AND diagnosis.fk_diagnose_name = diagnose_names.id AND diagnosis.fk_patient = '" + id + "' " +
                "GROUP BY diagnosis.id " +
                "ORDER BY diagnose_names.d_name";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     * returns single consultations
     * @param id
     **/
    public ResultSet getSingleDiagnosis(int id) throws SQLException {
        query = "SELECT diagnosis.*, diagnose_names.d_name " +
                "FROM diagnosis " +
                "INNER JOIN diagnose_names ON diagnosis.fk_diagnose_name = diagnose_names.id " +
                "WHERE diagnosis.id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     * fills diagnosis
     * @param diagnosis
     **/
    private PreparedStatement diagnosisFiller(Diagnosis diagnosis) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query, statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, diagnosis.getNote());
        preparedStatement.setInt(2, diagnosis.getDiagnosisNameId());
        preparedStatement.setInt(3, diagnosis.getDoctorId());

        return preparedStatement;
    }

    /**
     * returns diagnosis names
     **/
    public ResultSet getDiagnosisNames() throws SQLException {
        query = "SELECT * FROM diagnose_names " +
                "ORDER BY d_name";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     * edit diagnosis
     * @param diagnosis
     **/
    public void editDiagnosis(Diagnosis diagnosis) throws SQLException {
        query = "UPDATE diagnosis " +
                "SET note = ?, fk_diagnose_name = ?, fk_doctor = ? " +
                "WHERE id = '" + diagnosis.getId() + "'";

        preparedStatement = diagnosisFiller(diagnosis);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * delete diagnosis
     * @param id
     **/
    public void deleteDiagnosis(int id) throws SQLException {
        query = "DELETE FROM diagnosis " +
                "WHERE id = '" + id +"'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }

    /**
     * adds diagnosis
     * @param diagnosis
     **/
    public ResultSet addDiagnosis(Diagnosis diagnosis) throws SQLException {
        query = "INSERT INTO diagnosis (note, fk_diagnose_name, fk_doctor, fk_patient) " +
                "VALUES (?, ?, ?, ?)";

        preparedStatement = diagnosisFiller(diagnosis);
        preparedStatement.setInt(4, diagnosis.getPatientId());
        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }

    /**
     * adds diagnosis through junction
     * @param key
     * @param medId
     **/
    public void addDiagnosisJunction(int key, int medId) throws SQLException {
        query = "INSERT INTO dm_junction (fk_medicin, fk_diagnosis) " +
                "VALUES (?, ?)";

        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, medId);
        preparedStatement.setInt(2, key);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * adds diagnosis name
     * @param name
     **/
    public void addDiagnosisName(String name) throws SQLException {
        query = "INSERT INTO diagnose_names (d_name) " +
                "VALUES (?)";

        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
