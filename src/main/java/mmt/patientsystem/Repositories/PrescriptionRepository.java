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

    /**
     * get all prescriptions and the medications given, concatenates all medications into string
     * @param id
     **/
    public ResultSet getPrescriptions(int id) throws SQLException {
        query = "SELECT prescriptions.*, GROUP_CONCAT(medicine.NAME) AS medications_given " +
                "FROM prescriptions, medicine, pm_junction " +
                "WHERE pm_junction.fk_prescription = prescriptions.id AND pm_junction.fk_medicin = medicine.m_id AND prescriptions.fk_patient = '" + id + "' " +
                "GROUP BY prescriptions.id " +
                "ORDER BY prescriptions.prescription";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     * returns single prescription consultations
     * @param id
     **/
    public ResultSet getSinglePrescription(int id) throws SQLException {
        query = "SELECT * " +
                "FROM prescriptions " +
                "WHERE prescriptions.id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     * fills prescription
     * @param prescription
     **/
    private PreparedStatement prescriptionFiller(Prescription prescription) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, prescription.getPrescription());
        preparedStatement.setString(2, prescription.getNote());
        preparedStatement.setInt(3, prescription.getDoctorId());

        return preparedStatement;
    }

    /**
     * edit prescription
     * @param prescription
     **/
    public void editPrescription(Prescription prescription) throws SQLException {
        query = "UPDATE prescriptions " +
                "SET prescription = ?, note = ?, fk_doctor = ? " +
                "WHERE id = '" + prescription.getId() + "'";

        preparedStatement = prescriptionFiller(prescription);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * delete prescription
     * @param id
     **/
    public void deletePrescription(int id) throws SQLException {
        query = "DELETE FROM prescriptions " +
                "WHERE id = '" + id +"'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }

    /**
     * add prescription
     * @param prescription
     **/
    public ResultSet addPrescription(Prescription prescription) throws SQLException {
        query = "INSERT INTO prescriptions (prescription, note, fk_doctor, fk_patient) " +
                "VALUES (?, ?, ?, ?)";

        preparedStatement = prescriptionFiller(prescription);
        preparedStatement.setInt(4, prescription.getPatientId());
        preparedStatement.executeUpdate();

        return preparedStatement.getGeneratedKeys();
    }

    /**
     * adds prescription through junction
     * @param key
     * @param medId
     **/
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