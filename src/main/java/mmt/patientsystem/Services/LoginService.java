package mmt.patientsystem.Services;

import mmt.patientsystem.Models.User;
import mmt.patientsystem.Repositories.DBAccess;
import mmt.patientsystem.Repositories.UserData;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class LoginService {

    private UserData db = new UserData();

    public boolean verifyUser(User user) throws SQLException {
        System.out.println(user.getUsername());
        ResultSet resultSet = db.verifyUser(user);

        if(resultSet.next()) {
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setUsername(resultSet.getString("username"));
            user.setRoleTier(resultSet.getInt("role_tier"));

            System.out.println(user.getUsername());
            return true;
        } else {
            return false;
        }
    }
}
