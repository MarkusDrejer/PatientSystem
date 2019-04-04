package mmt.patientsystem.Services;

import mmt.patientsystem.Models.User;
import mmt.patientsystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class LoginService {

    @Autowired
    private UserRepository db;

    public boolean verifyUser(User user) throws SQLException {
        ResultSet resultSet = db.verifyUser(user);

        if(resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setUsername(resultSet.getString("username"));
            user.setRoleTier(resultSet.getInt("role_tier"));
            return true;
        } else {
            return false;
        }
    }
}
