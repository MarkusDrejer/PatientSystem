package mmt.patientsystem.Repositories;

import java.sql.ResultSet;

public interface DBAccess {

    ResultSet get();

    void create();

    void edit();

    void delete();
}
