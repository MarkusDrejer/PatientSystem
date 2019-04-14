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

    /**
     * returns patients
     * @param order
     **/
    public ResultSet getAllPatients(int order, boolean reverse) throws SQLException{
        patientSelectFiller();
        query += "ORDER BY ";

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

    /**
     * returns single patient
     * @param id
     **/
    public ResultSet getSinglePatientID(int id) throws SQLException{
        patientSelectFiller();
        query += "WHERE id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     * returns search of single patient
     * @param patient
     **/
    public ResultSet getSinglePatientSearch(Patient patient) throws SQLException {
        query = "SELECT id FROM patients " +
                "WHERE cpr = ?";

        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, patient.getCPR());
        return preparedStatement.executeQuery();
    }

    /**
     * fills chosen patient
     **/
    private void patientSelectFiller() {
        query = "SELECT *, YEAR(CURDATE()) - YEAR(birthdate) - IF(STR_TO_DATE(CONCAT(YEAR(CURDATE()), " +
                "'-', MONTH(birthdate), '-', DAY(birthdate)), '%Y-%c-%e') > CURDATE(), 1, 0) AS age FROM patients ";
    }

    /**
     * edits patient
     * @param patient
     **/
    public void editPatient(Patient patient) throws SQLException {
        query = "UPDATE patients " +
                "SET name = ?, birthDate = ?, cpr = ?, height = ?, weight = ?, gender = ?, description = ? " +
                "WHERE id = ?";

        preparedStatement = patientFiller(patient);
        preparedStatement.setInt(8, patient.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * adds patient
     * @param patient
     **/
    public void addPatient(Patient patient) throws SQLException {
        query = "INSERT INTO patients (name, birthdate, cpr, height, weight, gender, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        preparedStatement = patientFiller(patient);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * fills patient
     * @param patient
     **/
    private PreparedStatement patientFiller(Patient patient) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setString(1, patient.getName());
        preparedStatement.setDate(2, patient.getBirthDate());
        preparedStatement.setInt(3, patient.getCPR());
        preparedStatement.setInt(4, patient.getHeight());
        preparedStatement.setDouble(5, patient.getWeight());
        preparedStatement.setString(6, patient.getGender());
        preparedStatement.setString(7, patient.getPersonalDescription());
        return preparedStatement;
    }

    /**
     * delete patient
     * @param id
     **/
    public void deletePatient(int id) throws SQLException {
        query = "DELETE FROM patients " +
                "WHERE id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }
}
