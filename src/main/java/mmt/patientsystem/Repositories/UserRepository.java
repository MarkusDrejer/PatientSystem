package mmt.patientsystem.Repositories;

import mmt.patientsystem.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {

    @Autowired
    DBAccess dbAccess;

    private PreparedStatement preparedStatement;
    private Statement statement;
    private String query;

    /**
     * returns all users
     **/
    public ResultSet getAllUsers() throws SQLException {
        query = "SELECT users.*, roles.role_name FROM users " +
                "INNER JOIN roles ON users.fk_role = roles.id " +
                "ORDER BY fk_role";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     * returns single user
     * @param id
     **/
    public ResultSet getSingleUser(int id) throws SQLException {
        query = "SELECT * FROM users " +
                "WHERE id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

    /**
     * edits user
     * @param user
     **/
    public void editUser(User user) throws SQLException {
        query = "UPDATE users " +
                "SET name = ?, username = ?, password = ?, fk_role = ? " +
                "WHERE id = ?";

        preparedStatement = userFiller(user);
        preparedStatement.setInt(5, user.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * adds user
     * @param user
     **/
    public void addUser(User user) throws SQLException {
        query = "INSERT INTO users (name, username, password, fk_role) " +
                "VALUES (?, ?, ?, ?)";

        preparedStatement = userFiller(user);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    /**
     * fills user
     * @param user
     **/
    private PreparedStatement userFiller(User user) throws SQLException {
        preparedStatement = dbAccess.getConnection().prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setInt(4, user.getRole_id());
        return preparedStatement;
    }

    /**
     * deletes user
     * @param id
     **/
    public void deleteUser(int id) throws SQLException {
        query = "DELETE FROM users " +
                "WHERE id = '" + id + "'";

        statement = dbAccess.getConnection().createStatement();
        statement.executeUpdate(query);
    }

    /**
     * returns doctors
     **/
    public ResultSet getDoctors() throws SQLException {
        query = "SELECT users.* FROM users " +
                "INNER JOIN roles ON users.fk_role = roles.id " +
                "WHERE roles.id = 2 " +
                "ORDER BY fk_role";

        statement = dbAccess.getConnection().createStatement();
        return statement.executeQuery(query);
    }

}
