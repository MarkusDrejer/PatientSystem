package mmt.patientsystem.Services;

import mmt.patientsystem.Models.User;
import mmt.patientsystem.Repositories.LoginRepository;
import mmt.patientsystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    /**
     * verifies user
     * @param user
     **/
    public boolean verifyUser(User user) throws SQLException {
        ResultSet resultSet = loginRepository.loginVerification(user);

        if(resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setUsername(resultSet.getString("username"));
            user.setRole_id(resultSet.getInt("fk_role"));
            user.setRole_name(resultSet.getString("role_name"));
            return true;
        } else {
            return false;
        }
    }
}
