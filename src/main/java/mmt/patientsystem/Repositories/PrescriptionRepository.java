package mmt.patientsystem.Repositories;

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
        query = "SELECT prescriptions.*, medicine.* " +
                "FROM prescriptions, medicine, pm_junction " +
                "WHERE pm_junction.fk_prescription = prescriptions.id AND pm_junction.fk_medicin = medicine.m_id AND prescriptions.fk_patient = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet getSinglePrescription(int id) throws SQLException {
        query = "SELECT prescriptions.*, medicine.* " +
                "FROM prescriptions, medicine, pm_junction " +
                "WHERE pm_junction.fk_prescription = prescriptions.id AND pm_junction.fk_medicin = medicine.m_id AND prescriptions.id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    public void deletePrescription(int id) throws SQLException {
        query = "DELETE FROM prescriptions " +
                "WHERE id = '" + id +"'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }
}