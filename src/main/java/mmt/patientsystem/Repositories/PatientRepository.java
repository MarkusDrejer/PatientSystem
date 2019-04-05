package mmt.patientsystem.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class PatientRepository {

    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    @Autowired
    DBAccess dbAccess;

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
}
