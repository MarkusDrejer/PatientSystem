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
        prescriptionSelectFiller();



        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    private PreparedStatement prescriptionFiller(Prescription prescription) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setString(1, prescription.getNote());
        preparedStatement.setString(2, prescription.getMedicationName());
        preparedStatement.setDate(3, (Date) prescription.getDate());
        preparedStatement.setString(4, prescription.getPatientName());
        preparedStatement.setString(5, prescription.getDoctorName());

        return preparedStatement;
    }

    private void prescriptionSelectFiller() {
        query = "SELECT prescriptions.*, patients.cpr, patients.name as patient_name " +
                "FROM Prescription " +
                "INNER JOIN patients " +
                "ON patients.id = prescription.fk_patient";
    }

    public void addPrescription(Prescription prescription) throws SQLException {
        query = "INSERT INTO prescriptions (note, prescription, date, fk_patient, fk_doctor) " +
                "VALUES (?, ?, ?, ?, ?)";

        preparedStatement = prescriptionFiller(prescription);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


}
