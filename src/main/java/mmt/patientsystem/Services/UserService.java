package mmt.patientsystem.Services;

import mmt.patientsystem.Models.User;
import mmt.patientsystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private ResultSet resultSet;

    /**
     * puts results from database into list
     **/
    public List<User> getAllUsers() throws SQLException {
        resultSet = userRepository.getAllUsers();
        List<User> userList = new ArrayList<>();

        while (resultSet.next()) {
            User user = userFiller();
            user.setRole_name(resultSet.getString("role_name"));
            userList.add(user);
        }
        return userList;
    }

    /**
     * returns single user
     * @param id
     **/
    public User getSingleUser(int id) throws SQLException {
        resultSet = userRepository.getSingleUser(id);
        User user = null;

        while (resultSet.next()) {
            user = userFiller();
            user.setPassword(resultSet.getString("password"));
        }
        return user;
    }

    /**
     * fills user
     **/
    private User userFiller() throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setUsername(resultSet.getString("username"));
        user.setRole_id(resultSet.getInt("fk_role"));

        return user;
    }

    /**
     * edits user
     * @param user
     **/
    public void editUser(User user) throws SQLException {
        userRepository.editUser(user);
    }

    /**
     * delete user
     * @param id
     **/
    public void deleteUser(int id) throws SQLException {
        userRepository.deleteUser(id);
    }

    /**
     * add user
     * @param user
     **/
    public void addUser(User user) throws SQLException {
        userRepository.addUser(user);
    }

    /**
     * adds doctors to list
     **/
    public List<User> getDoctors() throws SQLException {
        resultSet = userRepository.getDoctors();
        List<User> userList = new ArrayList<>();

        while (resultSet.next()) {
            User user = userFiller();
            userList.add(user);
        }
        return userList;
    }

}
