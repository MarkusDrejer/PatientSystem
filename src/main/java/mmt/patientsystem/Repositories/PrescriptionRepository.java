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

    public ResultSet getPrescriptions() throws SQLException {
        query = "SELECT prescriptions.*, medicine.name " +
                "FROM prescriptions, medicine, pm_junction " +
                "WHERE pm_junction.fk_prescription = prescriptions.id AND pm_junction.fk_medicin = medicine.id";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet getSinglePrescription(int id) throws SQLException {
        query = "SELECT prescriptions.*, medicine.name " +
                "FROM prescriptions, medicine, pm_junction " +
                "WHERE pm_junction.fk_prescription = prescriptions.id AND pm_junction.fk_medicin = medicine.id AND prescriptions.id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    private PreparedStatement prescriptionFiller(Prescription prescription) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setString(1, prescription.getNote());
        preparedStatement.setString(2, prescription.getMedicationName());
        preparedStatement.setDate(3, (Date) prescription.getDate());

        return preparedStatement;
    }

    public void deletePrescription(int id) throws SQLException {
        query = "DELETE FROM prescriptions " +
                "WHERE id = '" + id +"'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }

    public void addPrescription(Prescription prescription) throws SQLException {
        query = "INSERT INTO prescriptions (note, prescription, date, fk_patient, fk_doctor) " +
                "VALUES (?, ?, ?, ?, ?)";

        preparedStatement = prescriptionFiller(prescription);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


}
