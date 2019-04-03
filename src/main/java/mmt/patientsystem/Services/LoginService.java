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
        ResultSet resultSet = db.verifyUser(user);

        return resultSet.next();
    }
}
