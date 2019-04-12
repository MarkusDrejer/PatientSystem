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

    public ResultSet getConsultations() throws SQLException {
        query = "SELECT * FROM consultations " +
                "ORDER BY date";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet getSingleConsultation(int id) throws SQLException {
        query = "SELECT * FROM consultations " +
                "WHERE id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    private PreparedStatement consultationFiller(Consultation consultation) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setString(1, consultation.getDescription());
        preparedStatement.setString(2, consultation.getConclusion());
        preparedStatement.setDate(3, (Date) consultation.getDate());
        preparedStatement.setString(4, consultation.getFromTime());
        preparedStatement.setString(5, consultation.getToTime());
        preparedStatement.setInt(6, consultation.getDoctorId());

        return preparedStatement;
    }

    public void editConsultation(Consultation consultation) throws SQLException {
        query = "UPDATE consultations " +
                "SET description = ?, consultation = ?, date = ?, from_time = ?, to_time = ?, fk_doctor = ? " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        preparedStatement = consultationFiller(consultation);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void deleteConsultation(int id) throws SQLException {
        query = "DELETE FROM consultations " +
                "WHERE id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }

    public void addConsultation(Consultation consultation) throws SQLException {
        query = "INSERT INTO consultations (description, conclusion, date, from_time, to_time, fk_doctor, fk_patient) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        preparedStatement = consultationFiller(consultation);
        preparedStatement.setInt(7, consultation.getPatientId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


}
