package mmt.patientsystem.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class PatientRepository {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    @Autowired
    DBAccess dbAccess;

    public PatientRepository() {
        connection = dbAccess.getConnection();
    }
}
