package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

import mmt.patientsystem.Models.Consultation;

@Repository
public class ConsultationRepository {

    @Autowired
    DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    public ResultSet getConsultations(int order, boolean reverse) throws SQLException {
        consultationSelectFiller();
        /*
        query += "ORDER BY ";

        switch (order) {

        }
        if(reverse) {
            query += "DESC";
        }
        */


        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    private PreparedStatement consultationFiller(Consultation consultation) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setString(1, consultation.getDescription());
        preparedStatement.setString(2, consultation.getConclusion());
        preparedStatement.setDate(3, (Date) consultation.getDate());
        preparedStatement.setTime(4, consultation.getFromTime());
        preparedStatement.setTime(5, consultation.getToTime());
        preparedStatement.setInt(6, consultation.getPatientId());
        preparedStatement.setInt(7, consultation.getDoctorId());

        return preparedStatement;
    }

    private void consultationSelectFiller() {
        query = "SELECT consultations.*, patients.cpr, patients.name as patient_name " +
                "FROM consultations " +
                "INNER JOIN patients " +
                "ON patients.id = consultations.fk_patient";
    }

    public void addConsultation(Consultation consultation) throws SQLException {
        query = "INSERT INTO consultations (description, conclusion, date, from_time, to_time, fk_patient, fk_doctor) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        preparedStatement = consultationFiller(consultation);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


}
