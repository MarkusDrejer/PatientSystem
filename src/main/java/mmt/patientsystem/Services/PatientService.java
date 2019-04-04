package mmt.patientsystem.Services;

import mmt.patientsystem.Models.Patient;
import mmt.patientsystem.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public List<Patient> getAllPatients(int order) throws SQLException {
        ResultSet resultSet = patientRepository.getAllPatients(order);

        List<Patient> patients = new ArrayList<>();

        while (resultSet.next()) {
            Patient patient = new Patient();

            patient.setId(resultSet.getInt("id"));
            patient.setName(resultSet.getString("name"));
            patient.setAge(resultSet.getInt("age"));
            patient.setBirthDate(resultSet.getDate("birthdate"));
            patient.setCPR(resultSet.getInt("cpr"));
            patient.setHeight(resultSet.getInt("height"));
            patient.setWeight(resultSet.getInt("weight"));
            patient.setGender(resultSet.getString("gender"));
            patient.setPersonalDescription(resultSet.getString("description"));

            patients.add(patient);
        }
        return patients;
    }
}
