package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class PatientRepository {

    @Autowired
    DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    public ResultSet getAllPatients(int order, boolean reverse) throws SQLException{
        query = "SELECT * FROM patients " +
                "ORDER BY ";

        switch (order) {
            case 1:
                query += "name ";
                break;
            case 2:
                query += "age ";
                break;
            case 3:
                query += "birthdate ";
                break;
            case 4:
                query += "cpr ";
                break;
            case 5:
                query += "height ";
                break;
            case 6:
                query += "weight ";
                break;
            case 7:
                query += "gender ";
                break;
        }
        if(reverse) {
            query += "DESC";
        }

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet getSinglePatientID(int id) throws SQLException{
        query = "SELECT * FROM patients " +
                "WHERE id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet getSinglePatientSearch(Patient patient) throws SQLException {
        query = "SELECT * FROM patients " +
                "WHERE cpr = ?";

        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, patient.getCPR());
        return preparedStatement.executeQuery();
    }

    public void editPatient(Patient patient) throws SQLException {
        query = "UPDATE patients " +
                "SET name = ?, age = ?, birthDate = ?, cpr = ?, height = ?, weight = ?, gender = ?, description = ? " +
                "WHERE id = ?";

        preparedStatement = patientFiller(patient);
        preparedStatement.setInt(9, patient.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void addPatient(Patient patient) throws SQLException {
        query = "INSERT INTO patients (name, age, birthdate, cpr, height, weight, gender, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        preparedStatement = patientFiller(patient);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private PreparedStatement patientFiller(Patient patient) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setString(1, patient.getName());
        preparedStatement.setInt(2, patient.getAge());
        preparedStatement.setDate(3, patient.getBirthDate());
        preparedStatement.setInt(4, patient.getCPR());
        preparedStatement.setInt(5, patient.getHeight());
        preparedStatement.setDouble(6, patient.getWeight());
        preparedStatement.setString(7, patient.getGender());
        preparedStatement.setString(8, patient.getPersonalDescription());
        return preparedStatement;
    }

    public void deletePatient(int id) throws SQLException {
        query = "DELETE FROM patients " +
                "WHERE id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }
}
