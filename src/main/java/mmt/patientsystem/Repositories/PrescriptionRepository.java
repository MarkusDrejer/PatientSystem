package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;


@Repository
public class PrescriptionRepository {

    @Autowired
    DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    public ResultSet getPrescriptions(int id) throws SQLException {
        query = "SELECT prescriptions.*, GROUP_CONCAT(medicine.NAME) AS medications_given " +
                "FROM prescriptions, medicine, pm_junction " +
                "WHERE pm_junction.fk_prescription = prescriptions.id AND pm_junction.fk_medicin = medicine.m_id AND prescriptions.fk_patient = '" + id + "' " +
                "GROUP BY prescriptions.id " +
                "ORDER BY prescriptions.prescription";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet getSinglePrescription(int id) throws SQLException {
        query = "SELECT * " +
                "FROM prescriptions " +
                "WHERE prescriptions.id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public void deletePrescription(int id) throws SQLException {
        query = "DELETE FROM prescriptions " +
                "WHERE id = '" + id +"'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }

    public ResultSet addPrescription(Prescription prescription) throws SQLException {
        query = "INSERT INTO prescriptions (prescription, note, fk_patient, fk_doctor) " +
                "VALUES (?, ?, ?, ?)";

        preparedStatement = dbAccess.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, prescription.getPrescription());
        preparedStatement.setString(2, prescription.getNote());
        preparedStatement.setInt(3, prescription.getPatientId());
        preparedStatement.setInt(4, prescription.getDoctorId());
        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }

    public void addPrescriptionJunction(int key, int medId) throws SQLException {
        query = "INSERT INTO pm_junction (fk_medicin, fk_prescription) " +
                "VALUES (?, ?)";
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, medId);
        preparedStatement.setInt(2, key);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}